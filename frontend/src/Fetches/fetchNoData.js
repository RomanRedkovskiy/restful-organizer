export default function fetchNoData (url, method) {
	fetch(url, {
		method: method,
		headers: {
			"Authorization": localStorage.getItem('Token'),
			"Access-Control-Allow-Headers" : "Content-Type, Authorization",
			"Access-Control-Expose-Headers": "Authorization",
			"Access-Control-Allow-Origin": "*",
			'Content-Type': 'application/json',
			"Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH"
		}
	});
}
