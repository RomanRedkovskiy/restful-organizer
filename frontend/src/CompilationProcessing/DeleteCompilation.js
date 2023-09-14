import { useHistory } from 'react-router-dom/cjs/react-router-dom';
import { useId } from '../IdProvider';
import fetchData from '../Fetches/fetchData';
import { useEffect } from 'react';
import fetchNoData from '../Fetches/fetchNoData';



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
 