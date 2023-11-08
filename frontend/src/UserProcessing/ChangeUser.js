import { BrowserRouter as Router, Route, Switch, useHistory } from 'react-router-dom';
import { useId } from '../IdProvider';
import UserForm from './FormUser';
import useFetch from '../Fetches/useFetch';
import AuthorizedNavbar from '../Navbars/NavbarAuthorized';
import { useEffect, useState } from 'react';

const ChangeUser = () => {

  const history = useHistory();
  const currentId = useId();
  const [data, setData] = useState();
  const {data: currentUserData, isLoading, error} = useFetch('http://localhost:8081/users/' + currentId.userId);

  useEffect(() => {
    if(data){
      if(data.id === -1){
        console.log('error!');
      } else {
        history.push('/compilations');
      }
    }
  }, [data]);

  const handleChange = (login, password, name) => (e) => {
    let user = {id: currentId.userId, login, password, name};
	console.log(user);
    e.preventDefault();
	fetch("http://localhost:8081/users", {
		method: "PUT",
		headers: {
			"Authorization": localStorage.getItem('Token'),
			"Access-Control-Allow-Headers" : "Content-Type",
			"Access-Control-Allow-Origin": "*",
			'Content-Type': 'application/json',
			"Access-Control-Allow-Methods": "OPTIONS,POST",
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
	});
  }

  return(
    <>
	{error && <h2>{error}</h2>}
	{isLoading && <h2>Loading...</h2>}
	{currentUserData &&
	<>
		<AuthorizedNavbar />
		<UserForm
			initialLogin = {currentUserData.login}
			initialPassword = {currentUserData.password}
			initialName = {currentUserData.name}
			formText = "Change"
			handler = {handleChange}
		/>
	</>
	}
    </>
  );
  };

  export default ChangeUser;