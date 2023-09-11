import RegistrationNavBar from "./RegistrationNavbar";
import AboutProject from "./AboutProject";

const StartPage = () => {
	return ( 
		<div>
			<RegistrationNavBar />
			<div className="content">
				<AboutProject />
			</div>
		</div>
	 );
}
 
export default StartPage;