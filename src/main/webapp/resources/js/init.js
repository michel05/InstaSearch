function limpaTela() {
//    $(".loading").hide();
    $("#sucesso").hide();
    $("#erro").hide();
    $("#semPosts").hide();
}

function ativarLoading() {
	$(".loading").show();
}

function buscarInfo(id) {
	
	limpaTela();
    $(".loading").show();
    
    var dataInicio = $("#dataInicio").val();
    var dataFim = $("#dataFim").val();
    var numPosts = $("#numPosts").val();
    $("#idUsuario").val(id);
    
//    var filtroPost = $("#formFiltro").serialize();
    
    var filtroPost = { 
        "idUsuario" : id,
        "dataInicio" : dataInicio,
        "dataFim" : dataFim,
        "numPosts" : numPosts
    }
    
    $.ajax({
        type: 'POST',
        contentType : 'application/json; charset=utf-8',
        url: 'http://52.10.61.126:8080/instaSearch/info',
        data : JSON.stringify(filtroPost),
        success: function (data) {
            $(".loading").hide();
            if (data != "") {
            	$("#sucesso").show();
            	window.open(data);
            } else {
            	$("#semPosts").show();
            }
        },
        error : function() {
        	$(".loading").hide();
        	$("#erro").show();
        }
    });
}