

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${permission }">

<form:form action="${actionURI}" modelAttribute="administrator" id="form" >


	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
		
		<acme:textbox code="administrator.name" path="name" />
			<br />
			<br />

	<acme:textbox code="administrator.surname" path="surname" />
			<br />
			<br />
	
	<acme:textbox code="administrator.postalAddress" path="postalAddress" />
			<br />
			<br />
	
	<acme:textbox code="administrator.phone" path="phone" />
			<br />
			<br />
	
	
	<acme:textbox code="administrator.email" path="email" />
			<br />
			<br />
			
	<acme:textbox code="administrator.linkPhoto" path="linkPhoto" />
			<br />
			<br />
	
	

	

	
	<jstl:if test="${administrator.id == 0}">
	
	
	
	<form:label path="userAccount.username">
		<spring:message code="administrator.username" />:
	</form:label>
	<spring:message code="administrator.username.placeholder" var="usernamePlaceholder"/> 
	<form:input path="userAccount.username" placeholder="${usernamePlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	<br />
	
	
	
	<form:label path="userAccount.password">
	<spring:message code="administrator.password" />:
	</form:label>
	<spring:message code="administrator.password.placeholder" var="passwordPlaceholder"/> 
	<form:password path="userAccount.password" placeholder="${passwordPlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	<br />
	

	
	
	
	</jstl:if>

	
	


<jstl:choose>
<jstl:when test="${administrator.id == 0}">
<acme:submit name="save" code="administrator.register" />&nbsp;
</jstl:when>
<jstl:otherwise>
<acme:submit name="save" code="administrator.save" />&nbsp;
</jstl:otherwise>
</jstl:choose>

	
<acme:cancel url="${redirectURI}" code="administrator.cancel" />

</form:form>





</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="administrator.nopermission" /></h3>
</jstl:if>




