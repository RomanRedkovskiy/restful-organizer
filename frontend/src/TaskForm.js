import { useEffect, useState } from "react";

function TaskForm ({title, description, status, action, handleSubmit}) {
	const [formTitle, setFormTitle] = useState('');
	const [formDescription, setFormDescription] = useState('');
	const [formStatus, setFormStatus] = useState('Uncompleted');
	useEffect(() => {
		setFormTitle(title);
		setFormDescription(description);
		setFormStatus(status);
	}, []);
	return ( 
		<div className="create">
			<h2>{action} Task:</h2>
			<form onSubmit={handleSubmit(formTitle, formDescription, formStatus)}>
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
		</div>
	 );
}
 
export default TaskForm;