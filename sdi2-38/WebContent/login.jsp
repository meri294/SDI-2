<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Inicie sesión</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div>
		<form action="validarse" method="post">


			<center>
				<h1>Inicie sesión</h1>
			</center>
			<hr>
			<br>
			<table align="center">
				<tr>
					<td align="right">Su identificador de usuario</td>
					<td><input type="text" name="nombreUsuario" align="left"
						size="15"></td>
				</tr>
				<tr>
					<td align="right">Su contraseña</td>
					<td><input id="etPasswd" type="password"
						name="passwordUsuario" align="left" size="15"></td>
				</tr>
				<tr>
					<td><input type="submit" value="Enviar" /></td>
				</tr>
			</table>
			<ul>
				<li class="dosLeft"><a id="listarViajes" href="listarViajes">
					Lista de viajes</a>
				</li>
				<li class="dosRight"><a id="registrarse" href="abrirRegistro">
					Registrarse</a>
				</li>
			</ul>
		</form>
		<hr>
		<br>
		<%@include file="muestraMensaje.jsp"%>
	</div>
</body>
</html>