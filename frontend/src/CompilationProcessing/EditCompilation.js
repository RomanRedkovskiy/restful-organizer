import { useState, useEffect } from 'react';
import { useId } from '../IdProvider';
import useFetch from '../Fetches/useFetch';
import fetchData from '../Fetches/fetchData';
import { useHistory } from 'react-router-dom/cjs/react-router-dom';
import AuthorizedNavbar from '../Authorized/NavbarAuthorized';

const CompilationEdit = () => {

	const history = useHistory();
	const [currentName, setCurrentName] = useState(null);
	const currentId = useId();

	function handleEdit(currentName){
		const userCompilation = {
		  name: currentName,
		  compilation_id: currentId.compilationId,
		  user_id: currentId.userId,
		  is_shared: currentId.isEditable,
		  read_only: false
		};
		// Use then method to wait for fetch to finish
		fetchData('http://localhost:8080/update_compilation', 'PUT', userCompilation)
  			.then (() => setTimeout(history.push('/compilations'), 2000));
	  }

	const {data: compilation, isLoading, error} =
    useFetch('http://localhost:8080/compilations/' + currentId.compilationId);

	useEffect(() => {
		if (compilation) { // Check if compilation is not null
		  setCurrentName(compilation.name); // Update current name when compilation changes
		}
	  }, [compilation]); // Add compilation as a dependency

    return ( 
    <>
		
		<AuthorizedNavbar />
		{currentName !== null && ( 
    	<div className="create">
      		<h2>Edit Compilation:</h2>
      		<form onSubmit={(() => handleEdit(currentName))}>
        		<label>Compilation name</label>
        		<input 
					required
					value = {currentName}
					onChange={(e) => setCurrentName(e.target.value)}>
				</input>
				<button type="submit">Edit Name</button>
      		</form>
    	</div>
		)};
    </>
   );
}
 
export default CompilationEdit;