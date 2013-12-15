<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Application </title>
	<jsp:include page="head.jsp" />
</head>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container"></div>
	</div>
</div>
<div class="bs-example bs-example-tabs">
	<div class="tab-pane fade in active">
		<div class="container login">
			<div class=" row">
				<div class="center span7 well">

					<h1>Erreur interne du serveur</h1>
					<p style="text-align:center;">
					<a href="<%=request.getContextPath()%>/login">Retour à la page d'accueil</a>
					 <p>
					
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>