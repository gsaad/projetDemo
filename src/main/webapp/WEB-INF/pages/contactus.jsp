<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="contact" class="modal hide fade in" style="display: none;">
		<div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<h3><fmt:message key="header.contacteznous"/></h3>
		</div>
		<div class="modal-body">
			<form>
				<div class="controls controls-row">
					<input id="nom" name="nom" type="text" class="span3"
						placeholder="Nom" />
				</div>
				<div class="controls controls-row">
					<input id="email" name="email" type="email" class="span3"
						placeholder="Adresse mail" />
				</div>
				<div class="controls">
					<textarea id="message" name="message" class="span5"
						placeholder="Votre message" rows="5"></textarea>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn btn-primary">Envoyer</a><a href="#" class="btn"
				data-dismiss="modal">Fermer</a>
		</div>
</div>