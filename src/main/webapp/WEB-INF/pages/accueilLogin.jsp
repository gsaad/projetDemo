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
	<!-- Navigation Bar -->
	<div class="navbar navbar-inverse navbar-fixed-top">
			<jsp:include page="head_accueil.jsp" />
	</div>

    <div class="bs-example bs-example-tabs">
		  <ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#Welcome" data-toggle="tab"> <fmt:message key="accueil.bienvenue"/> </a></li>
			<li><a onclick="document.location.href = '<%=request.getContextPath()%>/register'" data-toggle="tab"><fmt:message key="accueil.enregistrement"/></a></li>
		  </ul>
		  <div id="myTabContent" class="tab-content">
		  	<div class="tab-pane fade in active" id="Welcome">
				 <jsp:include page="loginForm.jsp" /> 
			 </div>
			<div class="tab-pane fade" id="Register">
			
		  	</div>
	</div>
		 
    </div>
	<!-- Forgot Password Model Box -->
	<jsp:include page="forgotpassword.jsp" /> 
	
	<!-- Contact Us Model Box -->
	 <jsp:include page="contactus.jsp" /> 
</body>
</html>