import { useHistory } from "react-router-dom/cjs/react-router-dom";
import TaskForm from "./FormTask";
import { useId } from '../IdProvider';
import AuthorizedNavbar from "../Authorized/NavbarAuthorized";
import useFetch from "../Fetches/useFetch";
import fetchData from "../Fetches/fetchData";

const EditTask = () => {
	const history = useHistory();
	const currentId = useId();

	const {data: task, isLoading, error} =
		useFetch('http://localhost:8080/tasks/' + currentId.taskId);

	const handleEdit = (id, title, description, status, compilationId) => (e) => {
		e.preventDefault();
		const editTask = {
			id: id,
			title: title,
			description: description,
			status: status,
			compilation_id: compilationId
		};
		console.log(editTask);
		// Use then method to wait for fetch to finish
		fetchData('http://localhost:8080/tasks', 'PUT', editTask)
		.then (() => setTimeout(history.push('/compilations'), 2000));
	}
	return ( 
		<div>
			<AuthorizedNavbar />
			{task && (
			<div className="container">
				<TaskForm 
					id = {task.id}
					title = {task.title}
					description = {task.description}
					status = {task.status}
					compilationId = {task.compilation_id}
					action = 'Edit' 
					handleSubmit={handleEdit}
				/>
			</div>
			)}
		</div>
	 );
}

export default EditTask;