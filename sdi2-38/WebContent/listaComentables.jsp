<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Viajes a comentar</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${viajes.isEmpty()}">
				<center>Actualmente no hay viajes para comentar</center>
			</c:when>
			<c:otherwise>
				<table border="1" align="center">
					<tr>
						<th></th>
						<th>ID viaje</th>
						<th>Origen</th>
						<th>Destino</th>
						<th>Fecha</th>
					</tr>

					<c:forEach var="entry" items="${viajes}" varStatus="i">
						<tr id="item_${i.index}">
							<td><a id="comentar_${entry.id}"
								href="comentar?id=${entry.id}"> 
								Comentar</a>
							</td>
							<td>
								<a href="mostrarViaje?id=${entry.id}">
									${entry.id}</a>
							</td>
							<td>${entry.departure.city}</td>
							<td>${entry.destination.city}</td>
							<td>${entry.departureDate}</td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
		<a href="irAInicio">Cancelar</a>
		<%@include file="muestraMensaje.jsp"%>
	</div>
</body>
</html>