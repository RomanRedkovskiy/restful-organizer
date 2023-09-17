export default function fetchData(url, method, body) {
	fetch(url, {
		method: method,
		headers: {
			"Access-Control-Allow-Headers" : "Content-Type",
			"Access-Control-Allow-Origin": "*",
			'Content-Type': 'application/json',
			"Access-Control-Allow-Methods": "OPTIONS,POST,GET,PUT,DELETE",
			"Cache-Control": "no-cache"
		},
		body: JSON.stringify(body)
	}).then(res => {
		if(!res.ok){
			throw Error('Could not fetch data for a server');
		}
		return res.json();
	});
}