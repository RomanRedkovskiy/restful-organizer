import Home from './TaskProcessing/TaskProcessor';
import NewTask from './TaskProcessing/NewTask';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import TaskDetails from './TaskProcessing/TaskDetails';
import EditTask from './TaskProcessing/EditTask';
import NotFound from './NotFound';
import StartPage from './Unauthorized/StartPage';
import {useState} from 'react';
import Registration from './Unauthorized/Registration';
import Login from './Unauthorized/Login';
import {IdProvider} from './IdProvider';
import CompilationProcessor from './CompilationProcessing/CompilationProcessor';

function App() {
  return (
	<IdProvider>
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
				<Route exact path="/registration">
					<Registration />
				</Route>
				<Route exact path="/login">
					<Login />
				</Route>
				<Route exact path="/compilations">
					<CompilationProcessor />
				</Route>
				<Route path="*">
					<NotFound />
				</Route>
			</Switch>
    	</div>
    </Router>
	</IdProvider>
  );
}

export default App;