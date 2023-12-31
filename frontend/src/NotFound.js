import { Link } from "react-router-dom/cjs/react-router-dom";
import AuthorizedNavbar from "./Navbars/NavbarAuthorized";

const NotFound = () => {
	return ( 
		<div>
			<AuthorizedNavbar />
			<div className="not-found container links">
				<h2>Sorry</h2>
				<p>This page cannot be found</p>
				<Link to="/" style = {{
					textDecoration: 'none',
					padding: '7px',
					color: "white",
					backgroundColor: '#f1365d',
					borderRadius: '8px'
				}}>Back to the main page</Link>
			</div>
		</div>
	 );
}
 
export default NotFound;