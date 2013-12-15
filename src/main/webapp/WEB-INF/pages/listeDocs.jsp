<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container login">
	<div class="row ">
		<div class="center span10 well">
		<c:if test="${not empty messageConfirmation}">
				<div class="alert alert-success center">
						<a class="close" data-dismiss="alert" href="#">×</a>${messageConfirmation}
				</div>
		</c:if>
		<c:if test="${messageErreur != null && not empty messageErreur}">
						<div class="alert alert-error">
							<a class="close" data-dismiss="alert" href="#">×</a>${messageErreur}
						</div>
		</c:if>	
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="example">
				<thead>
					<tr>
						<th><fmt:message key="listeDocument.intitule"/></th>
						<th><fmt:message key="listeDocument.date"/></th>
						<th><fmt:message key="listeDocument.type"/></th>
						<th><fmt:message key="listeDocument.mois"/></th>
						<th><fmt:message key="listeDocument.annee"/></th>
						<th><fmt:message key="listeDocument.action"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="document" items="${listDocs}">
					<tr >
						<%-- <td>
							<div class="checkbox" >
								<input type="checkbox" id="checkbox_id_${document.pk}">								
							</div>
						</td> --%>
						<td><c:out value=" ${document.intitule}"></c:out></td>
						<td><c:out value=" ${document.dateAjout}"></c:out></td>
						<td><c:out value=" ${document.typeDocument.libelleTypeDocument}"></c:out></td>
						<td><c:out value=" ${document.libelleMois}"></c:out></td>
						<td><c:out value=" ${document.annee}"></c:out></td>
						<td class="center">
							<a href="<%=request.getContextPath()%>/document/files/${document.pk}" target="_blank">   <i class="icon-file"></i></a>
							<a href="<%=request.getContextPath()%>/document/update?idDocument=${document.pk}">   <i class="icon-pencil"></i></a> 
							<a href="#" data-target="#modal-item" data-toggle="modal" class="modal-link" id ="<%=request.getContextPath()%>/document/delete?idDocument=${document.pk}"><i class="icon-trash"></i></a>
						</td>
					</tr>
					</c:forEach>	
				</tbody>
			</table>
			<div class="row">
				<div class=" span4 ">
					<p>
						<button type="button" class="btn btn-primary" onclick="document.location.href = '<%=request.getContextPath()%>/document/addDocForm'">Nouveau Document </button>
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="myModal">
	<div class="modal hide" id="modal-item">	
	    <form method="POST" class="form-horizontal">
	
	    <div class="modal-body">
	        Merci de confirmer la suppression du document
	    </div>
	
	    <div class="modal-footer">
	    	<a href = "#" class="btn btn-primary" id ="hrefbtn">Supprimer</a>
	        <a href="#" class="btn" data-dismiss="modal">Fermer</a>
	    </div>
	    </form>
	</div>
</div>
<script>
$(function() {
    $(".modal-link").click(function(event) {
        event.preventDefault();
        $('#myModal').removeData("modal");
        //$('#myModal').modal({remote: $(this).attr("href")})
        $('#hrefbtn').attr("href", $(this).attr("id"));
    })
})
</script>