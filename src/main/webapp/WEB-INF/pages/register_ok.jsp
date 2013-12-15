<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Application</title>
<meta name="viewport" content="width=device-width,initial-scale=1" />
<!-- StyleSheet -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/css/bootstrap-responsive.css" />

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/css/doc.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/bootstrap/css/custom.css" />
<!-- JavaScript -->
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<%=request.getContextPath()%>/resources/bootstrap/js/bootstrap.js"></script>
</head>
	<!-- Navigation Bar -->
	<div class="navbar navbar-inverse navbar-fixed-top">
			<jsp:include page="head_accueil.jsp" />
	</div>

    <div class="bs-example bs-example-tabs">
		  <ul id="myTab" class="nav nav-tabs">
			<li ><a onclick="document.location.href = '<%=request.getContextPath()%>/login'" data-toggle="tab">Bienvenue</a></li>
			<li class="active"><a href="#register" data-toggle="tab">Enregistrement</a></li>
		  </ul>
		  <div id="myTabContent" class="tab-content">
		  	<div class="tab-pane fade in active" id="Welcome">
				
			 </div>
			<div class="tab-pane fade in active" id="Register">
				<div class="container login">
					<div class="row ">
						<div class="center span4 well">
							<legend>Confirmation de création de compte</legend>
				Votre compte <c:out value="${user.login}"></c:out> a été crée, merci de vous <a href="login">connecter</a>  pour continuer.
				 </div>
				 </div>	</div>
		  	</div>
		  </div>
		 
    </div>
	<!-- Forgot Password Model Box -->
	<jsp:include page="forgotpassword.jsp" /> 
	
	<!-- Contact Us Model Box -->
	 <jsp:include page="contactus.jsp" /> 
</body>
</html>