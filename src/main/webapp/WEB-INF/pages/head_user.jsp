<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="navbar-inner">
	<div class="container">
		<a class="btn btn-navbar" data-toggle="collapse"
			data-target=".nav-collapse"> <span class="icon-bar"></span> <span
			class="icon-bar"></span> <span class="icon-bar"></span>
		</a> <a href="#" class="brand">Application</a>
		<div class="nav-collapse collapse pull-right">
			<ul class="nav">
				<li><a href="#forgot" data-toggle="modal"><i
						class="icon-user icon-white"></i><fmt:message key="header.motdepasseoublie"/></a></li>
				<li class="divider-vertical"></li>
				<li><a href="#contact" data-toggle="modal"><i
						class="icon-envelope icon-white"></i> <fmt:message key="header.contacteznous"/></a></li>
				<li class="divider-vertical"></li>
					<li>
					<a href="<c:url value="/j_spring_security_logout" />" > 
					<i	class="icon-off icon-white"></i>
					 	<fmt:message key="header.user.deconnexion"/></a>
					</li>
					<li class="divider-vertical"></li>
			</ul>

		</div>
	</div>
</div>