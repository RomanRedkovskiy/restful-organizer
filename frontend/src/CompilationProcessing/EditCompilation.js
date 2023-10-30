import { useState, useEffect } from 'react';
import { useId } from '../IdProvider';
import useFetch from '../Fetches/useFetch';
import fetchData from '../Fetches/fetchData';
import { useHistory } from 'react-router-dom/cjs/react-router-dom';
import AuthorizedNavbar from '../Navbars/NavbarAuthorized';

const CompilationEdit = () => {

	const history = useHistory();
	const [currentName, setCurrentName] = useState(null);
	const currentId = useId();
	console.log(currentId);

	const [response, setResponse] = useState("");

	function handleEdit(currentName) {
		const userCompilation = {
		  name: currentName,
		  compilationId: currentId.compilationId,
		  userId: currentId.userId,
		  isShared: currentId.isShared,
		  readOnly: false
		};
		setResponse(fetchData('http://localhost:8080/update_compilation', 'PUT', userCompilation));
	}
	useEffect(() => {
		if(response !== ""){
			history.push('/compilations');
		}
	}, [response]);



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
    	<div className="create default-layout">
      		<h2>Edit Compilation:</h2>
      		<form onSubmit={(() => handleEdit(currentName))}>
        		<label>Compilation name:</label>
        		<input 
					required
					value = {currentName}
					onChange={(e) => setCurrentName(e.target.value)}>
				</input>
				<button type="submit">Edit</button>
      		</form>
    	</div>
		)};
    </>
   );
}
 
export default CompilationEdit;