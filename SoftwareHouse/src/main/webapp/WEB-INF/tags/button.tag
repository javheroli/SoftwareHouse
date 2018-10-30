<%--
 * cancel.tag
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
 
<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="code" required="true" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="name" required="false" %>
<%@ attribute name="codeConfirm" required="false" %>



<jstl:if test="${name == null}">
	<jstl:set var="name" value="button" />
</jstl:if>

<jstl:if test="${codeConfirm == null }">
	<jstl:set var="onClick" value="redirect: location.href = '${url}';" />
</jstl:if>

<jstl:if test="${codeConfirm != null }">
<script>
var header = "${name}";
var funcion = "confirmAndRedirect" + header;
function confirmAndRedirect${name}() {
    var ask = window.confirm("<spring:message code='${codeConfirm }' />");
    if (ask) {
        window.location.href = "${url}";

    }
}
</script>
	<jstl:set var="onClick" value="confirmAndRedirect${name }();" />
</jstl:if>



<%-- Definition --%>

<input name="${name }" type="button" 
value="<spring:message code="${code }" />"
onclick="${onClick}" 

/>
