<%--
 * 
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<security:authorize access="isAuthenticated()" var="authenticated" />

<jstl:if test="${authenticated}" >
<security:authentication property="principal.username" var="principalUsername" /> 
</jstl:if>

		
		
<jstl:if test="${investor.userAccount.username == principalUsername}">
<jstl:set var="permission" value="true" />
</jstl:if>
	
		
		
		
	
	
<jstl:choose>
<jstl:when test="${authenticated}">




<h2> <spring:message code="investor.profile"/> </h2>


<h2><jstl:out value="${investor.userAccount.username}"> </jstl:out></h2>

<div class="card">
	<jstl:choose>
	<jstl:when test="${not empty investor.linkPhoto}">
	<img src="${investor.linkPhoto}" alt="${investor.userAccount.username}" style="width:100%" >
	</jstl:when>
	<jstl:otherwise>
	<img src="images/anonymous.png" alt="${investor.userAccount.username}" style="width:100%" >
	</jstl:otherwise>
	</jstl:choose>
  <h1><jstl:out value="${investor.name}"> </jstl:out>  <jstl:out value="${investor.surname}"> </jstl:out></h1>
  <p class="title"><jstl:out value="${investor.email}"> </jstl:out></p>
  <p>
 <jstl:choose>
 <jstl:when test="${not empty investor.postalAddress}">  
 <jstl:out value="${investor.postalAddress}"> </jstl:out>
</jstl:when> 
<jstl:otherwise> 
<em><spring:message code="investor.postalAddress.empty" /></em>
</jstl:otherwise> 
</jstl:choose>
  </p>
    <p>
 <jstl:choose>
 <jstl:when test="${not empty investor.phone}">  
 <jstl:out value="${investor.phone}"> </jstl:out>
</jstl:when> 
<jstl:otherwise> 
<em><spring:message code="investor.phone.empty" /></em>
</jstl:otherwise> 
</jstl:choose>
  </p>
  <br />
  <div style="margin: 24px 0;">
    <span class="tooltip">
     <img src="images/thread_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="investor.numThreads"/></strong><br />
        <spring:message code="investor.numThreads.1"/> <jstl:out value="${fn:length(investor.threads)}"/> <spring:message code="investor.numThreads.2"/>
    </span>
</span>
    <span class="tooltip">
     <img src="images/post_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="investor.numPosts"/></strong><br />
        <spring:message code="investor.numPosts.1"/> <jstl:out value="${fn:length(investor.posts)}"/> <spring:message code="investor.numPosts.2"/>
    </span>
</span>
    <span class="tooltip">
     <img src="images/investments_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="investor.investments"/></strong><br />
        <spring:message code="investor.investments.1"/> <fmt:formatNumber value="${totalAmountInvested}" minFractionDigits="2" maxFractionDigits="2" /> euros <spring:message code="investor.investments.2"/>
    </span>
</span>
</div>
 <jstl:if test="${permission == true}">
<p><button class="special" onclick="redirect: location.href = 'investor/investor/edit.do?investorId=${investor.id}';"> <spring:message code="investor.edit"/></button></p>
 </jstl:if>
</div>




</jstl:when>
<jstl:otherwise>


<div> <spring:message code="investor.unauthorizedAccess.message" /> </div>
</jstl:otherwise>
</jstl:choose>




