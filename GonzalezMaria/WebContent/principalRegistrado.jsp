<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Bienvenido</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div>
		<center>
			<h1>Bienvenido ${user.name}</h1>
		</center>
		<ul>
			<li><a href="cerrarSesion">Cerrar sesión</a></li>
			<li><a href="abrirModificacion">Modificar datos</a></li>
			<li><a id="listaViaje" href="listarViajes">Listar viajes</a></li>
			<li><a id="registrarViaje" href="registrarViaje">Registrar
					nuevo viaje</a></li>
			<li><a href="misViajes">Mis viajes</a></li>
			<li><a href="viajesAComentar">Comentar viaje</a></li>
		</ul>
		<%@include file="muestraMensaje.jsp"%>
	</div>
</body>
</html>