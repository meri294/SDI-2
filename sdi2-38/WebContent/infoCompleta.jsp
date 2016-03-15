<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Información participantes</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div>
		<p>
			El promotor de este viajes es: <a
				href="infoUsuario?id=${promotor.id}">${promotor.name}</a>
		</p>
		<c:choose>
			<c:when test="${participantes.isEmpty()}">
				<center>Actualmente no hay ningún participante confirmado
				</center>
			</c:when>
			<c:otherwise>
				<p>Participantes:</p>
				<ul>
					<c:forEach var="entry" items="${participantes}" 
					varStatus="i">
						<li><a href="infoUsuario?id=${entry.id}">${entry.name}
								${entry.surname}</a></li>
					</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
		<a href="irAInicio">Cancelar</a>
		<%@include file="muestraMensaje.jsp"%>
	</div>
</body>
</html>