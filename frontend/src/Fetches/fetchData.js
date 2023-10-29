export default function fetchData(url, method, body) {
	fetch(url, {
		method: method,
		headers: {
			"Authorization": localStorage.getItem('Token'),
			"Access-Control-Allow-Headers" : "Content-Type, Authorization",
			"Access-Control-Expose-Headers": "Authorization",
			"Access-Control-Allow-Origin": "*",
			'Content-Type': 'application/json',
			"Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH"
		},
		body: JSON.stringify(body)
	}).then(res => {
		if(!res.ok){
			throw Error('Could not fetch data for a server');
		}
		return res.json();
	});
}