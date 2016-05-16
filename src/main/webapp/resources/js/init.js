$(function() {
    $(".loading").hide();
    $("#sucesso").hide();
    $("#erro").hide();
});

function ativarLoading() {
	$(".loading").show();
}

function buscarInfo(id) {
    $(".loading").show();
    
    var date = $("#mesAno").val();
    var numPosts = $("#numPosts").val();
    $("#idUsuario").val(id);
    
//    var filtroPost = $("#formFiltro").serialize();
    
    var filtroPost = { 
        idUsuario : id,
        dataInicio : date,
        numPosts : numPosts
    }
    
    $.ajax({
        type: 'GET',
        url: 'http://52.10.61.126:8080/instaSearch/info',
        data : filtroPost,
        success: function (data) {
            $(".loading").hide();
            $("#sucesso").show(); 
            window.open(data);
        },
        error : function() {
        	$(".loading").hide();
        	$("#erro").show();
        }
    });
}