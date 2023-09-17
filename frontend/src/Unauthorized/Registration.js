import { BrowserRouter as Router, Route, Switch, useHistory } from 'react-router-dom';
import { useId, useIdUpdate } from '../IdProvider';
import UserForm from '../UserProcessing/FormUser';
import fetchData from '../Fetches/fetchData';
import UnauthorizedNavBar from "../Navbars/NavbarUnauthorized";
import { useState, useEffect } from 'react';

const Registration = () => {

	const history = useHistory();
	const currentId = useId();
	const setId = useIdUpdate();

	const [errorMessage, setErrorMessage] = useState('');
	const [data, setData] = useState('');

	useEffect(() => {
		if(data) {
			if(data.id === -1){
				console.log('error!');
				setErrorMessage('User with this name already exists!');
			} else {
				setId(data.id, currentId.compilationId, currentId.taskId, currentId.isShared);
				setErrorMessage('');
				history.push('/compilations');
			}
		}
	}, [data]);

	const handleRegistration = (login, password, name) => (e) => {
		let user = {login, password, name};
		e.preventDefault();
		fetch("http://localhost:8080/users", {
			method: "POST",
			headers: {
				"Access-Control-Allow-Headers" : "Content-Type",
				"Access-Control-Allow-Origin": "*",
				'Content-Type': 'application/json',
				"Access-Control-Allow-Methods": "OPTIONS,POST,GET,PUT,DELETE",
				"Cache-Control": "no-cache"
			},
			body: JSON.stringify(user)
		}).then(res => {
			if(!res.ok){
				throw Error('Could not fetch data for a server');
			}
			return res.json();
		}).then(fetchedData => {
			setData(fetchedData);
			console.log(fetchedData);
		});
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