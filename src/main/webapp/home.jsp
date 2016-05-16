<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<jsp:include page="common/header.jsp" />

<body>

	<div class="clear30"></div>
	<div class="col-md-4 centralizado login">
		<form action="buscarUsuario">
			<fieldset class="form-group center">
				<label for="usuario"><h2>Buscar usuário</h2></label>
			</fieldset>
			<input type="text" class="form-control" id="usuario" name="usuario" placeholder="@usuario">
  
			<div class="login-bwn">
				<input type="submit" value="Buscar" />
			</div>
		</form>
		<div class="clear10"></div>
	</div>

	<div class="clear30"></div>


	<div id="sucesso" class="col-md-12">
		<div class="col-md-6 centralizado">
			<div class="alert alert-success">Arquivo gerado com sucesso</div>
		</div>
		<div class="clear30"></div>
	</div>

	<div id="erro" class="col-md-12">
		<div class="col-md-6 centralizado">
			<div class="alert alert-danger">Um erro ocorreu. Tente novamente.</div>
		</div>
	</div>

	<c:if test="${ usuariosLista ne null}">
		<div class="col-md-10 centralizado login center">
			<form class="form-inline" id="formFiltro">
				<div class="form-group">
					<label for="mesAno">Mês/Ano</label> 
					<input type="month" class="form-control" id="mesAno" name="dataIncio" placeholder="">
				</div>
				<div class="form-group">
					<label for="posts">&nbsp;&nbsp;Nº Posts</label> 
					<input type="number" min="1" max="100" class="form-control" name="numPosts" id="numPosts" value="20">
				</div>
				<input type="hidden" name="idUsuario" id="idUsuario">
			
			<div class="clear30"></div>
			<div class="row">
				<div class="col-md-12">
					<c:forEach var="contato" items="${usuariosLista}" varStatus="id">

						<div class="col-md-3" style="padding-bottom: 20px">
							@${contato.nome} <br> <img style="border-radius: 50%;"
								alt="" src="${contato.imagem}"><br> 
								Id: ${contato.id}<br>
							<button onclick="buscarInfo(${contato.id});" type="button"
								class="btn btn-info">Baixar estatisticas</button>
						</div>

					</c:forEach>
				</div>
			</div>
			</form>
		</div>
	</c:if>
	<div class="loading">
		<img alt="" src="resources\images\loading.gif">
	</div>

</body>
</html>