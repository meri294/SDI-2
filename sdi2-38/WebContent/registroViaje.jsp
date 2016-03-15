<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Registro de viaje</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<body>
	<form action="guardarViaje" method="post">

		<center>
			<h1>Registre un nuevo viaje</h1>
		</center>
		<hr>
		<br>
		<table align="center">
			<tr>
				<td><strong>Datos de salida</strong></td>
			</tr>
			<tr>
				<td align="right">Dirección</td>
				<td><input type="text" name="departureAddress" align="left"
					size="50"></td>
			</tr>
			<tr>
				<td align="right">Ciudad</td>
				<td><input type="text" name="departureCity" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Estado</td>
				<td><input type="text" name="departureState" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">País</td>
				<td><input type="text" name="departureCountry" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Código postal</td>
				<td><input type="text" name="departureCP" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Latitud</td>
				<td><input type="text" name="departureLatitude" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Longitud</td>
				<td><input type="text" name="departureLongitud" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Fecha (dd/mm/yyyy)</td>
				<td><input type="text" name="departureDate" align="left"
					size="10"></td>
			</tr>
			<tr>
				<td align="right">Hora (hh:mm)</td>
				<td><input type="text" name="departureHour" align="left"
					size="5"></td>
			</tr>
			<tr>
				<td><strong>Datos de llegada</strong></td>
			</tr>
			<tr>
				<td align="right">Dirección</td>
				<td><input type="text" name="arrivalAddress" align="left"
					size="50"></td>
			</tr>
			<tr>
				<td align="right">Ciudad</td>
				<td><input type="text" name="arrivalCity" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Estado</td>
				<td><input type="text" name="arrivalState" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">País</td>
				<td><input type="text" name="arrivalCountry" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Código postal</td>
				<td><input type="text" name="arrivalCP" align="left" size="15"></td>
			</tr>
			<tr>
				<td align="right">Latitud</td>
				<td><input type="text" name="arrivalLatitude" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Longitud</td>
				<td><input type="text" name="arrivalLongitud" align="left"
					size="15"></td>
			</tr>
			<tr>
				<td align="right">Fecha (dd/mm/yyyy)</td>
				<td><input type="text" name="arrivalDate" align="left"
					size="10"></td>
			</tr>
			<tr>
				<td align="right">Hora (hh:mm)</td>
				<td>
					<input type="text" name="arrivalHour" align="left" size="5">
				</td>
			</tr>
			<tr>
				<td><strong>Datos generales</strong></td>
			</tr>
			<tr>
				<td align="right">Fecha cierre (dd/mm/yyyy)</td>
				<td><input type="text" name="closingDate" align="left"
					size="10"></td>
			</tr>
			<tr>
				<td align="right">Hora cierre (hh:mm)</td>
				<td>
					<input type="text" name="closingHour" align="left" size="5">
				</td>
			</tr>
			<tr>
				<td align="right">Pasajeros máximos</td>
				<td><input type="text" name="maxPas" align="left" size="2"></td>
			</tr>
			<tr>
				<td align="right">Precio estimado</td>
				<td><input type="text" name="estimatedCost" align="left"
					size="7"></td>
			</tr>
			<tr>
				<td align="right">Comentarios</td>
				<td>
					<input type="text" name="comments" align="left" size="30">
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Enviar" /></td>
			</tr>
		</table>
		<a href="irAInicio">Cancelar</a> <br>
		<%@include file="muestraMensaje.jsp"%>
	</form>
	<hr>
</body>
</html>