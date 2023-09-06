import Navbar from './Navbar';
import Home from './Home';
import NewTask from './NewTask';
import RegistrationForm from './RegistrationForm'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import TaskDetails from './TaskDetails';
import EditTask from './EditTask';
import NotFound from './NotFound';
import{useState} from 'react';

function App() {
  return (
    <Router>
      <div className="App">
        <Switch>
			<Route exact path="/">
				<RegistrationForm />
			</Route>
			<Navbar />
			<div className="content">
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
			</div>
        </Switch>
    </div>
    </Router>
  );
}

export default App;