import React, {useContext, useState} from "react";

const IdContext = React.createContext();
const IdUpdateContext = React.createContext();

export function useId() {
	return useContext(IdContext);
}

export function useIdUpdate() {
	return useContext(IdUpdateContext);
}

export function IdProvider ({children}){
	const [id, setId] = useState({ userId: 24, compilationId: -1, taskId: -1});

	function changeId(newUser, newCompilation, newTask) {
		// Use the setId function to update the id state with the new values
		setId({userId: newUser, compilationId: newCompilation, taskId: newTask});
	}
	return (
		<IdContext.Provider value = {id}>
			<IdUpdateContext.Provider value = {changeId}>
				{children}
			</IdUpdateContext.Provider>
		</IdContext.Provider>
	)
}