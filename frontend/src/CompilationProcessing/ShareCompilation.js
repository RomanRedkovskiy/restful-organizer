import { useHistory } from 'react-router-dom/cjs/react-router-dom';
import { useId } from '../IdProvider';
import { useEffect, useState } from 'react';
import useFetch from '../Fetches/useFetch';
import fetchData from '../Fetches/fetchData';
import AuthorizedNavbar from '../Navbars/NavbarAuthorized';

const CompilationShare = () => {
	const [response, setResponse] = useState("");

	function handleShare(){
		const userCompilation = {
		  name: currentCompilation.name,
		  compilationId: currentCompilation.id,
		  userId: selectedId,
		  shared: true,
		  readOnly: isReadOnly
		};
		// Use then method to wait for fetch to finish
		setResponse(fetchData('http://localhost:8080/update_compilation', 'PUT', userCompilation));
	}

	useEffect(() => {
		if(response !== ""){
			console.log("RESPONSE:");
			console.log(response);
			history.push('/compilations');
		}
	}, [response]);

	function handleChange () {
		// Update the state with the opposite value of the current state
		setIsReadOnly (!isReadOnly);
	}

	const currentId = useId();
	const history = useHistory();
	const {data: users, isLoading, error} =
	useFetch('http://localhost:8080/users/except/' + currentId.userId);
	const {data: currentCompilation, isCompilationLoading, compilationError} = 
	useFetch('http://localhost:8080/compilations/' + currentId.compilationId);	

	const [selectedId, setSelectedId] = useState();
	const [isReadOnly, setIsReadOnly] = useState(false);

	useEffect(() => {
		if(users !== null){
			setSelectedId(users[0].id);
		}
	}, [users]);

	return ( 
		<>
		<AuthorizedNavbar />
		{(isLoading || isCompilationLoading) && <div>Loading...</div>}
		{(error || compilationError) && <div>{error} {compilationError}</div>}
		{users && currentCompilation && (
		<div className="create default-layout">
			<h2>Share "{currentCompilation.name}" Compilation:</h2>
			<form onSubmit={handleShare}>
				<label>Choose user:</label>
				<select 
					value = {selectedId}
					onChange={(e) => setSelectedId(e.target.value)}>
					{users.map (user => (
						<option key={user.id} value={user.id}>
							{user.name}
						</option>
					))}
				</select>
				<div className = "checkbox-container">
					<input className= "checkbox-button" type="checkbox" checked={isReadOnly} onChange={handleChange} id = "cb"/>
					<label className= "checkbox-text" htmlFor="cb">Read-Only</label>
				</div>
				{<button style={{ width: 160 }}>Share Compilation</button>}
			</form>
		</div>
		)}
		</>
	);
}
 
export default CompilationShare;