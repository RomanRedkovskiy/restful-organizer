import { Link } from "react-router-dom/cjs/react-router-dom";

const NotFound = () => {
	return ( 
		<div className="not-found">
			<h2>Whoops!</h2>
			<p>This page cannot be found</p>
			<Link to="/">Back to the main page {"-->"}</Link>
		</div>
	 );
}
 
export default NotFound;