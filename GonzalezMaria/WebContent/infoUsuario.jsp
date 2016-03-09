<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ShareMyTrip - Valoraciones de usuario</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${valoraciones.isEmpty()}">
				<center>Actualmente no hay valoraciones sobre el usuario
					seleccionado</center>
			</c:when>
			<c:otherwise>
				<ul>
					<c:forEach var="entry" items="${valoraciones}" 
					varStatus="i">
						<li>${entry.comment}(${entry.value})</li>
					</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
		<a href="irAInicio">Cancelar</a>
		<%@include file="muestraMensaje.jsp"%>
	</div>
</body>
</html>