import classNames from "classnames";
import { Link } from "react-router-dom";
import { useId, useIdUpdate } from '../IdProvider';
import { useState, useEffect } from "react";
import useFetch from "../Fetches/useFetch";
import { BiEdit } from 'react-icons/bi';
import {RiDeleteBinLine} from 'react-icons/ri';

function TaskList({ tasks, title, id, completeness }) {

	const currentId = useId();
	const setId = useIdUpdate();
	const [isReadOnly, setIsReadOnly] = useState(true);
	const {data: readonlyCompilations, isReadonlyLoading, readonlyError} =
		useFetch('http://localhost:8080/users/' + currentId.userId + '/readonly_compilations');

	
	useEffect(() => {
		if (readonlyCompilations) {
			setIsReadOnly(readonlyCompilations.some ( (object) => object.id === id));
		}
	},[readonlyCompilations]);

	console.log(readonlyCompilations);
	function truncateTextWithEllipsis(text, length) {
		if (text.length <= length) {
		  return text;
		}
	  
		return text.substr(0, length) + '\u2026'
	}
	
	function handleTaskChoice(id){
		setId(currentId.userId, currentId.compilationId, id, currentId.isShared);
	}

	return (
		<div className="flexible-container">
			<h2 className="compilation-name">{title}</h2>
			{tasks.length > 0 && (
				<h3 className="compilation-name">{completeness}</h3>
			)}
			<Link to = "/new-task">
				<button className="add-task-btn">Add task</button>
			</Link>
			{tasks.length > 0 && (
			<>
			{tasks.map((task) => (
				<div className = {classNames('entity-container task-container',
					{'medium-completeness' : task.status === 'In Progress'},
					{'done-completeness' :   task.status === 'Completed'},
				)} key={task.id}>
					<Link onClick = {() => handleTaskChoice(task.id)} to = "/task">
						<h2>{truncateTextWithEllipsis(task.title, 35)}</h2>
						<p> {truncateTextWithEllipsis(task.description, 50)}</p>
					</Link>
					{!isReadOnly && <>
						<Link onClick = {() => handleTaskChoice(task.id)} to = "/edit-task">
							<button type = "button" className = "small-button edit-button" style = {{fontSize: 17, top: 12, minWidth: 95}}>
								<span className="small-button-text">Edit</span>
								<span className="small-button-icon">
									<BiEdit />
								</span>
							</button>
						</Link>
						<Link onClick = {() => handleTaskChoice(task.id)} to = "/delete-task">
							<button type = "button" className = "small-button delete-button" style = {{fontSize: 17, top: 52, minWidth: 95}}>
								<span className="small-button-text">Delete</span>
								<span className="small-button-icon">
									<RiDeleteBinLine />
								</span>
							</button>
						</Link>
					</>}
				</div>
			))}</>)}
		</div>
	);
}
 
export default TaskList;