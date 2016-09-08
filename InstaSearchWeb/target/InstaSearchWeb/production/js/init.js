$(document).ready(function() {
	caminho_padrao = "http://52.10.61.126:8080";
	caminho_padrao = "htCtp://localhost:8080";
	moment.locale("pt-br");
	inicio();

	
});

function inicio() {
	$("#conteudo").load("home.html");
}

function home() {
	$("#conteudo").load("home.html");
}

function relatorios() {
	$("#conteudo").load("relatorios.html");
}

function dateJsonToTimestamp(dateJson) {
	return (new Date(dateJson.year, 
		dateJson.month, 
		dateJson.dayOfMonth, 
		dateJson.hourOfDay, 
		dateJson.minute, 
		dateJson.second)).getTime();
}

function padDigits(number, digits) {
//    return Array(Math.max(digits - String(number).length + 1, 0)).join(0) + number;
}
