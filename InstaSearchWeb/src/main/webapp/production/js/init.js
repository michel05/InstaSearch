

$(document).ready(function() {

	moment.locale("pt-br");
	caminho_padrao = "http://localhost:8080";
	inicieParametros();
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

function configuracoes() {
	$("#conteudo").load("configuracoes.html");
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
    return Array(Math.max(digits - String(number).length + 1, 0)).join(0) + number;
}

function inicieParametros() {

	
}