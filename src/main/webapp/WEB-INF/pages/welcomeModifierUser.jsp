<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Application</title>
	<jsp:include page="head.jsp" />
<script>
	 $(document).ready(function () {
		$("#datepicker").datepicker();
		});
		
	</script>
	<script type="text/javascript" charset="utf-8">
		$(document).ready(function() {
			$('#example').dataTable( {
				"aaSorting": [[ 4, "desc" ]],
			 	"oLanguage": {
		        	 "sSearch": "Rechercher",
		        	 "sZeroRecords" : "La liste est vide",
		        	 "oPaginate": {
		        		 "sNext" : "Suivant",
			        	 "sPrevious" : "Précédent"
		        	 },
		        	 "sInfo" :"_START_ à _END_ sur _TOTAL_ documents",
		        	 "sLengthMenu" : "_MENU_ documents",
		       	}
			} );
		} );
	</script>
</head>
	<!-- Navigation Bar -->
	<div class="navbar navbar-inverse navbar-fixed-top">
			<jsp:include page="head_user.jsp" />
	</div>
    <div class="bs-example bs-example-tabs">
		  <ul id="myTab" class="nav nav-tabs">
			<li><a onclick="document.location.href = '<%=request.getContextPath()%>/document/listeDocs'" data-toggle="tab">La liste des documents</a></li>
			<li class="active"><a href="#user" data-toggle="tab">Mon compte</a></li>
		  </ul>
		  <div id="myTabContent" class="tab-content">
		  	<div class="tab-pane fade in active" id="Welcome">
					
			 </div>
			<div class="tab-pane fade in active" id="user">
			 <jsp:include page="modifierUser.jsp" />
				 
		  	</div>
		  </div>
		 
    </div>
	<!-- Forgot Password Model Box -->
	<jsp:include page="forgotpassword.jsp" /> 
	
	<!-- Contact Us Model Box -->
	 <jsp:include page="contactus.jsp" /> 
</body>
</html>