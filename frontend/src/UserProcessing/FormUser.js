import { useState } from "react";

function UserForm ({formText, handler}) {
	const [login, setLogin] = useState('');
	const [password, setPassword] = useState('');
	const [name, setName] = useState('');
	return ( 
		<div className="content create">
			<h2>{formText}</h2>
			<form onSubmit={handler(login, password, name)}>
				<label>Username:</label>
				<input 
					required
					value = {name}
					onChange={(e) => setName(e.target.value)}>
				</input>
				<label>Login</label>
				<input 
					type = "login"
					required
					value = {login}
					onChange={(e) => setLogin(e.target.value)}>
				</input>
				<label>Password:</label>
				<input 
					type = "password"
					required
					value = {password}
					onChange={(e) => setPassword(e.target.value)}>
				</input>
				{<button>{formText}</button>}
			</form>
		</div>
	 );
}
 
export default UserForm;