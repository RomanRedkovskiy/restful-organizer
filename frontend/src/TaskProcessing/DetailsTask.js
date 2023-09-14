import { Link, useHistory } from "react-router-dom/cjs/react-router-dom";
import useFetch from "../Fetches/useFetch";
import AuthorizedNavbar from "../Authorized/NavbarAuthorized";
import { useId } from '../IdProvider';
import fetchNoData from "../Fetches/fetchNoData";

const TaskDetails = () => {
	const currentId = useId();
	const {data: task, isLoading, error} = useFetch('http://localhost:8080/tasks/' + currentId.taskId);
	const history = useHistory();
	const handleDelete = () => {

	};
	return ( 
		<div>
			<AuthorizedNavbar />
			<div className="task-details content">
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
						<Link to = "/compilation">
							<button>Back to tasks</button>
						</Link>
					</div>
				)}
			</div>
		</div>
	 );
}
 
export default TaskDetails;