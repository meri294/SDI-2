<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Registro</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<body>
	<form action="registrarse" method="post">

		<center>
			<h1>Registre una nueva cuenta</h1>
		</center>
		<hr>
		<br>
		<table align="center">
			<tr>
				<td align="right">Identificador</td>
				<td>
					<input type="text" name="idUsuario" align="left" size="15">
				</td>
			</tr>
			<tr>
				<td align="right">Nombre</td>
				<td><input type="text" name="nombreUsuario" align="left"
					size="30"></td>
			</tr>
			<tr>
				<td align="right">Apellidos</td>
				<td><input type="text" name="apellidosUsuario" align="left"
					size="50"></td>
			</tr>
			<tr>
				<td align="right">Email</td>
				<td><input type="text" name="emailUsuario" align="left"
					size="30"></td>
			</tr>
			<tr>
				<td align="right">Contraseña</td>
				<td><input id="etPasswd" type="password" name="passwordUsuario"
					align="left" size="15"></td>
			</tr>
			<tr>
				<td align="right">Repita la contraseña</td>
				<td><input id="etPasswd" type="password" name="repetedPassword"
					align="left" size="15"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Enviar" /></td>
			</tr>
		</table>
		<%@include file="muestraMensaje.jsp"%>
	</form>
	<a href="login.jsp">Cancelar</a>
	<hr>
</body>
</html>