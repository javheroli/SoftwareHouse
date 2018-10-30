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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>




<div><a href="forum/list.do" class="previous">&laquo; <spring:message code="forum.list.redirect"/></a></div>
<br />

<security:authorize access="isAuthenticated()">
<a class="actionButton" href="thread/actor/create.do?forumId=${forumId}"> <spring:message code="forum.thread.create" /> </a>
</security:authorize>


<h3 style="color:purple"><strong><jstl:out value="${name}"/></strong></h3>
<p><em><jstl:out value="${description}"/></em></p>


<!-- Listing grid -->

<spring:message code="forum.date.pattern" var="patternDate"/>
<display:table name="threads" id="row"
	requestURI="forum/display.do" pagesize="5" class="displaytag">
	
	
	<spring:message code="forum.thread.topics"
	var="topicsHeader" />
	
	<display:column title="${topicsHeader}">
	
	
	<jstl:choose>
	<jstl:when test="${not empty row.disciplines}">
	<ul>
	<jstl:forEach items="${row.disciplines}" var="discipline">
	<li> <span class="title"><jstl:out value="${discipline.name}"/></span></li>
	</jstl:forEach>
	</ul>
	</jstl:when>
	<jstl:otherwise>
	<spring:message code="forum.thread.disciplines.empty"/>
	</jstl:otherwise>
	</jstl:choose>
	
	
	</display:column>
	
	
	<spring:message code="forum.thread.title" var="titleHeader"/>
	<display:column title="${titleHeader}" sortable="true">
	<p><a class="specialLink" href="post/list.do?threadId=${row.id}"><jstl:out value="${row.title}"/></a></p>
	<p><spring:message code = "forum.thread.by"/> <a href="actor/actor/redirect.do?actorId=${row.writer.id}"><jstl:out value="${row.writer.userAccount.username}"/></a>, <fmt:formatDate value="${row.startMoment}" pattern="${patternDate}" />
	<p><i><spring:message code = "forum.thread.lastModification"/>: <fmt:formatDate value="${row.momentLastModification}" pattern="${patternDate}" /></i> </p>
	</display:column>
	
	<spring:message code="forum.thread.numPosts" var="numPostsHeader"/>
	<display:column title="${numPostsHeader}" sortable="true">
	<span style="font-size:45px;"><jstl:out value="${row.numPosts}"/></span>
	</display:column>	


	<spring:message code="forum.thread.lastPostBy" var="lastPostBy"/>
	<display:column title="${lastPostBy}" sortable="true">
	<jstl:choose>
	<jstl:when test="${not empty row.lastPost}">
	<p><a href="actor/actor/redirect.do?actorId=${row.lastPost.writer.id}"> <jstl:out value="${row.lastPost.writer.userAccount.username}"/></a></p>
	<p><fmt:formatDate value="${row.lastPost.publicationMoment}" pattern="${patternDate}" /></p>
	</jstl:when>
	<jstl:otherwise>
	<spring:message code="forum.thread.lastPostBy.empty"/>
	</jstl:otherwise>
	</jstl:choose>
	</display:column>
	
	
	
	
</display:table>



