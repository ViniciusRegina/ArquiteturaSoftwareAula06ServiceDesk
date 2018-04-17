<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Editar Fila</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
</head>
<body>
	<!-- Barra superior com os menus de navegação -->
	<c:import url="../menu.jsp" />
	<!-- Container Principal -->
	<div id="main" class="container">
		<h3 class="page-header">Alterar Local #${fila.id}</h3>
		<!-- Formulario para alteracao de locais -->
		<form action="atualizar_local" method="post"
			enctype="multipart/form-data">
			<!-- area de campos do form -->
			<input type="hidden" name=id value="${fila.id}"> <input
				type="hidden" name=imagem value="${fila.caminho_figura}">
			<div class="row">
				<div class="form-group col-md-8">
					<label for="nome">Nome</label> <input type="text"
						class="form-control" name="nome" id="nome" required
						maxlength="128" value="${fila.nome}" placeholder="nome do local">
					<form:errors path="fila.nome" cssStyle="color:red" />
				</div>
				<div class="form-group col-md-4">
					<label for="tipo">Tipo</label> <select name="tipo.id"
						class="form-control">
						<c:forEach var="tipo" items="${filas}">
							<c:if test="${fila.id != fila.tipo.id}">
								<option value="${fila.id}">${fila.nome}</option>
							</c:if>
							<c:if test="${fila.id eq fila.tipo.id}">
								<option value="${fila.id}" selected>${fila.nome}</option>
							</c:if>
						</c:forEach>
					</select>
				</div>
			</div>
				
				
			<div class="row">
				<div class="form-group col-md-8">
					<label for="arquivo">Selecione uma imagem</label> 
					<input type="file" name="file" id="arquivo" class="file" />
				</div>
			</div>
			<hr />
			<div id="actions" class="row">
				<div class="col-md-12">
					<button type="submit" class="btn btn-primary">Salvar</button>
					<a href="listar_locais" class="btn btn-default">Cancelar</a>
				</div>
			</div>
		</form>
	</div>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>