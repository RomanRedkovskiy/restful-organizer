import { Link } from 'react-router-dom'

const RegistrationNavBar = () => {
	return ( 
		<nav className="navbar">
			<Link to="/">
				<h1>RESTful organizer</h1>
			</Link>
            <div className="links">
                <Link to="/">Log in</Link>
                <Link to="/new_task" style = {{
					color: "white",
					backgroundColor: '#f1365d',
					borderRadius: '8px'
				}}>Sign Up</Link>
            </div>
        </nav>
	 );
}
 
export default RegistrationNavBar;