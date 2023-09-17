import NewTask from './TaskProcessing/AddTask';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import TaskDetails from './TaskProcessing/DetailsTask';
import EditTask from './TaskProcessing/EditTask';
import DeleteTask from './TaskProcessing/DeleteTask';
import NotFound from './NotFound';
import StartPage from './Unauthorized/StartPage';
import {useState} from 'react';
import Registration from './Unauthorized/Registration';
import Login from './Unauthorized/Login';
import {IdProvider} from './IdProvider';
import CompilationProcessor from './CompilationProcessing/ProcessorCompilation';
import CompilationDetails from './CompilationProcessing/DetailsCompilation';
import CompilationShare from './CompilationProcessing/ShareCompilation';
import CompilationList from './CompilationProcessing/ListCompilation';
import CompilationDelete from './CompilationProcessing/DeleteCompilation';
import CompilationEdit from './CompilationProcessing/EditCompilation';
import AddCompilation from './CompilationProcessing/AddCompilation';

function App() {
  return (
	<IdProvider>
    <Router>
      <div className="App">
			<Switch>
				<Route exact path="/">
					<StartPage />
				</Route>
				<Route exact path="/new-task">
					<NewTask />
				</Route>
				<Route exact path="/task">
					<TaskDetails />
				</Route>
				<Route exact path="/edit-task">
					<EditTask />
				</Route>
				<Route exact path="/delete-task">
					<DeleteTask />
				</Route>
				<Route exact path="/compilation">
					<CompilationDetails />
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
				<Route exact path="/compilation-details">
					<CompilationDetails />
				</Route>
				<Route exact path="/new-compilation">
					<AddCompilation />
				</Route>
				<Route exact path="/share-compilation">
					<CompilationShare />
				</Route>
				<Route exact path="/edit-compilation">
					<CompilationEdit />
				</Route>
				<Route exact path="/delete-compilation">
					<CompilationDelete />
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