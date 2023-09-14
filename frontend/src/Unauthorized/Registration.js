import { BrowserRouter as Router, Route, Switch, useHistory } from 'react-router-dom';
import { useId, useIdUpdate } from '../IdProvider';
import UserForm from '../UserProcessing/FormUser';
import fetchData from '../Fetches/fetchData';
import UnauthorizedNavBar from "./NavbarUnauthorized";
import { useState } from 'react';

const Registration = () => {

	const history = useHistory();
	const currentId = useId();
	const setId = useIdUpdate();
	const [errorMessage, setErrorMessage] = useState('');

	const handleRegistration = (login, password, name) => (e) => {
		let user = {login, password, name};
		e.preventDefault();
		fetchData("http://localhost:8080/users", "POST", user).then(data => {
			if(data.id === -1){
				console.log('error!');
				setErrorMessage('User with this name already exists!');
			} else {
				setId(data.id, currentId.compilationId, currentId.taskId);
				setErrorMessage('');
				history.push('/compilations');
			}
		})
		//const {data: tasks, isLoading, error} = useFetch('http://localhost:8080/tasks', 'POST', user);
	}

	return(
		<div>
			<UnauthorizedNavBar />
			<UserForm
				formText = "Registration"
				handler = {handleRegistration}
			/>
			<div className="content create not-found"> {errorMessage} </div>
		</div>
	);
  };

  export default Registration;