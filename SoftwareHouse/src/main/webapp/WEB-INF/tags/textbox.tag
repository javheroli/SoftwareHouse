<%--
 * textbox.tag
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
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="placeholder" required="false" %>
<%@ attribute name="id" required="false" %>

<%@ attribute name="readonly" required="false" %>
<%@ attribute name="disabled" required="false" %>
<%@ attribute name="divinline" required="false" %>


<jstl:if test="${divinline == null}">
	<jstl:set var="divinline" value="false" />
</jstl:if>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<jstl:if test="${disabled == null}">
	<jstl:set var="disabled" value="" />
</jstl:if>

<jstl:if test="${divinline}">
	<jstl:set var="style" value="display: inline-block;" />
</jstl:if>

<%-- Definition --%>

<div style="${style}">
	<form:label path="${path}">
		<spring:message code="${code}" /> :
	</form:label>	
	<form:input id="${id }" path="${path}" placeholder="${placeholder}" readonly="${readonly}" disabled="${disabled}" />	
	<form:errors path="${path}" cssClass="error" />
</div>
