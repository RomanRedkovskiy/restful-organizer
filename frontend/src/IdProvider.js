import React, {useContext, useEffect, useState} from "react";

const IdContext = React.createContext();
const IdUpdateContext = React.createContext();

export function useId() {
	return useContext(IdContext);
}

export function useIdUpdate() {
	return useContext(IdUpdateContext);
}

export function IdProvider ({children}){
	const [id, setId] = useState(() => {
		const storedId = localStorage.getItem('id');
		return storedId !== null ? JSON.parse(storedId) : { userId: -1, compilationId: -1, taskId: -1, isShared: false };
	});

	useEffect(() => {
		localStorage.setItem('id', JSON.stringify(id));
	}, [id]);

	function changeId(newUser, newCompilation, newTask, newIsShared) {
		setId({userId: newUser, compilationId: newCompilation, taskId: newTask, isShared: newIsShared});
	}
	return (
		<IdContext.Provider value = {id}>
			<IdUpdateContext.Provider value = {changeId}>
				{children}
			</IdUpdateContext.Provider>
		</IdContext.Provider>
	)
}