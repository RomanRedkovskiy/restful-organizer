import restfulOrganiser from '../images/restful_organiser.jpg';
import editTasksImage1 from '../images/edit_tasks_logic_1.jpg';
import editTasksImage2 from '../images/edit_tasks_logic_2.jpg';
import collaborationTasks from '../images/collaborating_tasks_into_compilations.jpg';
import shareTL1 from '../images/share_tasks_logic_1.jpg';
import shareTL2 from '../images/share_tasks_logic_2.jpg';
import userSTATlogic from '../images/user_statistics_logic.jpg'
import { List } from 'reactstrap';


const AboutProject = () => {
	return ( 
		<>
			<div className="about_project">
				<h2 className='about_project_header'>About project</h2>
				
				<div className="about_project_box">
					<h4 className='about_project_box_header'>RESTful organiser:</h4>
					<p className="about_project_text">RESTful organizer is a task management system that allows users to: </p>
						<ul className='about_project_list'> 
							<li>organize their tasks into compilations,</li> 
							<li>collaborate with other users by sharing your work progress with them.</li>
						</ul>  
					<p className="about_project_text">The program follows a client-server architecture, with the frontend implemented using React and the backend implemented using Java Spring.</p>
					
					<div className='image_and_discription'>
						<img className='restfulOrganiser_img' src={restfulOrganiser}/>
						<p className='img_descriprion_text'>Compilations example</p>
					</div>
					<div className='image_and_discription'>
						<img className='collaborationTask_img' src={collaborationTasks}/>
						<p className='img_descriprion_text'>Compilation menu</p>
					</div>
				</div>
				
				<div className="about_project_box">
					<h4 className='about_project_box_header'>Tasks:</h4>
					<p className="about_project_text">The frontend provides an intuitive user interface, allowing users to: </p>
						<ol className='about_project_list'> 
							<li><b>create</b> tasks;</li> 
							<li><b>edit</b> tasks;</li>
							<li><b>delete</b> tasks.</li>
						</ol> 
						<div className='img'>
							<img className='editTaskImg_1' src={editTasksImage1}/>
						</div>
						<div className='image_and_discription'>
							<img className='editTaskImg_2' src={editTasksImage2}/>
							<p className='img_descriprion_text'>Task edit logic</p>
						</div>
					<p className="about_project_text">Users can customize task’s properties, such as <b>title</b>, <b>description</b> and <b>status</b>. </p>
				</div>

				<div className="about_project_box">
					<h4 className='about_project_box_header'>Compilations:</h4>
					<p className="about_project_text">Also, it’s possible to organize tasks into compilations, change compilation parameters and <b>share</b> them with other users. </p>
						
					<div className='img'>
						<img className='shareTaskImg_1' src={shareTL1}/>	
					</div>
						
					<div className='image_and_discription'>
						<img className='shareTaskImg_2' src={shareTL2}/>
						<p className='img_descriprion_text'>Share compilations logic</p>
					</div>
				</div>

				<div className="about_project_box">
					<h4 className='about_project_box_header'>Statistics:</h4>
					<p className="about_project_text">Users can check their <b>overall task completeness stats</b>, change their account settings and much more.</p>
						<div className='image_and_discription'>
							<img className='userSTATlogic_img' src={userSTATlogic}/>
							<p className='img_descriprion_text'>User statistics logic</p>
						</div>
				</div>
						

				<div className="about_project_box_last">
					<h4 className='about_project_box_header'>Backend logic:</h4>
					<p className="about_project_text">The frontend communicates with the backend through RESTful API endpoints, sending requests to perform various operations on tasks, compilations and users.</p>
					<p className="about_project_text">On the backend, the Java Spring framework is used to build the RESTful API and handle the business logic. The backend interacts with the MySQL database to store and retrieve user’s data. The database schema includes tables for tasks, compilations, and user information, allowing for efficient storage and retrieval of data. The backend is built on a microservice architecture with a separate database for each microservice.</p>
					<p className="about_project_text">The backend logic includes authentication and authorization based on JSON Web Tokens (JWT) to ensure that users can only access and modify their own data. Also, separation of roles is supported via JWT. For example, the admin role has expanded functionality compared to the user role.</p>
					
				</div>

			</div>

			<footer className='footer'>
				<div class="footer_text">
					<p className='email_text'>Please do not hesitate to contact me with any additional questions. My email:
						<a href="https://mail.google.com/mail/u/0/#inbox" class="email" target="_blank"><b> romanredkovskiy10@gmail.com</b></a>
					</p>
				</div>
			</footer>
		
		</>
	);
}

 
export default AboutProject;