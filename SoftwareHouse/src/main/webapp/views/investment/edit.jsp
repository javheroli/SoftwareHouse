

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<jstl:choose>
	<jstl:when
		test="${(not investment.research.isCancelled) && (empty investment.research.endDate)}">


		<form:form action="investment/investor/create.do"
			modelAttribute="investment" id="form" onsubmit="return checkCreditCard()">


			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="research" />
			
			

			<acme:textbox code="investment.amount.form" path="amount" />
			<br />
			<br />

			
			<fieldset style="width: 40%;">
	<legend><spring:message code="investment.creditCard" />:</legend>
	<br>
	
	<acme:textbox code="investment.creditCard.holderName" path="creditCard.holderName"/>
	<br>
	<br>
	
	<acme:textbox code="investment.creditCard.brandName" path="creditCard.brandName"/>
	<br>
	<br>
	
	<acme:textbox placeholder = "4024007148621138" code="investment.creditCard.number" path="creditCard.number"/>
	<br>
	<br>
	
	<acme:textbox placeholder = "08" id = "expirationMonth" code="investment.creditCard.expirationMonth" path="creditCard.expirationMonth"/>
	<br>
	<br>
	
	<acme:textbox placeholder = "21" id="expirationYear" code="investment.creditCard.expirationYear" path="creditCard.expirationYear"/>
	<br>
	<br>
	
	<acme:textbox placeholder = "422"  code="investment.creditCard.CVV" path="creditCard.CVV"/>
	<br>
	<br>
	
	</fieldset>
			
			
			


<acme:submit name="${save}" code="investment.save" />&nbsp;
	
<acme:cancel url="${redirectURI}" code="investment.cancel" />

		</form:form>


	</jstl:when>
	<jstl:otherwise>
		<acme:h3 code="investment.noPermission.edit" />
	</jstl:otherwise>
</jstl:choose>


<spring:message code="investment.creditCard.expired" var="creditCardWarning" />

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
</script>



