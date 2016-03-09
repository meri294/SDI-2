<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4
/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Modificar datos de usuario</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<jsp:useBean id="user" class="uo.sdi.model.User" scope="session" />
	<form action="modificarDatos" method="POST">
		<table>
			<tr>
				<td>Login:</td>
				<td id="login">${user.login}</td>
			</tr>
			<tr>
				<td>Nombre:</td>
				<td id="name"><input type="text" name="name" size="15"
					value="${user.name}"></td>
			</tr>
			<tr>
				<td>Apellidos:</td>
				<td id="surname"><input type="text" name="surname" size="30"
					value="${user.surname}"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td id="email"><input type="text" name="email" size="15"
					value="${user.email}"></td>
			</tr>
			<tr>
				<td>Contraseña actual:</td>
				<td id="oldPassword"><input type="password" name="oldPassword"
					size="15"></td>
			</tr>
			<tr>
				<td>Nueva contraseña:</td>
				<td id="newPassword"><input type="password" name="newPassword"
					size="15"></td>
			</tr>
			<tr>
				<td>Repita la contraseña:</td>
				<td id="repPassword"><input type="password" name="repPassword"
					size="15"> <input type="submit" value="Modificar">
				</td>
			</tr>
		</table>
		<ul>
			<li class="dosLeft">
				<a href="irAInicio">Cancelar</a>
			</li>
			<li class="dosRight"><a href="cerrarSesion">Cerrar sesión</a></li>
		</ul>
		<br>Es Vd el usuario número: ${contador}
		<%@include file="muestraMensaje.jsp"%>
	</form>
</body>
</html>
