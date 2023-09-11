import TaskList from "./TaskList";
import useFetch from './useFetch'
import Navbar from "./Navbar";

const Home = () => {

	const {data: tasks, isLoading, error} = useFetch('http://localhost:8080/tasks')

    return ( 
		<div>
			<Navbar />
			<div className="home container">
				{error && <h2>{error}</h2>}
				{isLoading && <h2>Loading...</h2>}
				{tasks && <TaskList tasks = {tasks} title = "Current stack:"/>}
				{ /*<TaskList tasks = {tasks.filter((task) => task.author === 'romanidus')} title = "romanidus' tasks:" onDelete={setOnDelete}/> */}
			</div>
		</div>
    );
}
 
export default Home;