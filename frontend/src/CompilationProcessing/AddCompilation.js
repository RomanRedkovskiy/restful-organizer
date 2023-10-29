import { useEffect, useState } from 'react';
import { useId } from '../IdProvider';
import fetchData from '../Fetches/fetchData';
import { useHistory } from 'react-router-dom/cjs/react-router-dom';
import AuthorizedNavbar from '../Navbars/NavbarAuthorized';

const AddCompilation = () => {

	const history = useHistory();
	const [currentName, setCurrentName] = useState("");
	const currentId = useId();
	console.log(currentId);

	const [response, setResponse] = useState("");

	function handleAdd(currentName) {
		const compilation = {
		  name: currentName,
		  userId: currentId.userId
		};
		setResponse(fetchData('http://localhost:8080/compilations', 'POST', compilation));
	}
	useEffect(() => {
		if(response !== ""){
			history.push('/compilations');
		}
	}, [response]);


    return ( 
    <>
		
		<AuthorizedNavbar />
    	<div className="create default-layout">
      		<h2>Add Compilation:</h2>
      		<form onSubmit={(() => handleAdd(currentName))}>
        		<label>Compilation name:</label>
        		<input 
					required
					placeholder = "Enter name"
					value = {currentName}
					onChange={(e) => setCurrentName(e.target.value)}>
				</input>
				<button type="submit">Add</button>
      		</form>
    	</div>
    </>
   );
}
 
export default AddCompilation;