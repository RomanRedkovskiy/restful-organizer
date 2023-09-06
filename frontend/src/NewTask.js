import { useHistory } from "react-router-dom/cjs/react-router-dom";
import TaskForm from "./TaskForm";

const NewTask = () => {
	const history = useHistory();

	const handleSubmit = (title, description, status) => (e) => {
		e.preventDefault();
		const task = {title, description, status};

		fetch('http://localhost:8080/tasks', {
			method: 'POST',
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(task)
		}).then(() => {
			console.log("new task added");
			history.push('/');
		})
	}

	return ( 
		<TaskForm 
			title = '' 
			description = '' 
			status = 'Uncompleted' 
			action = 'Add' 
			handleSubmit={handleSubmit}/>
	 );
}
 
export default NewTask;