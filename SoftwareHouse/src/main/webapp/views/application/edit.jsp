<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<jstl:if test="${permission}" >
<form:form  action="application/apprentice/edit.do" modelAttribute="application" onsubmit="return checkCreditCard()">

	<form:hidden path="id" />
	
	<form:hidden path="version" />
	
	<jstl:if test="${application.id == 0 }">
		<form:hidden path="contest" />
	</jstl:if>
	
	<jstl:if test="${!hasEnoughPoints && application.id != 0 }">
		<fieldset style="width: 40%;">
		<legend><spring:message code="application.creditCard" />:</legend>
		<br>
		
		<acme:textbox id="holderName" code="application.creditCard.holderName" path="creditCard.holderName"/>
		<br>
		<br>
		
		<acme:textbox id="brandName" code="application.creditCard.brandName" path="creditCard.brandName"/>
		<br>
		<br>
		
		<acme:textbox id="number" placeholder = "4024007148621138" code="application.creditCard.number" path="creditCard.number"/>
		<br>
		<br>
		
		<acme:textbox  placeholder = "08" id="expirationMonth" code="application.creditCard.expirationMonth" path="creditCard.expirationMonth"/>
		<br>
		<br>
		
		<acme:textbox  placeholder = "21" id="expirationYear" code="application.creditCard.expirationYear" path="creditCard.expirationYear"/>
		<br>
		<br>
		
		<acme:textbox  placeholder = "422" id="CVV" code="application.creditCard.CVV" path="creditCard.CVV"/>
		<br>
		<br>
		
		</fieldset>
		<br>
		<br>
	</jstl:if>
	
	<jstl:if test="${hasEnoughPoints }">
		<h3 style="color:#80ff80;"><spring:message code="application.enoughPoints" /></h3>
		<h3 style="color:#80ff80;"><spring:message code="application.thanks" /></h3>
	</jstl:if>
	
	<jstl:if test="${application.id == 0 }">
	<acme:textarea code="application.motivationLetter" path="motivationLetter"/>
	<br>
	<br>
	</jstl:if>
	
	
	
	<acme:submit  name="save" code="application.apply"/>&nbsp; 
	
	<jstl:if test="${application.id != 0 }">
	<acme:cancel url="application/apprentice/list.do" code="application.cancel"/>
	</jstl:if>
	
	
	<jstl:if test="${application.id == 0 }">
	<acme:cancel url="contest/actor/display.do?contestId=${application.contest.id }" code="application.cancel"/>
	</jstl:if>	
	
	
	
</form:form>
</jstl:if>

<jstl:if test="${!permission }" >
	<acme:h3 code="application.nopermisiontobehere"/>
</jstl:if>



<spring:message code="application.creditCard.expired" var="creditCardWarning" />

<script type="text/javascript">
function checkCreditCard() {
    var expirationYear = document.getElementById("expirationYear").value;
    var expirationMonth = document.getElementById("expirationMonth").value;
    var year = "${year}";
    var month = "${month}";
    
    expirationMonth = parseInt(expirationMonth, 10);
    
	if (expirationYear > 9 && expirationYear < 100 && expirationMonth > 0 && expirationMonth < 13) {
    if (year > expirationYear || (year == expirationYear && month >= expirationMonth)) {
        alert("${creditCardWarning}");
        return false;
    }
	}
}

$(document).ready(function() {
	quitarCreditCardFicticia();
});
</script>








