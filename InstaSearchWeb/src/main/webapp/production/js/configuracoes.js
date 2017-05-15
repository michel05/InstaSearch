$(document).ready(function() {

	console.log("passou configuracoes.js");

	init();

});


function init() {
	
	$.get({
		url: caminho_padrao + '/Servico/monitoramento/tempoMonitoramento',
		success: function(data) {
			if(data != null) {
				console.log(data);
				$("#tempo_monitoramento").val(data);
				$(".loading-padrao").hide();
			}
		},
		error: function(data) {
			bootbox.alert("Falha na conexão com o serviço");
		}
	});
	
	
}


function submit_configuracoes() {

	var tempo_monitoramento = calculaHoraParaTimestamp($("#tempo_monitoramento").val());

	$.get({
		url: caminho_padrao + '/Servico/monitoramento/tempoMonitoramento/'+tempo_monitoramento, 
		success: function(data) {
			if(data != null) {
				console.log(data);
				$("#tempo_monitoramento").val(data);
				bootbox.alert("Tempo de monitoramento atualizado com sucesso!");
			}
		},
		error:  function(data) {
			bootbox.alert("Houve um erro, tente novamente!");
		}

	});	

}

function calculaHoraParaTimestamp(horario)  {
	var dt = new moment(horario, 'HH:mm');
	var valor = ((dt.hour() * 3600000) + (dt.minutes() * 60000));
	
	return valor;
}