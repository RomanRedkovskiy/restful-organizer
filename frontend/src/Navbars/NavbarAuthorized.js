import { Link } from 'react-router-dom'
import { useId, useIdUpdate } from '../IdProvider';

const AuthorizedNavbar = () => {

	function handleLogout(){
		setId(-1, -1, -1, false);
	}

	const currentId = useId();
	const setId = useIdUpdate();

    return ( 
        <nav className="navbar">
			<Link to="/compilations">
				<h1>RESTful organizer</h1>
			</Link>
			<div className="links">
				<div className="dropdown">
					<p>New</p>
					<div className = "dropdown-content">
						<Link to = "/new-compilation">Compilation</Link>
						<Link to = "/new-task"> Task</Link>
					</div>
				</div> 
                <Link to = "/">Stats</Link>
                <Link onClick = {handleLogout} to="/" style = {{
					color: "white",
					backgroundColor: '#f1365d',
					borderRadius: '8px'
				}}>Log out</Link>
            </div>
        </nav>
     );
}
 
export default AuthorizedNavbar;