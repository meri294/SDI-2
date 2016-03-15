<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Información del viaje "${viaje.id}"</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div>
		<table border="1" align="center">
			<tr>
				<th>Origen</th>
				<th>Fecha salida</th>
				<th>Destino</th>
				<th>Fecha llegada</th>
				<th>Plazas libres</th>
				<th>Plazas totales</th>
				<th>Precio</th>
				<th>Fecha cierre</th>

			</tr>

			<tr>
				<td>${viaje.departure.address}-${viaje.departure.zipCode},
					${viaje.departure.city}(${viaje.departure.city})</td>
				<td>${viaje.departureDate}</td>
				<td>${viaje.destination.address}-${viaje.destination.zipCode},
					${viaje.destination.city}(${viaje.destination.city})</td>
				<td>${viaje.arrivalDate}</td>
				<td>${viaje.availablePax}</td>
				<td>${viaje.maxPax}</td>
				<td>${viaje.estimatedCost}</td>
				<td>${viaje.closingDate}</td>
			</tr>
		</table>

		<center>
			<p>
				Si desea consultar más información sobre este viaje (promotor,
				viajeros confirmados, comentarios...) por favor pulse <a
					href="masInfo?id=${viaje.id}">aquí</a>
			</p>
		</center>
		<a href="irAInicio">Cancelar</a>
		<%@include file="muestraMensaje.jsp"%>
	</div>
</body>
</html>