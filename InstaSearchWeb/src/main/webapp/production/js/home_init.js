$(document).ready(function() {

	console.log("passou home_init.js");

	$("#monit .loading-padrao").show();
	
	inicie_home();
	intervalPostagens = setInterval(populePostagensAtuais, 30000);

});

function inicie_home() {
	
	console.log(caminho_padrao + '/Servico/monitoramento/verifiqueMonitoramento');
	$.get({
		url: caminho_padrao + '/Servico/monitoramento/verifiqueMonitoramento',
		success: function(data) {
			if(data != null && data != '') {
				continuar_monitoramento(data);
				prepare_area_postagens(data);

			} else {
				$("#monit .loading-padrao").hide();
				$(".area-botao").show();
			}
		}
	});		
}

function prepare_area_postagens(data) {
	$("#area_blocos_postagens").html("");
	$("#img_loading_postagens").show();
	for (var i = 0; i < data.postagens.length; i++) {
		if (data.postagens[i].status == 'ATIVO') {
			crieSeletorAccordion(data.postagens[i]);
		}
	};
	$("#img_loading_postagens").hide();
	$("#monit .loading-padrao").hide();
	
}

function continuar_monitoramento(monitoramentoJson) {
	start = monitoramentoJson.dataDeInicio;
	maxTime = 10800000; // 3 hrs
	//maxTime = 600000; // 10 min
	//timeoutVal = Math.floor((maxTime/100)/3); // dividido por tres para atualizar mais rapido
	timeoutVal = 5000; //5 segundos
	console.log("intervalo: " + timeoutVal);
	//animateUpdate();
	$(".monitoramento-carregando").show();
	$("#title-monitoramento").html("Monitoramento em execução - Horario de início " + moment(start).format("DD-MM-YYYY HH:mm:ss"));

}

function iniciar_monitoramento() {
	
	bootbox.prompt("Digite o nome do novo monitoramento?", function(result) {
		if (result === null) {  
		} else {
			$("#monit .loading-padrao").show();
			var url_inicia_monitoramento = caminho_padrao + "/Servico/monitoramento/iniciar/"+ result;
			$.get({
				url: url_inicia_monitoramento,
				success: function(data) {
					$(".area-botao").hide();
				},
				async: false,
			});	

			home();
		}
	}); 
}	

function updateProgress(percentage) {
	$('.progress-bar').css("width", percentage + "%");
	$('.progress-bar').html(percentage + "%");
}

function animateUpdate() {
	var now = new Date();
	var timeDiff = now.getTime() - start;
	var perc = Math.round((timeDiff/maxTime)*100);
	if (perc <= 100) {
		updateProgress(perc);

		populePostagensAtuais(); //Carrega as postagens atuais a cada 1% da barra
		setTimeout(animateUpdate, timeoutVal);
	} else {
		$(".monitoramento-carregando").hide();
		$(".area-botao").show();
		home();
	}
}


function populePostagensAtuais() {

	console.log("populePostagensAtuais hora: " + new Date());
	$.get({
		url: caminho_padrao + '/Servico/monitoramento/verifiqueMonitoramento',
		success: function(data) {
			if(data != null) {
				prepare_area_postagens(data);
			} else {
				clearInterval(intervalPostagens);
			}
		}
	});	
}

function crieSeletorAccordion (post) {

	var $bloco = $("#layout_postagens_atuais").clone();

	$bloco.show();
	$bloco.find("img").prop("src", post.urlImagem);
	$bloco.find(".descricao").html("<b>Descrição: </b>" + post.descricao.substring(0,30) + "...");
	$bloco.find(".curtidas").html("<b>Nº de curtidas: </b>" + post.numTotalCurtidas);
	$bloco.find(".comentarios").html("<b>Nº de comentários: </b>" + post.numTotalComentarios);
	$bloco.find(".link-post").html("<a href='" + post.link + "'>" + post.link + "</a>");

	$("#area_blocos_postagens").append($bloco);
}

function cancelarMonitoramento () {
	bootbox.confirm("Deseja cancelar o monitoramento atual?", function(result) {
	  if(result) {
	  	$.get({
		url: caminho_padrao + '/Servico/monitoramento/cancelar',
		success: function(data, status) {
			console.log(status);
			console.log(data);
			home();
			}
		});	
	  }
	}); 
	
}

