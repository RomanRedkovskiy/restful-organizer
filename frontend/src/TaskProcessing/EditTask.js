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
		fetch('http://localhost:8080/tasks', {
			method: "PUT",
			headers: {
				"Authorization": localStorage.getItem('Token'),
				"Access-Control-Allow-Headers" : "Content-Type, Authorization",
				"Access-Control-Expose-Headers": "Authorization",
				"Access-Control-Allow-Origin": "*",
				'Content-Type': 'application/json',
				"Access-Control-Allow-Methods": "OPTIONS, PUT"
			},
			body: JSON.stringify(editTask)
		}).then(res => {
			if(!res.ok){
				throw Error('Could not fetch data for a server');
			}
			return res.json();
		}).then(fetchedData => {
			setResponse(fetchedData);
			console.log(fetchedData);
		});
	}

	useEffect(() => {
		if(response !== ""){
			history.push('/compilation');
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