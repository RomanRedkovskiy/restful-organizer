import { useId, useIdUpdate } from '../IdProvider';
import AuthorizedNavbar from '../Navbars/NavbarAuthorized';
import useFetch from '../Fetches/useFetch';
import CompilationList from './ListCompilation';
import { Link } from 'react-router-dom/cjs/react-router-dom.min';
const CompilationProcessor = () => {
	const currentId = useId();
	const setId = useIdUpdate();
	const {data: selfCompilations, isSelfLoading, selfError} =
		useFetch('http://localhost:8080/users/' + currentId.userId + '/self_compilations');
	const {data: sharedCompilations, isSharedLoading, sharedError} =
		useFetch('http://localhost:8080/users/' + currentId.userId + '/shared_compilations');
	const {data: readonlyCompilations, isReadonlyLoading, readonlyError} =
		useFetch('http://localhost:8080/users/' + currentId.userId + '/readonly_compilations');
	return ( 
		<>
			<AuthorizedNavbar />
			<div className="home container default-layout">
				{selfCompilations && sharedCompilations && readonlyCompilations &&
				 selfCompilations.length + sharedCompilations.length + readonlyCompilations.length === 0 && 
				<div className='empty-message'>
					<h2>You don't have any compilations yet. <Link to = "/new-compilation">Add one</Link></h2>
				</div>	
				}
				{selfError && <h2>{selfError}</h2>}
				{isSelfLoading && <h2>Loading...</h2>}
				{selfCompilations &&  selfCompilations.length > 0 && 
				<CompilationList compilations = {selfCompilations} title = "Your Compilations:" isSelf={true} isShared={false}/>}

				{sharedError && <h2>{sharedError}</h2>}
				{isSharedLoading && <h2>Loading...</h2>}
				{sharedCompilations &&  sharedCompilations.length > 0 && 
				<CompilationList compilations = {sharedCompilations} title = "Shared Compilations:" isSelf = {true} isShared={true}/>}

				{readonlyError && <h2>{readonlyError}</h2>}
				{isReadonlyLoading && <h2>Loading...</h2>}
				{readonlyCompilations && readonlyCompilations.length > 0 && 
				<CompilationList compilations = {readonlyCompilations} title = "Readonly Compilations:" isSelf={false} isShared={false}/>}
			</div>
		</> 
	);
}
 
export default CompilationProcessor;