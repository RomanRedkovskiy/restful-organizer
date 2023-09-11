import Navbar from './Navbar';
import Home from './Home';
import NewTask from './NewTask';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import TaskDetails from './TaskDetails';
import EditTask from './EditTask';
import NotFound from './NotFound';
import StartPage from './StartPage';
import {useState} from 'react';

function App() {
  return (
    <Router>
      <div className="App">
			<Switch>
				<Route exact path="/">
					<StartPage />
				</Route>
				<Route exact path="/new_task">
					<NewTask />
				</Route>
				<Route exact path="/tasks/:id">
					<TaskDetails />
				</Route>
				<Route exact path="/edit-task/:id">
					<EditTask />
				</Route>
				<Route exact path="/home_page">
					<Home />
				</Route>
				<Route path="*">
					<NotFound />
				</Route>
			</Switch>
    	</div>
    </Router>
  );
}

export default App;