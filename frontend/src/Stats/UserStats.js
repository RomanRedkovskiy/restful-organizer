import { useId } from '../IdProvider';
import useFetch from '../Fetches/useFetch';

function UserStats({ userStat }) {
	const currentId = useId();
	return (
	 <>
		<div className = "statistic compilation-container">
			<div>
				<h2 style={{ paddingTop: 0, marginBottom: 0, paddingBottom: 45 }}>Username: 
					<strong> {userStat.userName}</strong>
				</h2>
				<h2 style={{ marginBottom: 0, paddingBottom: 47 }}>Task Completeness:
					<strong> {userStat.overallCompleteness}%</strong>
				</h2>
			</div>
			<div>
				<h2 style={{ marginBottom: 0, paddingBottom: 8 }}>Overall tasks:
					<strong> {userStat.overallTasks}</strong></h2>
				<h2 style={{ marginBottom: 0, paddingBottom: 8, color: '#2ac82ac5'}}>Completed tasks: 
					<strong style={{color: '#2ac82ac5'}}> {userStat.completedTasks}</strong></h2>
				<h2 style={{ marginBottom: 0, paddingBottom: 8, color: '#c1cc29e8'}}>In Progress tasks:
					<strong style={{color: '#c1cc29e8'}}> {userStat.inProgressTasks}</strong></h2>
				<h2 style={{ marginBottom: 0, paddingBottom: 8, color: '#f1356d'}}>Uncompleted tasks:
					<strong style={{color: '#f1356d'}}> {userStat.uncompletedTasks}</strong></h2>
			</div>
		</div>
		
	 	{/* <div class="flex-wrapper">
		<div class="single-chart">
			<svg viewBox="0 0 36 36" className="circular-chart orange">
			<path className="circle-bg"
				d="M18 2.0845
				a 15.9155 15.9155 0 0 1 0 31.831
				a 15.9155 15.9155 0 0 1 0 -31.831"
			/>
			<path className="circle"
				strokeDasharray="30, 100"
				d="M18 2.0845
				a 15.9155 15.9155 0 0 1 0 31.831
				a 15.9155 15.9155 0 0 1 0 -31.831"
			/>
			<text x="18" y="20.35" className="percentage">30%</text>
			</svg>
		</div>
		
		<div class="single-chart">
			<svg viewBox="0 0 36 36" className="circular-chart green">
			<path className="circle-bg"
				d="M18 2.0845
				a 15.9155 15.9155 0 0 1 0 31.831
				a 15.9155 15.9155 0 0 1 0 -31.831"
			/>
			<path className="circle"
				strokeDasharray="60, 100"
				d="M18 2.0845
				a 15.9155 15.9155 0 0 1 0 31.831
				a 15.9155 15.9155 0 0 1 0 -31.831"
			/>
			<text x="18" y="20.35" className="percentage">60%</text>
			</svg>
		</div>

		<div class="single-chart">
			<svg viewBox="0 0 36 36" className="circular-chart blue">
			<path className="circle-bg"
				d="M18 2.0845
				a 15.9155 15.9155 0 0 1 0 31.831
				a 15.9155 15.9155 0 0 1 0 -31.831"
			/>
			<path class="circle"
				strokeDasharray="90, 100"
				d="M18 2.0845
				a 15.9155 15.9155 0 0 1 0 31.831
				a 15.9155 15.9155 0 0 1 0 -31.831"
			/>
			<text x="18" y="20.35" className="percentage">90%</text>
			</svg>
		</div>
		</div> */}
	 </> 
	);
}
 
export default UserStats;