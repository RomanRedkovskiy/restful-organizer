import { useHistory } from "react-router-dom/cjs/react-router-dom";
import TaskForm from "./FormTask";
import AuthorizedNavbar from "../Navbars/NavbarAuthorized";
import { useId, useIdUpdate } from "../IdProvider";

const NewTask = () => {
	const history = useHistory();
	const currentId = useId();
	const setId = useIdUpdate();

	const handleAdd = (id, title, description, status, compilation_id) => (e) => {
		setId(currentId.userId, compilation_id, currentId.taskId, currentId.isShared);
		e.preventDefault();
		const task = {		
			id: id,
			title: title,
			description: description,
			status: status,
			compilation_id: compilation_id
		};
		fetch('http://localhost:8080/tasks', {
			method: 'POST',
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(task)
		}).then(() => {
			console.log("new task added");
			history.push('/compilation');
		})
	}

	return ( 
		<div>
			<AuthorizedNavbar />
			<div className="content">
				<TaskForm 
					title = '' 
					description = '' 
					status = 'Uncompleted' 
					compilationId = {currentId.compilationId}
					action = 'Add' 
					handleSubmit={handleAdd}
				/>
			</div>
		</div>
	 );
}
 
export default NewTask;