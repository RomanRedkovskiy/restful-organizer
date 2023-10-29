import { useHistory } from 'react-router-dom/cjs/react-router-dom';
import { useId } from '../IdProvider';
import { useEffect } from 'react';



export default function CompilationDelete() {
	const currentId = useId();
	const history = useHistory();
	useEffect(() => {
		fetch('http://localhost:8080/tasks/' + currentId.taskId, {
			method: 'DELETE',
			headers: {
				"Authorization": localStorage.getItem('Token'),
				"Access-Control-Allow-Headers" : "Content-Type, Authorization",
				"Access-Control-Expose-Headers": "Authorization",
				"Access-Control-Allow-Origin": "*",
				'Content-Type': 'application/json',
				"Access-Control-Allow-Methods": "OPTIONS, DELETE"
			}
		}).then(() => {
			history.push('/compilation');
		})
	}, []);
}