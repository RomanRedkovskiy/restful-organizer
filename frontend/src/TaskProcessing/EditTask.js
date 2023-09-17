import { useHistory } from "react-router-dom/cjs/react-router-dom";
import { useState, useEffect } from "react";
import TaskForm from "./FormTask";
import { useId, useIdUpdate } from '../IdProvider';
import AuthorizedNavbar from "../Navbars/NavbarAuthorized";
import useFetch from "../Fetches/useFetch";
import fetchData from "../Fetches/fetchData";

const EditTask = () => {
	const history = useHistory();
	const currentId = useId();
	const setId = useIdUpdate();
	const [response, setResponse] = useState("");

	const {data: task, isLoading, error} =
		useFetch('http://localhost:8080/tasks/' + currentId.taskId);

	const handleEdit = (id, title, description, status, compilation_id) => (e) => {
		setId(currentId.userId, compilation_id, currentId.taskId, currentId.isShared);
		e.preventDefault();
		const editTask = {
			id: id,
			title: title,
			description: description,
			status: status,
			compilation_id: compilation_id
		};
		setResponse(fetchData('http://localhost:8080/tasks', 'PUT', editTask));
	}

	useEffect(() => {
		if(response !== ""){
			setTimeout(history.push('/compilation'), 200);
		}
	}, [response]);

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