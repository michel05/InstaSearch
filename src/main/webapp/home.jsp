<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<jsp:include page="common/header.jsp" />
<script>

function ativarLoading() {
	$(".loading").show();
}


</script>

<body>

        <div class="login">
            <h2>Buscar usuario</h2>
            <form action="buscarUsuario">
                <input type="text" class="user active" name="usuario" placeholder="@usuario"/>
            
            <div class="login-bwn">
                <input type="submit" value="Buscar" />
            </div>
            </form>
            <div class="clear"></div>
            
            <div class="row">
            </div>
            
		</div>
		
		<div class="clear30"></div>
		
		<c:if test="${sucesso ne null }">
		  <div class="col-md-12">
		      <div class="col-md-6 centralizado">
		            <div class="alert alert-success">
					  Arquivo gerado com sucesso em: F://arquivos_coleta/${sucesso }
				    </div>
		      </div>
		  </div>
		</c:if>
		
		<c:if test="${error ne null }">
          <div class="col-md-12">
              <div class="col-md-6 centralizado">
                    <div class="alert alert-danger">
                      ${error }
                    </div>
              </div>
          </div>
        </c:if>
		
		
		<c:if test="${ lista ne null }">
        <div class="login" style="width: 80%">
            <div class="row">
            <div class="col-md-12">
                <c:forEach var="contato" items="${lista}" varStatus="id">
                    
                    <div class="col-md-3" style="padding-bottom: 20px">
                       @${contato.nome} <br>
                        <img style="border-radius: 50%;" alt="" src="${contato.imagem}"><br>
					Id: ${contato.id}<br> 
					<a href="buscarInformacoes?id=${contato.id}"><button onclick="ativarLoading();" type="button" class="btn btn-info">Baixar estatisticas</button></a>
                    </div>
                 
                </c:forEach>
            </div>
            </div>
		 </div>
		 </c:if>
		 <div class="loading" style="display: none">
		      <c:if test="${sucesso eq null }">
		          <img alt="" src="resources\images\loading.gif">
		      </c:if>
		 </div>
		 
    </body>
</html>