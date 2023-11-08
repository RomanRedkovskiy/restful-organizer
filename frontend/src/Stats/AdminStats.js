import UserStats from "./UserStats";

function AdminStats({ adminStat }) {
	console.log("ADMIN STATS:");
	console.log(adminStat);
	return ( 
	<>
		<div className="list-preview">
			{adminStat.map((userStat) => (
				<UserStats userStat={userStat} key={userStat.id}/>
			))}
		</div>
	</> 
	);
}
 
export default AdminStats;