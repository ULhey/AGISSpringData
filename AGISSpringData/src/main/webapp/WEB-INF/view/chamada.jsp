<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"	integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
<title>Chamada Lista</title>
</head>
<body class="bg-light">
		<nav class="navbar navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"> <img
				src="https://cdn-icons-png.flaticon.com/512/1/1968.png" alt="Logo"
				width="30" height="24" class="d-inline-block align-text-top">
				GIS
			</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavDropdown">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="index">Home</a></li>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" role="button"	data-bs-toggle="dropdown" aria-expanded="false">Secretaria</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="curso">Cursos</a></li>
							<li><a class="dropdown-item" href="disciplina">Disciplina</a></li>
							<li><a class="dropdown-item" href="turma">Turmas</a></li>
							<li><a class="dropdown-item" href="aluno">Ficha Aluno</a></li>
							<li><a class="dropdown-item" href="avaliacao">Notas Parciais</a></li>
							<li><a class="dropdown-item" href="historico">Historico Aluno</a></li>
							<li><a class="dropdown-item" href="historiconota">Historico	Notas Turmas</a></li>
							<li><a class="dropdown-item" href="historicofaltas">Historico Faltas Turmas</a></li>
						</ul></li>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" role="button"	data-bs-toggle="dropdown" aria-expanded="false">Professor</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="chamada">Chamadas</a></li>
						</ul>
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle" role="button"	data-bs-toggle="dropdown" aria-expanded="false">Aluno</a>
						<ul class="dropdown-menu">
							<li><a class="dropdown-item" href="matricula">Matriculas</a></li>
						</ul>
					</ul>
			</div>
		</div>
	</nav>
	<div align="center" class="container col-md-6">
		<br>
		<p class="fs-2">
			<b>Lista de Chamada</b>
		</p>
	</div>
	<div align="right" class="container">
			<div align="center">
				<form action="chamada" method="post">
					<table>
						<tr>
							<td><select name="idTurma" class="form-select col-md-7">
								<option>Selecione a Turma</option>
									<c:forEach var="c" items="${turmas}">
										<option value="${c.idTurma }">
											<c:out value="${c.disciplina.nomeDisciplina}"/>
										</option>
									</c:forEach>
							</select></td>
	
							<td><input class="btn btn-dark" type="submit" id="botao" name="botao" value="Realizar chamada">
								<button type="button" class="btn btn-dark" data-bs-toggle="modal"
									data-bs-target="#exampleModal">Alterar Chamada</button>
									
								<div class="modal fade" id="exampleModal" tabindex="-1"
									aria-labelledby="exampleModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h1 class="modal-title fs-5" id="exampleModalLabel">Alteração
													de Faltas</h1>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body" align="center">
												<div class="col-md-5" align="center">
													<label class="form-label"><b>Data que deseja
															altera: </b></label> <input class="form-control" type="date" id="data"
														name="data" value="data">
												</div>
											</div>
											<div class="modal-footer">
												<input class="btn btn-dark" type="submit" id="botao"
													name="botao" value="Consultar">
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</form>
				<br> <br>
				<div class="container" align="center">
					<table class="table table-striped ">
						<c:if test="${not empty chamadas}">
							<div align="center" class="container col-md-8">
								<br>
								<p class="fs-2">
									<b>Chamada do Dia</b>
								</p>
							</div>
							<div class="table-responsive">
								<table class="table table-striped">
									<thead>
										<tr>
											<th><b>RA</b></th>
											<th><b>Nome</b></th>
											<th><b>Disciplina</b></th>
											<th><b>Faltas</b></th>
											<th><b></b></th>
									</thead>
									<tbody>
										<c:forEach var="c" items="${chamadas}">
											<form action="chamada" method="post">
												<input type="hidden" name="ra" value="<c:out value="${c.RAAluno}"></c:out>">
												<input type="hidden" name="idTurma" value="<c:out value="${c.idTurma}"></c:out>">
												<input type="hidden" name="voltaLista" value="chamada1">
												<tr>
													<td><c:out value="${c.RAAluno}"></c:out></td>
													<td><c:out value="${c.nomeAluno}"></c:out></td>
													<td><c:out value="${c.nomeDisciplina}"></c:out></td>
													<td class="col-md-1"><input class="form-control" type="text" name="faltas" value="0"/></td>
													<td align="center">
														<input type="submit" class="btn btn-dark" name="botao" value="Salvar">
													</td>
												</tr>
											</form>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
					</table>
				</div>
				
				<div class="container" align="center">
					<table class="table table-striped ">
						<c:if test="${not empty chamadas2}">
							<div align="center" class="container col-md-8">
								<br>
								<p class="fs-2">
									<b>Chamada do dia: <c:out value="${dataChamada}"></c:out> </b>
								</p>
							</div>
							<div class="table-responsive">
								<table class="table table-striped">
									<thead>
										<tr>
											<th><b>RA</b></th>
											<th><b>Nome</b></th>
											<th><b>Disciplina</b></th>
											<th><b>Faltas</b></th>
											<th><b>Data</b></th>
											<th><b></b></th>
									</thead>
									<tbody>
										<c:forEach var="c" items="${chamadas2}">
											<form action="chamada" method="post">
												<input type="hidden" name="idChamada" value="<c:out value="${c.idChamada}"></c:out>">
												<input type="hidden" name="idTurma" value="<c:out value="${c.turma.idTurma}"></c:out>">
												<input type="hidden" name="data" value="<c:out value="${c.dataChamada}"></c:out>">
												<input type="hidden" name="voltaLista" value="chamada2">
												<tr>
													<td><c:out value="${c.aluno.RA}"></c:out></td>
													<td><c:out value="${c.aluno.nome}"></c:out></td>
													<td><c:out value="${c.turma.disciplina.nomeDisciplina}"></c:out></td>
													<td class="col-md-1"><input class="form-control" type="text" name="faltas" value="<c:out value="${c.faltas}"></c:out>"/></td>
													<td><c:out value="${c.dataChamada}"></c:out></td>
													<td align="center">
														<input type="submit" class="btn btn-dark" name="botao" value="Alterar">
													</td>
												</tr>
											</form>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
					</table>
				</div>
			</div>
	</div>
</body>
</html>