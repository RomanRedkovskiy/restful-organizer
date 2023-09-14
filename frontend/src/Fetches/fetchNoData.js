export default function fetchNoData (url, method) {
	fetch(url, {
		method: method,
		headers: {
			"Access-Control-Allow-Headers" : "Content-Type",
			"Access-Control-Allow-Origin": "*",
			'Content-Type': 'application/json',
			"Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH"
		}
	});
}
