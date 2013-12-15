<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="forgot" class="modal hide fade in" style="display: none;">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<h3><fmt:message key="header.motdepasseoublie"/></h3>
		</div>
		<div class="modal-body">
			<p>Entrer votre username pour initialiser votre mot de passe</p>
			<form>
				<div class="controls controls-row">
					<input id="name" name="name" type="text" class="span3"
						placeholder="username" />
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn btn-primary">Envoyer</a><a href="#" class="btn"
				data-dismiss="modal">Fermer</a>
		</div>
</div>