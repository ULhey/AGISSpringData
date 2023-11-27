<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> 
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"	integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"	integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
<title>Matriculas</title>
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
			<b>Matriculas Aluno</b>
		</p>
	</div>
	<div align="right" class="container">
		<form class="row g-3" action="matricula" method="post">
			<div align="center">
				<table>
					<tr>
						<td><input class="form-control" type="text" id="RA" name="RA"
							placeholder="RA" value='<c:out value="${matricula.RA}"></c:out>'></td>
						<td><input class="btn btn-dark" type="submit" id="botao"
							name="botao" value="Buscar"></td>
					</tr>
				</table>
				<br> <br>
			</div>
		</form>
		<c:if test="${not empty matriculas}">
			<div align="center" class="container col-md-8">
				<br>
				<p class="fs-2">
					<b>Disciplinas Matriculadas</b>
				</p>
			</div>
			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th><b>Codigo Turma</b></th>
							<th><b>Disciplina</b></th>
							<th><b>Semestre</b></th>
							<th><b>Horário</b></th>
							<th></th>
					</thead>
					<tbody>
						<c:forEach var="m" items="${matriculas}">
							<form action="matricula" method="post">
								<input type="hidden" name="RA" value="${m.aluno.RA}">
								<input type="hidden" name="idMatricula" value="${m.idMatricula}">
								<tr>
									<td><c:out value="${m.turma.idTurma}"></c:out></td>
									<td><c:out value="${m.turma.disciplina.nomeDisciplina}"></c:out></td>
									<td><c:out value="${m.turma.disciplina.semestreDisciplina}"></c:out></td>
									<td><c:out value="${m.turma.horarioAula}"></c:out></td>

									<td><div align="center">
											<c:if test="${m.situacao != 'Dispensado' }">
												<button type="button" class="btn btn-outline-primary"
													data-bs-toggle="modal" data-bs-target="#modal1">+</button>
											</c:if>
											<div class="modal fade" id="modal1" tabindex="-1"
												aria-labelledby="exampleModalLabel" aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
														<div class="modal-header">
															<h1 class="modal-title fs-5" id="exampleModalLabel">Dispensar</h1>
															<button type="button" class="btn-close"
																data-bs-dismiss="modal" aria-label="Close"></button>
														</div>
														<div class="modal-body" align="center">
															Deseja dispensar a disciplina? <br>
														</div>
														<div class="modal-footer">
															<input type="submit" class="btn btn-danger" name="botao"
																value="Dispensar">
														</div>
													</div>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</form>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
	<div class="container" align="center"></div>
	<div align="center" class="container col-md-10">
		<c:if test="${not empty turmas}">
			<div align="center" class="container col-md-8">
				<br>
				<p class="fs-2">
					<b>Disciplinas Disponiveis</b>
				</p>
			</div>
			<div align="center" class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th><b>Codigo Disciplina</b></th>
							<th><b>Disciplina</b></th>
							<th><b>Semestre</b></th>
							<th><b>Horário</b></th>
							<th></th>
					</thead>
					<tbody>
						<c:forEach var="m" items="${turmas}">
							<form action="matricula" method="post">
								<input type="hidden" name="idTurma" value="${m.idTurma}">
								<tr>
									<td><c:out value="${m.idTurma}"></c:out></td>
									<td><c:out  value="${m.disciplina.nomeDisciplina}"></c:out></td>
									<td><c:out value="${m.disciplina.semestreDisciplina}"></c:out></td>
									<td><c:out value="${m.horarioAula}"></c:out></td>
									
									<td><div align="center">
											<button type="button" class="btn btn-outline-primary"
												data-bs-toggle="modal" data-bs-target="#modal2">+</button>
											<div class="modal fade" id="modal2" tabindex="-1"
												aria-labelledby="exampleModalLabel" aria-hidden="true">
												<div class="modal-dialog">
													<div class="modal-content">
														<div class="modal-header">
															<h1 class="modal-title fs-5" id="exampleModalLabel">Matricular</h1>
															<button type="button" class="btn-close"
																data-bs-dismiss="modal" aria-label="Close"></button>
														</div>
														<div class="modal-body" align="center">
															Matricular a Disciplina? <br>
														</div>
														<div class="modal-footer">
															<input type="submit" class="btn btn-success" name="botao" value="Matricular">
														</div>
													</div>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</form>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
</body>
</html>