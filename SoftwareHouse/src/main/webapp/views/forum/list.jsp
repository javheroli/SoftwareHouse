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



<security:authorize access="hasRole('ADMIN')" var="isAdmin" />

<jstl:if test="${isAdmin}">
<a class="actionButton" href="forum/administrator/create.do"> <spring:message code="forum.create" /> </a>
</jstl:if>


<!-- Listing grid -->


<display:table name="forums" id="row"
	requestURI="forum/list.do" pagesize="5" class="displaytag">
	
	<jstl:if test="${isAdmin}" >
	<display:column>
	<a href="forum/administrator/edit.do?forumId=${row.id}"> <spring:message code="forum.edit"/> </a>
	</display:column>
	</jstl:if>
	
	<spring:message code="forum.name" var="nameHeader"/>
	<display:column title="${nameHeader}">
	<jstl:choose>
	<jstl:when test="${empty row.linkPicture}">
	<img src="images/forum.png" alt="${row.name}" width="112" height="112" style="float:left">
	</jstl:when>
	<jstl:otherwise>
	<img src="${row.linkPicture}" alt="${row.name}" width="112" height="112" style="float:left">
	</jstl:otherwise>
	</jstl:choose>
	<p style="position:relative; left:20px"><strong><a href="forum/display.do?forumId=${row.id}"><jstl:out value="${row.name}"/></a></strong></p>
	<p style="position:relative; left:20px"><em><jstl:out value="${row.description}"/></em></p>
	</display:column>
	
	<spring:message code="forum.numThreadsAndNumPosts" var="numThreadsAndNumPostsHeader"/>
	<display:column title="${numThreadsAndNumPostsHeader}">
	<p> <spring:message code="forum.threads"/>: <jstl:out value="${fn:length(row.threads)}"/></p>
	<p> <spring:message code="forum.posts"/>: <jstl:out value="${row.numPosts}"/></p>
	
	</display:column>

	<spring:message code="forum.lastPost" var="lastPost"/>
	<spring:message code="forum.date.pattern" var="patternDate"/>
	<display:column title="${lastPost}">
	<jstl:choose>
	<jstl:when test="${not empty row.lastPost}">
	<p><a href="post/list.do?threadId=${row.lastPost.topic.id}"><jstl:out value="${row.lastPost.topic.title}"/></a></p>
	<p><spring:message code="forum.lastPost.by"/> <a href="actor/actor/redirect.do?actorId=${row.lastPost.writer.id}"> <jstl:out value="${row.lastPost.writer.userAccount.username}"/></a></p>
	<p><fmt:formatDate value="${row.lastPost.publicationMoment}" pattern="${patternDate}" /></p>
	</jstl:when>
	<jstl:otherwise>
	<spring:message code="forum.lastPost.empty"/>
	</jstl:otherwise>
	</jstl:choose>
	</display:column>
	
	
	
	
</display:table>



