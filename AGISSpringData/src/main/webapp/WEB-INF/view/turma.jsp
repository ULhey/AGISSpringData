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
<title>Turmas</title>
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
			<b>Turmas</b>
		</p>
	</div>
	<div align="right" class="container">
		<form class="row g-3" action="turma" method="post">
			<div align="right">
				<table>
					<tr>
						<td><input class="form-control" type="text" id="idTurma"
							name="idTurma" placeholder="idTurma"
							value='<c:out value="${turma.idTurma}"></c:out>'></td>
						<td><input class="btn btn-dark" type="submit" id="botao"
							name="botao" value="Buscar"></td>
					</tr>
				</table>
			</div>
			<p class="fs-5 fw-medium" align="left">
				<b>Cadastro de Turma:</b>
			</p>
			<div class="col-md-7" align="left">
				<label class="form-label">Professor</label><input
					class="form-control" type="text" id="professor" name="professor"
					placeholder="ex: Fulana Santos"
					value='<c:out value="${turma.professor}"></c:out>'>
			</div>
			<div class="col-md-3" align="left">
				<label class="form-label">Horario de Aula</label><input
					class="form-control" type="text" id="horarioAula"
					name="horarioAula" placeholder="ex: 13:00 as 14:50"
					value='<c:out value="${turma.horarioAula}"></c:out>'>
			</div>
			<div class="col-md-2" align="left">
				<label class="form-label">Periodo (Turno)</label><input
					class="form-control" type="text" id="periodoAula"
					name="periodoAula" placeholder="ex: V (Vespertino)"
					value='<c:out value="${turma.periodoAula}"></c:out>'>
			</div>
			<div class="col-md-5" align="left">
				<label class="form-label">Metodo Avaliativo</label> <select
					name="metodoAvaliativo" class="form-select">
					<option>Selecione o Tipo Avaliativo</option>
					<option value="nota1">Nota 1 (P1 0,3/P2 0,5/T 0,2)</option>
					<option value="nota2">Nota 2 (P1 0,35/P2 0,35/T 0,3)</option>
					<option value="nota3">Nota 3 (0,333...)</option>
					<option value="m">M (A 0,2/M 0,8)</option>
				</select>
			</div>
			<div class="col-md-7" align="left">
				<label class="form-label">Disciplina</label> <select
					name="idDisciplina" class="form-select">
					<c:choose>
						<c:when test="${not empty turma}">
							<option value="${turma.disciplina.idDisciplina}">
								<c:out value="${turma.disciplina.nomeDisciplina}" />
							</option>
						</c:when>
						<c:otherwise>
							<option>Selecione o disciplina</option>
						</c:otherwise>
					</c:choose>
					<c:forEach var="d" items="${disciplinas}">
						<option value="${d.idDisciplina}">
							<c:out value="${d.nomeDisciplina}" />
						</option>
					</c:forEach>
				</select>
			</div>
			<br> <br>
			<table>
				<tr>
					<td class="col-md-2" align="center"><input
						class="btn btn-dark col-md-11" type="submit" id="botao"
						name="botao" value="Cadastrar"></td>
					<td class="col-md-2" align="center"><input
						class="btn btn-dark col-md-11" type="submit" id="botao"
						name="botao" value="Alterar"></td>
					<td class="col-md-2" align="center"><input
						class="btn btn-dark col-md-11" type="submit" id="botao"
						name="botao" value="Excluir"></td>
				</tr>
			</table>
		</form>
	</div>
	<br>
	<c:if test="${not empty turmas}">
		<div class="table-responsive">
			<table class="table table-striped table">
				<thead>
					<tr>
						<th><b>idTurma</b></th>
						<th><b>Professor</b></th>
						<th><b>Horario de Aula</b></th>
						<th><b>Perido (Turno)</b></th>
						<th><b>Metodo Avaliativo</b></th>
						<th><b>Disciplina</b></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="t" items="${turmas}">
						<tr>
							<td><c:out value="${t.idTurma}"></c:out></td>
							<td><c:out value="${t.professor}"></c:out></td>
							<td><c:out value="${t.horarioAula}"></c:out></td>
							<td><c:out value="${t.periodoAula}"></c:out></td>
							<td><c:out value="${t.metodoAvaliativo}"></c:out></td>
							<td><c:out value="${t.disciplina.nomeDisciplina}"></c:out></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</body>
</html>