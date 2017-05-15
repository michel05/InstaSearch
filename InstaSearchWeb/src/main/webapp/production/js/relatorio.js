$(document).ready(function() {

	console.log("passou relatorio.js");

	$layout_relatorio = $("#relatorio_layout").clone();
	$painelMonitoramento = null;
	inicie_relatorio();

});


function inicie_relatorio() {

	$.get({
		  url: caminho_padrao + '/Servico/monitoramento',
		  success: function(data) {
		  	for (var i = 0; i < data.length; i++) {
		  		montePainelMonitoramento(data[i]);
		  	};
		      $(".loading-padrao").hide();
		  }
		});			
}

function montePainelMonitoramento(monitoramentoJson) {



	$painelMonitoramento = $layout_relatorio.find("#painel_relatorio_padrao").clone();


	$painelMonitoramento.prop("id", monitoramentoJson.id);

	var dataJsonInicio = monitoramentoJson.dataDeInicio;
	var dataJsonFim = monitoramentoJson.dataDeTermino;
	var dataInicio = "<small> <b>Data inicio:</b> " + new Date(dataJsonInicio).toLocaleDateString() + " " + new Date(dataJsonInicio).toLocaleTimeString() + " </small>";
	var dataFim = "  -<small><b>Data fim:</b> " + new Date(dataJsonFim).toLocaleDateString() + " " + new Date(dataJsonFim).toLocaleTimeString() + " </small>";
	var duracao = (dataJsonFim - dataJsonInicio);
	var duracaoHora = padDigits((new Date(duracao).getUTCHours()), 2) + ":" + padDigits((new Date(duracao).getUTCMinutes()),2) + ":" + padDigits((new Date(duracao).getUTCSeconds()), 2);
	console.log("Diferencas: " + (dataJsonFim - dataJsonInicio));
	$painelMonitoramento.find(".titulo").append(monitoramentoJson.titulo + dataInicio + (dataJsonFim > 0 ? dataFim + " / <b>Duração:</b>: " + duracaoHora : ""));
	$painelMonitoramento.find(".area-botao").html(crieBotaoDeDownload(monitoramentoJson.id));


	montePostagensNoMonitoramento($painelMonitoramento, monitoramentoJson);

	//Adiciona todo painel na div principal
	$("#area_conteudo_relatorios").prepend($painelMonitoramento.html());
}

function montePostagensNoMonitoramento(painelMonitoramento, monitoramentoJson) {

	var $bar_tab = painelMonitoramento.find(".bar_tabs");
	var $tab_content = painelMonitoramento.find(".tab-content");

	var listaDePostagensJson = monitoramentoJson.postagens;

	console.log(listaDePostagensJson.length);

	for (var i = 0; i < listaDePostagensJson.length; i++) {
		
		var $aba_relatorio = $layout_relatorio.find("#aba_relatorio").clone();
		var $conteudo_aba_relatorio = $layout_relatorio.find("#conteudo_aba_relatorio").clone();

		$aba_relatorio.find("a").prop("href", "#" + monitoramentoJson.id + "_" + listaDePostagensJson[i].id);
		$aba_relatorio.find("a").html(listaDePostagensJson[i].descricao.substring(0,10) + "...");

		$conteudo_aba_relatorio.prop("id", monitoramentoJson.id + "_" + listaDePostagensJson[i].id);

		var conteudo_aba_html = "<b>Descricao:</b><br>" + listaDePostagensJson[i].descricao +
								"<br><br><b>Número de curtidas: </b>" + listaDePostagensJson[i].numTotalCurtidas +
								"<br><br><b>Número de comentários: </b>" + listaDePostagensJson[i].numTotalComentarios + 
								"<br><br><b>Grafico de curtidas/comentarios por minuto, em breve... =) </b>";
		$conteudo_aba_relatorio.find("p").html(conteudo_aba_html);

		$bar_tab.append($aba_relatorio);
		$tab_content.append($conteudo_aba_relatorio);

	};

}

function donwloadRelatorio(idMonitoramento) {
	
	$("#botao-download" + idMonitoramento).attr('disabled', true);
	window.open(caminho_padrao + '/Servico/monitoramento/exportarMonitoramento/'+ idMonitoramento, "_blank");
  	$("#botao-download" + idMonitoramento).attr('disabled', false);

}

function crieBotaoDeDownload(id) {
	var htmlBotao = " <button id='botao-download" + id + "' onclick='donwloadRelatorio(" + id + ")' class='btn btn-primary pull-right botao-download'" +
		 " style='margin-right: 5px;'><i class='fa fa-download'></i> Download xls</button> ";

	return htmlBotao;
}