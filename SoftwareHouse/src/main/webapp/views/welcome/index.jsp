<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:choose>
	<jstl:when test="${signUp}">

		<h2>
			<spring:message code="${welcomeMessage}" />
		</h2>
	</jstl:when>

	<jstl:otherwise>

	<p> <jstl:out value="${welcomeMessage}" /> </p>


	</jstl:otherwise>
</jstl:choose>

<p>
	<spring:message code="welcome.greeting.current.time" />
	<spring:message code = "welcome.greeting.current.time.format" var="formatWelcomeDate"/>
	<fmt:formatDate value="${moment}" pattern="${formatWelcomeDate}" />
</p>


<div id="cookieConsent">
	<div id="closeCookieConsent">x</div>
	<spring:message code="welcome.cookies.message" />
	<a href="${cookiePolicyPage}" target="_blank"> <spring:message
			code="welcome.cookies.message.informacion" />
	</a>. <a class="cookieConsentOK"><spring:message
			code="welcome.cookies.message.close" /></a>
</div>

