import classNames from "classnames";
import { Link } from "react-router-dom"
import { useId, useIdUpdate } from '../IdProvider';
import { BiEdit, BiShareAlt } from 'react-icons/bi';
import {RiDeleteBinLine} from 'react-icons/ri';

function CompilationList({ compilations, title, isSelf, isShared }) {

	function handleCompilationChoice(id){
		setId(currentId.userId, id, currentId.taskId, isShared);
	}

	const currentId = useId();
	const setId = useIdUpdate();

	function truncateTextWithEllipsis(text, length) {
		if (text.length <= length) {
		  return text;
		}
	  
		return text.substr(0, length) + '\u2026'
	}
	return (
		<>
		<h2>{title}</h2>
		<div className="list-preview">
			{compilations.map((compilation) => (
				<div className = {classNames('compilation-container compilation',
					{'low-completeness' : 	 compilation.completeness >= 20 && compilation.completeness < 40},
					{'medium-completeness' : compilation.completeness >= 40 && compilation.completeness < 70},
					{'high-completeness' : 	 compilation.completeness >= 70 && compilation.completeness < 100},
					{'done-completeness' :   compilation.completeness === 100},
					)} key = {compilation.id}>
					<Link onClick = {() => handleCompilationChoice(compilation.id)} to = "/compilation">
						{isSelf && <h2>{truncateTextWithEllipsis(compilation.name, 18)}</h2>}
						{!isSelf && <h2>{truncateTextWithEllipsis(compilation.name, 20)}</h2>}
						<h3>Completeness: {compilation.completeness} %</h3>
					</Link>
					{isSelf && <>
					<Link onClick = {() => handleCompilationChoice(compilation.id)} to = "/edit-compilation">
						<button type = "button" className = "small-button edit-button">
							<span className="small-button-text">Edit</span>
							<span className="small-button-icon">
								<BiEdit />
							</span>
						</button>
					</Link>
					<Link onClick = {() => handleCompilationChoice(compilation.id)} to = "/share-compilation">
						<button type = "button" className = "small-button share-button">
							<span className="small-button-text">Share</span>
							<span className="small-button-icon">
								<BiShareAlt />
							</span>
						</button>
					</Link>
					<Link onClick = {() => handleCompilationChoice(compilation.id)} to = "/delete-compilation">
						<button type = "button" className = "small-button delete-button">
							<span className="small-button-text">Delete</span>
							<span className="small-button-icon">
								<RiDeleteBinLine />
							</span>
						</button>
					</Link>
					</>}
				</div>
			))}
		</div>
		</>
	);
}
 
export default CompilationList;