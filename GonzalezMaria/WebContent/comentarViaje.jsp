<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Comentar viaje</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>

	<form action="guardarComentario?id=${viaje}" method="post">
		<center>
			<h1>Escriba un comentario</h1>
		</center>
		<hr>
		<br>
		<table align="center">
			<tr>
				<td align="right">Usuario</td>
				<td>
					<select name="userComentado">
						<c:forEach var="entry" items="${participantes}" 
						varStatus="i">
							<option value="${entry.id}">${entry.name}
								${entry.surname}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">Puntación (0-10)</td>
				<td>
					<input type="text" name="points" align="left" size="2">
				</td>
			</tr>
			<tr>
				<td align="right">Comentario</td>
				<td>
					<textarea name="comment" cols="40" rows="10">
					</textarea>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Enviar" /></td>
			</tr>
		</table>
		<%@include file="muestraMensaje.jsp"%>
	</form>
</body>
</html>
