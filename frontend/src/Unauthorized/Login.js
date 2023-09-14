import { BrowserRouter as Router, Route, Switch, useHistory } from 'react-router-dom';
import { useId, useIdUpdate } from '../IdProvider';
import UserForm from '../UserProcessing/FormUser';
import fetchData from '../Fetches/fetchData';
import UnauthorizedNavBar from "./NavbarUnauthorized";
import { useState } from 'react';

const Login = () => {

	const history = useHistory();
	const currentId = useId();
	const setId = useIdUpdate();
	const [errorMessage, setErrorMessage] = useState('');

	const handleLogin = (login, password, name) => (e) => {
		let user = {login, password, name};
		e.preventDefault();
		fetchData("http://localhost:8080/users/login", "POST", user).then(data => {
			if(data.id === -1){
				console.log('error!');
				setErrorMessage('Incorrect data, try again!');
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
				formText = "Login"
				handler = {handleLogin}
			/>
			<div className="content create not-found"> {errorMessage} </div>
		</div>
	);
  };

  export default Login;