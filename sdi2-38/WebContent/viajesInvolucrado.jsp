<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Mis viajes</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div>
		<table border="1" align="center">
			<tr>
				<th>ID viaje</th>
				<th>Estado viaje</th>
				<th>Fecha cierre</th>
				<th>Fecha salida</th>
				<th>Fecha fecha llegada</th>
				<th>Estado</th>
				<th>Opciones</th>
			</tr>

			<c:forEach var="entry" items="${promocionados}" varStatus="i">
				<tr id="item_${i.index}">
					<td>
						<a href="mostrarViaje?id=${entry.id}">${entry.id}</a>
					</td>
					<td>${entry.status}</td>
					<td>${entry.closingDate}</td>
					<td>${entry.departureDate}</td>
					<td>${entry.arrivalDate}</td>
					<td>Promotor</td>
					<td>
						<a href="verSolicitantes?id=${entry.id}"> Ver
						solicitantes</a><br> 
						<a href="cancelarViaje?id=${entry.id}">Cancelar
							viaje</a><br> 
						<a href="modificarViaje?id=${entry.id}">Modificar
							Viaje</a>
					</td>
				</tr>
			</c:forEach>

			<c:forEach var="entry" items="${participante}" varStatus="i">
				<tr id="item_${i.index}">
					<td>
						<a href="mostrarViaje?id=${entry.id}">${entry.id}</a>
					</td>
					<td>${entry.status}</td>
					<td>${entry.closingDate}</td>
					<td>${entry.departureDate}</td>
					<td>${entry.arrivalDate}</td>
					<td>Aceptado</td>
					<td><a
						href="cancelarSolicitud?user=${user.id}
								&viaje=${entry.id}">Cancelar
							solicitud</a></td>
				</tr>
			</c:forEach>

			<c:forEach var="entry" items="${enEspera}" varStatus="i">
				<tr id="item_${i.index}">
					<td>
						<a href="mostrarViaje?id=${entry.id}">${entry.id}</a>
					</td>
					<td>${entry.status}</td>
					<td>${entry.closingDate}</td>
					<td>${entry.departureDate}</td>
					<td>${entry.arrivalDate}</td>
					<td>Pendiente</td>
					<td><a
						href="cancelarSolicitud?user=${user.id}
								&viaje=${entry.id}">Cancelar
							solicitud</a></td>
				</tr>
			</c:forEach>

			<c:forEach var="entry" items="${excluido}" varStatus="i">
				<tr id="item_${i.index}">
					<td>
						<a href="mostrarViaje?id=${entry.id}">${entry.id}</a>
					</td>
					<td>${entry.status}</td>
					<td>${entry.closingDate}</td>
					<td>${entry.departureDate}</td>
					<td>${entry.arrivalDate}</td>
					<td>Excluido</td>
					<td><a
						href="cancelarSolicitud?user=${user.id}
								&viaje=${entry.id}">Cancelar
							solicitud</a></td>
				</tr>
			</c:forEach>

			<c:forEach var="entry" items="${sinPlaza}" varStatus="i">
				<tr id="item_${i.index}">
					<td>
						<a href="mostrarViaje?id=${entry.id}">${entry.id}</a>
					</td>
					<td>${entry.status}</td>
					<td>${entry.closingDate}</td>
					<td>${entry.departureDate}</td>
					<td>${entry.arrivalDate}</td>
					<td>Sin plaza</td>
					<td><a
						href="cancelarSolicitud?user=${user.id}
								&viaje=${entry.id}">Cancelar
							solicitud</a></td>
				</tr>
			</c:forEach>
		</table>
		<a href="irAInicio">Cancelar</a>
		<%@include file="muestraMensaje.jsp"%>
	</div>
</body>
</html>