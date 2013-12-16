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
	$('input[id=lefile]').change(function() {
			$('#photoCover').val($(this).val());
		});
		$("#datep").datepicker();
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
		<li class="active"><a href="#Welcome" data-toggle="tab">La
				liste des documents</a></li>
		<li><a
			onclick="document.location.href = '<%=request.getContextPath()%>/modifierUser'"
			data-toggle="tab">Mon compte</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="Welcome">
			<div class="container login">
				<div class=" row">
					<div class="center span5 well">
						<legend>
							<fmt:message key="document.add" />
						</legend>
						<div class="wellmodif center">
							<ul>
								<li><fmt:message key="addDoc.info.1" /></li>
							</ul>
						</div>
						<c:if test="${messageErreur != null && not empty messageErreur}">
							<div class="alert alert-error">
								<a class="close" data-dismiss="alert" href="#">×</a>
								<c:out value=" ${messageErreur}"></c:out>
							</div>
						</c:if>
						<form method="POST"
							action="<%=request.getContextPath()%>/document/addDocForm"
							accept-charset="UTF-8" id="documentForm"
							enctype="multipart/form-data" class="form-horizontal">
							<div class="control-group">
								<label for="intituleDocument" class="control-label"><fmt:message key="listeDocument.intitule"/></label>
								<div class="controls">	
									<input id="intitule" name="intituleDocument" type="text"
										class="span3"
										placeholder="" />
									</div>
							</div>
							<div class="control-group">
								<label for="idTypeDocument" class="control-label">Type du document </label>
								<div class="controls">
									<select id="idTypeDocument" name="idTypeDocument" class="span3"
										placeholder="Type document">
										<c:forEach var="typeDocument" items="${typeDocuments}">
											<option value="${typeDocument.idTypeDocument}">${typeDocument.libelleTypeDocument}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label for="periode" class="control-label">Période de référence</label>
								<div class="controls">
									<div data-date-minviewmode="months" data-date-viewmode="months"  id="datep" 
										data-date-format="mm/yyyy" data-date="12/2014" 
										class=" date">
										<input type="text" readonly value="" size="16"
											class="span2" id="periode" name="periode"> <span class="add-on"><i
											class="icon-calendar"></i></span>
									</div>
								</div>	
							</div>
							<div class="control-group">
							<label for="fileData" class="control-label">Rechercher un fichier</label>
								<div class="controls">
									<input id="lefile" type="file" style="display: none"
										name="fileData">
									<div class="input-append">
										<input id="photoCover" class="span2" type="text" disabled>
										<a class="btn" onclick="$('input[id=lefile]').click();">Ajouter
											un fichier</a>
									</div>
								</div>	
							</div>
								<div class="controls">
									<button type="submit" name="submit" class="btn btn-primary">
										<fmt:message key="accueil.enregistrement.btn.valider" />
									</button>
									<a
										onclick="document.location.href = '<%=request.getContextPath()%>/listeDocs'">
										<button type="button" class="btn btn-primary">
											<fmt:message key="accueil.enregistrement.btn.annuler" />
										</button>
									</a>
								</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="tab-pane fade" id="user"></div>
	</div>
</div>
<!-- Forgot Password Model Box -->
	<jsp:include page="forgotpassword.jsp" /> 
	
	<!-- Contact Us Model Box -->
	 <jsp:include page="contactus.jsp" /> 
</body>
</html>