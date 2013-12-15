<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div class="container login">
		<div class=" row">
			<div class="center span6 well">
				<legend><fmt:message key="accueil.enregistrement.informationsPersonnelles"/></legend>
				
					<div class="center wellmodif" > 
					    <ul>
					        <li>
					            <fmt:message key="register.info.1"/>
					        </li>
					        <li>
					            <fmt:message key="register.info.2"/>
					        </li>
					    </ul>
					</div>
					<c:if test="${messageErreur != null && not empty messageErreur}">
						<div class="alert alert-error">
							<a class="close" data-dismiss="alert" href="#">×</a><fmt:message key="${messageErreur}"/>
						</div>
					</c:if>					
				<form method="POST" action="register" accept-charset="UTF-8" id = "user"
					class="form-horizontal">
					<div class="control-group">
						<label for="username" class="control-label"><fmt:message key="accueil.enregistrement.login"/></label>
						<div class="controls">
							<input type="text" id="lastname" class="span3" name="login"
								placeholder="Username" value ="${user.login}" data-required="true" />
						</div>
					</div>
					<div class="control-group">
						<label for="prenom" class="control-label"><fmt:message key="accueil.enregistrement.prenom"/></label>
						<div class="controls">
							<input type="text" id="lastName" class="span3" name="lastName"
								placeholder="<fmt:message key="accueil.enregistrement.placeholder.prenom"/>" value ="${user.lastName}"/>
					</div>
					</div>
					<div class="control-group">
						<label for="nom" class="control-label"><fmt:message key="accueil.enregistrement.nom"/></label>
						<div class="controls">
							<input type="text" id="firstName" class="span3" name="firstName"
								placeholder="<fmt:message key="accueil.enregistrement.placeholder.nom"/>" value ="${user.firstName}"/>
						</div>
					</div>
					<div class="control-group">
						<label for="email" class="control-label"><fmt:message key="accueil.enregistrement.email"/></label>
						<div class="controls">
							<input type="text" id="email" class="span3" name="email"
								placeholder="<fmt:message key="accueil.enregistrement.placeholder.email"/>" value ="${user.email}"/>
						</div>
					</div>
	
					<div class="control-group">
						<label for="password" class="control-label"><fmt:message key="accueil.enregistrement.password"/></label>
						<div class="controls">
							<input type="password" id="password" class="span3" name="password"
								placeholder="<fmt:message key="accueil.enregistrement.placeholder.password"/>" />
						</div>
						<div class="control-group">
							<label for="verifyPassword" class="control-label"><fmt:message key="accueil.enregistrement.verifmotdepasse"/></label>
							<div class="controls">
								<input type="password" id="verifyPassword" class="span3"
									name="verifyPassword" placeholder="" />
							</div>
	
						</div>
						<div class="control-group">
							<div class="controls">
								<button type="submit" name="submit" class="btn btn-primary"><fmt:message key="accueil.enregistrement.btn.valider"/></button>
								<a onclick="document.location.href = '<%=request.getContextPath()%>/register/Cancel'">
 									<button type="button"  class="btn btn-primary"><fmt:message key="accueil.enregistrement.btn.annuler"/></button>
 								</a>
								
							</div>
						</div>	
					</div>	
				</form>
			</div>
		</div>
	</div>
