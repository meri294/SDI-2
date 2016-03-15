<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Listado de solicitantes</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div>
		<table border="1" align="center">
			<tr>
				<th>ID</th>
				<th>Nombre</th>
				<th>Estado</th>
				<th>Opciones</th>
			</tr>
			<c:forEach var="entry" items="${pendiente}" varStatus="i">
				<tr>
					<td>
						<a href="infoUsuario?id=${entry.id}">${entry.id}</a>
					</td>
					<td>${entry.name}${entry.surname}</td>
					<td>Pendiente</td>
					<td><a href="aceptar?user=${entry.id}&id=${viaje.id}">
							Aceptar</a><br> 
						<a href="excluir?user=${entry.id}&id=${viaje.id}"> 
						Excluir</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<center>
			<p>Quedan ${viaje.availablePax} plazas libres</p>
		</center>
		<a href="irAInicio">Cancelar</a>
		<%@include file="muestraMensaje.jsp"%>
	</div>
</body>
</html>