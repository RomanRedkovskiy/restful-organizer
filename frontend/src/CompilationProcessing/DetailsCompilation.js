import { useId, useIdUpdate } from '../IdProvider';

import TaskList from "../TaskProcessing/ListTask";
import useFetch from '../Fetches/useFetch'
import AuthorizedNavbar from "../Navbars/NavbarAuthorized";

const CompilationDetails = () => {
	const currentId = useId();
	const setId = useIdUpdate();
	console.log(currentId);
	const {data: tasks, isLoading, error} = 
		useFetch('http://localhost:8080/compilations/' + currentId.compilationId + '/tasks');
	const {data: compilation, isCompilationLoading, compilationError} = 
		useFetch('http://localhost:8080/compilations/' + currentId.compilationId);

    return ( 
		<>
		{tasks && compilation && 
		<div>
			<AuthorizedNavbar />
			<div className = "home container default-layout" key = {compilation.id}>
				{error && <h2>{error}</h2>}
				{isLoading && <h2>Loading...</h2>}
				{tasks && <TaskList tasks = {tasks} title = {compilation.name} 
					id = {compilation.id} completeness = {"Completeness: " + compilation.completeness + " %"}/>}
			</div>
		</div>
		}
		</>
    );
}
 
export default CompilationDetails;