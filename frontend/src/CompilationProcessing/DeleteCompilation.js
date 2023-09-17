import { useHistory } from 'react-router-dom/cjs/react-router-dom';
import { useId } from '../IdProvider';
import { useEffect } from 'react';



export default function CompilationDelete() {
	const currentId = useId();
	const history = useHistory();
	useEffect(() => {
		fetch('http://localhost:8080/compilations/' + currentId.compilationId, {
			method: 'DELETE',
			headers: {
				"Access-Control-Allow-Headers" : "Content-Type",
				"Access-Control-Allow-Origin": "*",
			    'Content-Type': 'application/json',
				"Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH"
			}
		}).then(() => {
			history.push('/compilations');
		})
	}, []);
}
 