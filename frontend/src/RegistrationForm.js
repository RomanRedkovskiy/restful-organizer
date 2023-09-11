import { BrowserRouter as Router, Route, Switch, useHistory } from 'react-router-dom';
import UserForm from './UserForm';

const RegistrationForm = () => {

	const history = useHistory();

	const handleLogin = (login, password, name) => (e) => {
		e.preventDefault();
		const user = {login, password, name};

		fetch('http://localhost:8080/users', {
			method: 'POST',
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(user)
		}).then(() => {
			console.log("new user added");
			history.push('/home_page');
		})
	}

	return(
		<div>
			<UserForm
				formText = "Registration"
				handler = {handleLogin}
			/>
		</div>
	);
  };

  export default RegistrationForm;