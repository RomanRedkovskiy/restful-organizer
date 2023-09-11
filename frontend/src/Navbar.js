import { Link } from 'react-router-dom'

const Navbar = () => {
    return ( 
        <nav className="navbar">
			{/* <Link to="/"> */}
				<h1>RESTful organizer</h1>
			{/* </Link> */}
            <div className="links">
                <Link to="/">Home</Link>
                <Link to="/new_task" style = {{
					color: "white",
					backgroundColor: '#f1365d',
					borderRadius: '8px'
				}}>New Task</Link>
            </div>
        </nav>
     );
}
 
export default Navbar;