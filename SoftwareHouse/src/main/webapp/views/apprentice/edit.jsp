

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


<jstl:choose>

<jstl:when test="${editPersonalData}">

<form:form action="apprentice/apprentice/edit.do" modelAttribute="apprentice">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	

	<acme:textbox code="apprentice.name" path="name" />
			<br />
			<br />

	<acme:textbox code="apprentice.surname" path="surname" />
			<br />
			<br />
	
	<acme:textbox code="apprentice.postalAddress" path="postalAddress" />
			<br />
			<br />
	
	<acme:textbox code="apprentice.phone" path="phone" />
			<br />
			<br />
	
	
	<acme:textbox code="apprentice.email" path="email" />
			<br />
			<br />
			
	<acme:textbox code="apprentice.linkPhoto" path="linkPhoto" />
			<br />
			<br />


<acme:submit name="save" code="apprentice.save" />&nbsp;
	
<acme:cancel url="actor/actor/display.do" code="apprentice.cancel" />


</form:form>


</jstl:when>
<jstl:otherwise>


<spring:message code="apprentice.termsAndConditions.message"  var="specialMessage"/>
<form:form action="apprentice/edit.do" modelAttribute="actorForm" id="form" onsubmit="if(document.getElementById('agree').checked) { return true; } else { alert('${specialMessage}'); return false; }">


	<acme:textbox code="apprentice.name" path="name" />
			<br />
			<br />


	<acme:textbox code="apprentice.surname" path="surname" />
			<br />
			<br />
	
	<acme:textbox code="apprentice.postalAddress" path="postalAddress" />
			<br />
			<br />
	
	<acme:textbox code="apprentice.phone" path="phone" />
			<br />
			<br />
	
	
	<acme:textbox code="apprentice.email" path="email" />
			<br />
			<br />
			
	<acme:textbox code="apprentice.linkPhoto" path="linkPhoto" />
			<br />
			<br />
	
	
		
	<form:label path="userAccount.username">
		<spring:message code="apprentice.username" />:
	</form:label>
	<spring:message code="apprentice.username.placeholder" var="usernamePlaceholder"/> 
	<form:input path="userAccount.username" placeholder="${usernamePlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.username" />
	<br />
	<br />
	
	
	
	
	<form:label path="userAccount.password">
	<spring:message code="apprentice.password" />:
	</form:label>
	<spring:message code="apprentice.password.placeholder" var="passwordPlaceholder"/> 
	<form:password name="password" id="password" path="userAccount.password" placeholder="${passwordPlaceholder}" size="25"/>
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	<br />
	
	<form:label path="confirmPassword">
	<spring:message code="apprentice.confirmPassword" />:
	</form:label>
	<form:password name="confirm_password" id="confirm_password" path="confirmPassword" placeholder="${passwordPlaceholder}" size="25"/> <span id="messageValidation"></span>
	<form:errors cssClass="error" path="confirmPassword" />
	<br />
	<br />
	
	
	<spring:message var="currentLanguage" code="apprentice.language" />
	<form:label path="termsAndConditions">
	<spring:message code="apprentice.termsAndConditions.1"/> <a href="welcome/termsAndConditions.do?language=${currentLanguage}" target="_blank"><spring:message code="apprentice.termsAndConditions.2"/></a>:
	</form:label>
	<form:checkbox id = "agree" path="termsAndConditions"/>
	<form:errors cssClass="error" path="termsAndConditions" />
	<br>
	<br>

<acme:submit name="save" code="apprentice.register" />&nbsp;
	
<acme:cancel url="welcome/index.do" code="apprentice.cancel" />

</form:form>




</jstl:otherwise>
</jstl:choose>



</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="apprentice.nopermission" /></h3>
</jstl:if>

<spring:message code="apprentice.password.validation.true" var="matching"/>
<spring:message code="apprentice.password.validation.false" var="notMatching"/>
<script>

$('#password, #confirm_password').on('keyup', function() {
	if ($('#password').val() == $('#confirm_password').val()) {
		$('#messageValidation').html('${matching}').css('color', 'green');
	} else
		$('#messageValidation').html('${notMatching}').css('color', 'red');
});
</script>



