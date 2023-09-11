import { useHistory, useParams } from "react-router-dom/cjs/react-router-dom";
import TaskForm from "./TaskForm";
import Navbar from "./Navbar";

const EditTask = () => {
const history = useHistory();
const { id } = useParams();
//const {data, isLoading, error} = useFetch('http://localhost:8080/tasks/' + id);
console.log(history.location.state);


const handleSubmit = (title, description, status) => (e) => {
	e.preventDefault();
	const task = {title, description, status};

	fetch('http://localhost:8080/tasks/' + id, {
		method: 'PUT',
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify(task)
	}).then(() => {
		console.log("task changed");
		history.push('/');
	})
}

	return ( 
		<div>
			<Navbar />
			<div className="container">
				<TaskForm 
					title = {history.location.state.title}
					description = {history.location.state.description}
					status = {history.location.state.status}
					action = 'Edit' 
					handleSubmit={handleSubmit}
				/>
			</div>
		</div>
	 );
}

export default EditTask;