import { Link, useHistory, useParams } from "react-router-dom/cjs/react-router-dom";
import useFetch from "./useFetch";

const TaskDetails = () => {
	const { id } = useParams();
	const {data: task, isLoading, error} = useFetch('http://localhost:8080/tasks/' + id);
	const history = useHistory();
	const handleDelete = () => {
		fetch('http://localhost:8080/tasks/' + id, {
			method: 'DELETE',
			headers: {
				"Access-Control-Allow-Headers" : "Content-Type",
				"Access-Control-Allow-Origin": "*",
			    'Content-Type': 'application/json',
				"Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH"
			}
		}).then(() => {
			history.push('/');
		})
	};
	return ( 
		<div className="task-details">
			{isLoading && <div>Loading...</div>}
			{error && <div>{error}</div>}
			{task && (
				<div>
					<article>
						<h1> {task.title} </h1>
						<h2> Description: </h2>
						<p> {task.description} </p>
						<h2> Status: </h2>
						<p> {task.status} </p>
					</article>
					<button onClick = {handleDelete}>Delete Task</button>
					<Link
						to={{
							pathname: `/edit-task/${task.id}`,
							state: task // your data array of objects
						}}>
						<button>Edit Task</button>
					</Link>
				</div>
			)}
		</div>
	 );
}
 
export default TaskDetails;