import { useEffect, useState } from "react";
import { useId, useIdUpdate } from "../IdProvider";
import useFetch from "../Fetches/useFetch";

function TaskForm ({id, title, description, status, compilationId, action, handleSubmit}) {

	const currentId = useId();
	const setId = useIdUpdate();

	console.log(currentId);

	const { data: selfCompilations, isSelfLoading, selfError } = 
		useFetch('http://localhost:8080/users/' + currentId.userId + '/self_compilations');
	const { data: sharedCompilations, isSharedLoading, sharedError } = 
		useFetch('http://localhost:8080/users/' + currentId.userId + '/shared_compilations');
	const [chosenCompilationId, setChosenCompilationId] = useState(compilationId);

	useEffect(() => {
		if (chosenCompilationId === -1 && sharedCompilations && sharedCompilations.length > 0) {
			setChosenCompilationId(sharedCompilations[0].id);
		}
	}, [sharedCompilations]); 

	useEffect(() => {
		if (chosenCompilationId === -1 && selfCompilations && selfCompilations.length > 0) {
			setChosenCompilationId(selfCompilations[0].id);
		}
	}, [selfCompilations]); 

	const [formTitle, setFormTitle] = useState('');
	const [formDescription, setFormDescription] = useState('');
	const [formStatus, setFormStatus] = useState('Uncompleted');

	useEffect(() => {
		setFormTitle(title);
		setFormDescription(description);
		setFormStatus(status);
	}, []);

	useEffect(() => {
		setId(currentId.userId, chosenCompilationId, currentId.taskId, currentId.isShared);
	}, [chosenCompilationId]);

	return (
		<div className="create">
			{selfCompilations && sharedCompilations && selfCompilations.length === 0
			 	&& sharedCompilations.length === 0 &&
			<>
				<h2>You have no compilations yet!</h2>
				<h2>Add compilation before adding a task!</h2>
			</>}
			{selfCompilations && sharedCompilations
				&& (selfCompilations.length > 0 || sharedCompilations.length > 0) && 
			<>
			<h2>{action} Task:</h2>
			<form onSubmit={handleSubmit(id, formTitle, formDescription, formStatus, chosenCompilationId)}>
				<label>Compilation</label>
				<select 
					value = {chosenCompilationId}
					onChange={(e) => setChosenCompilationId(e.target.value)}>
					{selfCompilations.map (compilation => (
						<option key={compilation.id} value={compilation.id}>
							{compilation.name}
						</option>
					))}
					{sharedCompilations.map (compilation => (
						<option key={compilation.id} value={compilation.id}>
							{compilation.name}
						</option>
					))}
				</select>
				<label>Task title</label>
				<input 
					required
					value = {formTitle}
					onChange={(e) => setFormTitle(e.target.value)}>
				</input>
				<label>Task description</label>
				<textarea 
					required
					value = {formDescription}
					onChange={(e) => setFormDescription(e.target.value)}>
				</textarea>
				<label>Task status: </label>
				<select 
					value = {formStatus}
					onChange={(e) => setFormStatus(e.target.value)}>
					<option value = "Completed">Completed</option>
					<option value = "In Progress">In Progress</option>
					<option value = "Uncompleted">Uncompleted</option>
				</select>
				{<button>{action} Task</button>}
			</form>
			</>}
		</div>	
	 );
}
 
export default TaskForm;