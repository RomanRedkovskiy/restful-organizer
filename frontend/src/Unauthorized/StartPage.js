import UnauthorizedNavBar from "../Navbars/NavbarUnauthorized";
import AboutProject from "./AboutProject";
import { useId, useIdUpdate } from '../IdProvider';
import { useEffect } from "react";


const StartPage = () => {

	const currentId = useId();
	const setId = useIdUpdate();	

	console.log('Not authorized:');
	console.log(currentId);

	return ( 
		<div>
			<UnauthorizedNavBar />
			<div className="content">
				<AboutProject />
			</div>
		</div>
	 );
}
 
export default StartPage;