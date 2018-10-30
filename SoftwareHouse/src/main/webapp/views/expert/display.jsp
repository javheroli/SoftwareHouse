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

		
		
<jstl:if test="${expert.userAccount.username == principalUsername}">
<jstl:set var="permission" value="true" />
</jstl:if>
	
		
		
		
	
	
<jstl:choose>
<jstl:when test="${authenticated}">




<h2> <spring:message code="expert.profile"/> </h2>


<h2><jstl:out value="${expert.userAccount.username}"> </jstl:out></h2>

<div class="card">
	<jstl:choose>
	<jstl:when test="${not empty expert.linkPhoto}">
	<img src="${expert.linkPhoto}" alt="${expert.userAccount.username}" style="width:100%" >
	</jstl:when>
	<jstl:otherwise>
	<img src="images/anonymous.png" alt="${expert.userAccount.username}" style="width:100%" >
	</jstl:otherwise>
	</jstl:choose>
   <h1><jstl:out value="${expert.name}"> </jstl:out>  <jstl:out value="${expert.surname}"> </jstl:out></h1>
  <p class="title"><jstl:out value="${expert.email}"> </jstl:out></p>
  <p>
 <jstl:choose>
 <jstl:when test="${not empty expert.postalAddress}">  
 <jstl:out value="${expert.postalAddress}"> </jstl:out>
</jstl:when> 
<jstl:otherwise> 
<em><spring:message code="expert.postalAddress.empty" /></em>
</jstl:otherwise> 
</jstl:choose>
  </p>
    <p>
 <jstl:choose>
 <jstl:when test="${not empty expert.phone}">  
 <jstl:out value="${expert.phone}"> </jstl:out>
</jstl:when> 
<jstl:otherwise> 
<em><spring:message code="expert.phone.empty" /></em>
</jstl:otherwise> 
</jstl:choose>
  </p>
  <br />
  <div style="margin: 24px 0;">
    <span class="tooltip">
     <img src="images/thread_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="expert.numThreads"/></strong><br />
        <spring:message code="expert.numThreads.1"/> <jstl:out value="${fn:length(expert.threads)}"/> <spring:message code="expert.numThreads.2"/>
    </span>
</span>
    <span class="tooltip">
     <img src="images/post_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="expert.numPosts"/></strong><br />
        <spring:message code="expert.numPosts.1"/> <jstl:out value="${fn:length(expert.posts)}"/> <spring:message code="expert.numPosts.2"/>
    </span>
</span>
    <span class="tooltip">
     <img src="images/research_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="expert.researches"/></strong><br />
        <spring:message code="expert.researches.1"/> <jstl:out value="${fn:length(expert.researches)}"/> <spring:message code="expert.researches.2"/>
    </span>
</span>
    <span class="tooltip">
     <img src="images/contestsForEdition_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="expert.contestsForEdition"/></strong><br />
        <spring:message code="expert.contestsForEdition.1"/> <jstl:out value="${fn:length(expert.contestsForEdition)}"/> <spring:message code="expert.contestsForEdition.2"/>
    </span>
</span>
    <span class="tooltip">
     <img src="images/contestsForEvaluation_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="expert.contestsForEvaluation"/></strong><br />
        <spring:message code="expert.contestsForEvaluation.1"/> <jstl:out value="${fn:length(expert.contestsForEvaluation)}"/> <spring:message code="expert.contestsForEvaluation.2"/>
    </span>
</span>
<span class="tooltip">
     <img src="images/discipline_icon.png" height="50" width="50" >
    <span>
        <img class="callout" src="images/callout_black.gif" />
        <strong><spring:message code="expert.disciplines"/></strong><br />
        <spring:message code="expert.disciplines.1"/>
        <br />
        <jstl:forEach items="${expert.disciplines}" var="discipline" varStatus="status">
        <jstl:choose>
			<jstl:when test="${not status.last}">
			<jstl:out value="${discipline.name}"/>, &nbsp;
			</jstl:when>
			<jstl:otherwise>
			<jstl:out value="${discipline.name}"/>
			</jstl:otherwise>
			</jstl:choose>
        </jstl:forEach>
    </span>
</span>
</div>
 <jstl:if test="${permission == true}">
<p><button class="special" onclick="redirect: location.href = 'expert/expert/edit.do?expertId=${expert.id}';"> <spring:message code="expert.edit"/></button></p>
 </jstl:if>
</div>




</jstl:when>
<jstl:otherwise>


<div> <spring:message code="expert.unauthorizedAccess.message" /> </div>
</jstl:otherwise>
</jstl:choose>




