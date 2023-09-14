import { useHistory } from "react-router-dom/cjs/react-router-dom";
import TaskForm from "./FormTask";
import AuthorizedNavbar from "../Authorized/NavbarAuthorized";

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
		<div>
			<AuthorizedNavbar />
			<div className="content">
				<TaskForm 
					title = '' 
					description = '' 
					status = 'Uncompleted' 
					action = 'Add' 
					handleSubmit={handleSubmit}
				/>
			</div>
		</div>
	 );
}
 
export default NewTask;