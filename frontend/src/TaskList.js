import classNames from "classnames";
import { Link } from "react-router-dom"

function truncateTextWithEllipsis(text, length) {
	if (text.length <= length) {
	  return text;
	}
  
	return text.substr(0, length) + '\u2026'
}

function TaskList({ tasks, title }) {
	return (
		<div className="task-list">
			<h2>{title}</h2>
			{tasks.map((task) => (
				<div className= {classNames('task-preview',
				 				{'completed' : task.status === "Completed"},
								{'inprogress' : task.status === "In Progress"}
								)} key={task.id}>

					<Link to = {`/tasks/${task.id}`}>
						<h2>{task.title}</h2>
						<p> {truncateTextWithEllipsis(task.description, 137)}</p>
					</Link>
				</div>
			))}
		</div>
	);
}
 
export default TaskList;