import { useId } from '../IdProvider';
import AdminStats from './AdminStats';
import AuthorizedNavbar from '../Navbars/NavbarAuthorized';
import UserStats from './UserStats';
import useFetch from '../Fetches/useFetch';
import { useEffect, useState } from 'react';

const StatsPage = () => {
	const currentId = useId();
	const [isFetched, setIsFetched] = useState(false);
	let url = 'http://localhost:8082/stats';
	const {data: isAdmin, isLoading, error} = useFetch('http://localhost:8080/users/' + currentId.userId + '/is_admin');
	
	useEffect(() => {
		if(!isAdmin){
			url += '/' + currentId.userId;
		}
		const timer = setTimeout(() => {
			setIsFetched(true);
			console.log(isFetched);
		}, 200);
		return () => clearTimeout(timer);
	}, [isAdmin]);

	if(!isAdmin) {
		url += '/' + currentId.userId;
	}

	const {data: stats, statLoading, statError} = useFetch(url);

	return ( 
		<>
		<AuthorizedNavbar />
		{(error || statError) && <h2>{error}</h2>}
		{(isLoading || statLoading) && <h2>Loading...</h2>}
		{isAdmin && stats && isFetched && 
			<div>
				<h2 className="content" style={{ marginLeft: 270, marginBottom: 0}}>Users Statistic:</h2>
				<AdminStats adminStat={stats}/>
			</div>}
		{!isAdmin && stats && isFetched &&
			<div className="list-preview">
				<h2 className="content" style={{ marginLeft: 270, marginBottom: 0 }}>Personal statistic:</h2>
				<UserStats userStat={stats}/>
			</div>}
		</>
	 );
}
 
export default StatsPage;