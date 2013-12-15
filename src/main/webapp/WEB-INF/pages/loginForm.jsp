<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container login">
	<div class="row ">
		<div class="center span4 well">
			<legend>Connectez-vous</legend>
           <c:if test="${not empty error}">
				<div class="alert alert-error">
						<a class="close" data-dismiss="alert" href="#">×</a>Le nom d'utilisateur ou le mot de passe saisi est incorrect.
				</div>
			</c:if>
			
			<form method="post" action="<c:url value='j_spring_security_check' />" accept-charset="UTF-8">
				<input type="text" id="j_username" class="span4" name="j_username"
					placeholder="<fmt:message key="accueil.connexion.placeholder.username"/>" /> 
					<input type="password" id="j_password"
						class="span4" name="j_password" placeholder="<fmt:message key="accueil.connexion.placeholder.password"/>" /> <label
						class="checkbox"> 
					<input type="checkbox" name="remember"
						value="1" /> <fmt:message key="accueil.connexion.checkbox.resterConnecter"/>
				</label>
				<button type="submit" name="submit"
					class="btn btn-primary btn-block"><fmt:message key="accueil.connexion.btn.connexion"/></button>
			</form>
			 <p>
                <fmt:message key="login.register.1"/>
                 <a href="<%=request.getContextPath()%>/register"><fmt:message key="login.register.link"/></a><fmt:message
                           key="login.register.2"/>
                 </p>
			
		</div>
	</div>
</div>