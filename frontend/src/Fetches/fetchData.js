export default async function fetchData(url, method, body) {
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
	  }).then(response => response.json())
	  .then(data => data)
	  .catch(error => console.error(error));
}