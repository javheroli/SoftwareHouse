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

		
		
<jstl:if test="${apprentice.userAccount.username == principalUsername}">
<jstl:set var="permission" value="true" />
</jstl:if>
	
		
		
		
	
	
<jstl:choose>
<jstl:when test="${authenticated}">




<h2> <spring:message code="apprentice.profile"/> </h2>


<h2><jstl:out value="${apprentice.userAccount.username}"> </jstl:out></h2>

<div class="card">
	<jstl:choose>
	<jstl:when test="${not empty apprentice.linkPhoto}">
	<img src="${apprentice.linkPhoto}" alt="${apprentice.userAccount.username}" style="width:100%" >
	</jstl:when>
	<jstl:otherwise>
	<img src="images/anonymous.png" alt="${apprentice.userAccount.username}" style="width:100%" >
	</jstl:otherwise>
	</jstl:choose>
  <h1><jstl:out value="${apprentice.name}"> </jstl:out>  <jstl:out value="${apprentice.surname}"> </jstl:out></h1>
  <p class="title"><jstl:out value="${apprentice.email}"> </jstl:out></p>
  <p>
 <jstl:choose>
 <jstl:when test="${not empty apprentice.postalAddress}">  
 <jstl:out value="${apprentice.postalAddress}"> </jstl:out>
</jstl:when> 
<jstl:otherwise> 
<em><spring:message code="apprentice.postalAddress.empty" /></em>
</jstl:otherwise> 
</jstl:choose>
  </p>
    <p>
 <jstl:choose>
 <jstl:when test="${not empty apprentice.phone}">  
 <jstl:out value="${apprentice.phone}"> </jstl:out>
</jstl:when> 
<jstl:otherwise> 
<em><spring:message code="apprentice.phone.empty" /></em>
</jstl:otherwise> 
</jstl:choose>
  </p>
  <br />
  <div style="margin: 24px 0;">
    <span class="tooltip">
     <img src="images/thread_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="apprentice.numThreads"/></strong><br />
        <spring:message code="apprentice.numThreads.1"/> <jstl:out value="${fn:length(apprentice.threads)}"/> <spring:message code="apprentice.numThreads.2"/>
    </span>
</span>
    <span class="tooltip">
     <img src="images/post_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="apprentice.numPosts"/></strong><br />
        <spring:message code="apprentice.numPosts.1"/> <jstl:out value="${fn:length(apprentice.posts)}"/> <spring:message code="apprentice.numPosts.2"/>
    </span>
</span>
    <span class="tooltip">
     <img src="images/points_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="apprentice.points"/></strong><br />
        <spring:message code="apprentice.points.1"/> <jstl:out value="${apprentice.points}"/> <spring:message code="apprentice.points.2"/>
    </span>
</span>
</div>
 <jstl:if test="${permission == true}">
<p><button class="special" onclick="redirect: location.href = 'apprentice/apprentice/edit.do?apprenticeId=${apprentice.id}';"> <spring:message code="apprentice.edit"/></button></p>
 </jstl:if>
</div>




</jstl:when>
<jstl:otherwise>


<div> <spring:message code="apprentice.unauthorizedAccess.message" /> </div>
</jstl:otherwise>
</jstl:choose>




