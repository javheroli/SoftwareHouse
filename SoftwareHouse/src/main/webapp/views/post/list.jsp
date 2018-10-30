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





<security:authorize access="isAuthenticated()" var="authenticated" />
<jstl:if test="${authenticated}">
	<security:authentication property="principal.username"
		var="principalUsername" />
</jstl:if>

<security:authorize access="hasRole('ADMIN')" var="isAdmin" />

<jstl:if test="${markAsBestAnswerSuccess}">
<div class="alertSuccess">
  <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
  <strong><spring:message code="post.bestAnswer.success"/></strong>
</div>
<br>
</jstl:if>

<jstl:if test="${markAsBestAnswerFail}">
<div class="alertFail">
  <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
  <strong><spring:message code="post.bestAnswer.fail"/></strong>
</div>
<br>
</jstl:if>



<div><a href="forum/display.do?forumId=${thread.forum.id}" class="previous">&laquo; <spring:message code="post.forum.redirect"/></a></div>
<br />

<security:authorize access="isAuthenticated()">
<div><a class="actionButton" href="post/actor/create.do?threadId=${thread.id}"> <spring:message code="post.create" /> </a></div>
<br>
</security:authorize>


<jstl:if test="${thread.writer.userAccount.username eq principalUsername}">
<div><a class="actionButton" href="thread/actor/edit.do?threadId=${thread.id}"> <spring:message code="post.thread.edit" /> </a></div>
<br>

</jstl:if>

<jstl:if test="${isAdmin}">
<spring:message code="post.thread.confirm.delete" var="confirmDeleteThread"/>
<a class="actionButton" href="thread/administrator/delete.do?threadId=${thread.id}" onclick="javascript: return confirm('${confirmDeleteThread}')"> <spring:message code="post.thread.delete" /> </a>
</jstl:if>


<h3> <jstl:out value="${thread.title}"/> </h3>


<spring:message code="post.date.pattern" var="patternDate"/>
<table class="displayStyle">
<tr>
<td>

<jstl:choose>
<jstl:when test="${thread.writer.userAccount.authorities[0].authority eq 'APPRENTICE'}">
<jstl:set var="rol" value="apprentice"/>
</jstl:when>
<jstl:when test="${thread.writer.userAccount.authorities[0].authority eq 'MANAGER'}">
<jstl:set var="rol" value="manager"/>
</jstl:when>
<jstl:when test="${thread.writer.userAccount.authorities[0].authority eq 'EXPERT'}">
<jstl:set var="rol" value="expert"/>
</jstl:when>
<jstl:when test="${thread.writer.userAccount.authorities[0].authority eq 'INVESTOR'}">
<jstl:set var="rol" value="investor"/>
</jstl:when>
<jstl:when test="${thread.writer.userAccount.authorities[0].authority eq 'ADMIN'}">
<jstl:set var="rol" value="admin"/>
</jstl:when>
</jstl:choose>


<div class="forumPost">
<p><fmt:formatDate value="${thread.startMoment}" pattern="${patternDate}" /></p>
	<jstl:choose>
	<jstl:when test="${not empty thread.writer.linkPhoto}">
	<img class="${rol}" src="${thread.writer.linkPhoto}" alt="${thread.writer.userAccount.username}" style="width:100%">
	</jstl:when>
	<jstl:otherwise>
	<img class="anonymous" src="images/anonymous.png" alt="${thread.writer.userAccount.username}" style="width:100%">
	</jstl:otherwise>
	</jstl:choose>

  
  <h3><a href="actor/actor/redirect.do?actorId=${thread.writer.id}"><jstl:out value="${thread.writer.userAccount.username}"/></a></h3>
  <p class="${rol}"><strong><spring:message code="post.writer.rol.${rol}"/></strong></p>
  <p><spring:message code="post.writer.numThreads"/>: <jstl:out value="${fn:length(thread.writer.threads)}"/></p>
  <p><spring:message code="post.writer.numPosts"/>: <jstl:out value="${fn:length(thread.writer.posts)}"/></p>
  <jstl:if test="${rol eq 'apprentice'}">
  <p><spring:message code="post.apprentice.points"/>: <jstl:out value="${thread.writer.points}"/></p>
  </jstl:if>
  <jstl:if test="${rol eq 'investor'}">
  <p><spring:message code="post.investor.numInvestments"/>: <jstl:out value="${fn:length(thread.writer.investments)}"/></p>
  </jstl:if>
  <jstl:if test="${rol eq 'expert'}">
  <p><spring:message code="post.expert.expertIn"/>: </p>
  <ul>
  <jstl:forEach items="${thread.writer.disciplines}" var="discipline">
  <li> <jstl:out value="${discipline.name}"/> </li>
  </jstl:forEach>
  </ul>
  </jstl:if>
</div>

<div style="position:relative; left:15px; overflow:hidden;">
<p style="width:95%;"> <jstl:out value="${thread.text}"/></p>
<br>
<jstl:if test="${not empty thread.linksAttachments}">

<ul>
<jstl:forEach items="${thread.linksAttachments}" var="attachment">
<jstl:if test="${not empty attachment}">
<li><a href="${attachment}" target="_blank"><img src="${attachment}" alt="${attachment}" height="112" width="112"></a></li>
</jstl:if>
</jstl:forEach>
</ul>
<br>
</jstl:if>


<jstl:if test="${not empty thread.disciplines}">

<p style="color: grey; font-size: 18px"> <spring:message code="post.thread.topics"/>: &nbsp;
<jstl:forEach items="${thread.disciplines}" var="topic" varStatus="status">
<jstl:choose>
<jstl:when test="${not status.last}">
<jstl:out value="${topic.name}"/>, &nbsp;
</jstl:when>
<jstl:otherwise>
<jstl:out value="${topic.name}"/>
</jstl:otherwise>
</jstl:choose>

</jstl:forEach>
</p>
</jstl:if>
<br>
</div>
</td>
</tr>
</table>

<!-- Listing grid -->


<display:table name="posts" id="row"
	requestURI="post/list.do" pagesize="5" class="displaytag">
	
	
	<display:column>

<jstl:choose>
<jstl:when test="${row.writer.userAccount.authorities[0].authority eq 'APPRENTICE'}">
<jstl:set var="rol" value="apprentice"/>
</jstl:when>
<jstl:when test="${row.writer.userAccount.authorities[0].authority eq 'MANAGER'}">
<jstl:set var="rol" value="manager"/>
</jstl:when>
<jstl:when test="${row.writer.userAccount.authorities[0].authority eq 'EXPERT'}">
<jstl:set var="rol" value="expert"/>
</jstl:when>
<jstl:when test="${row.writer.userAccount.authorities[0].authority eq 'INVESTOR'}">
<jstl:set var="rol" value="investor"/>
</jstl:when>
<jstl:when test="${row.writer.userAccount.authorities[0].authority eq 'ADMIN'}">
<jstl:set var="rol" value="admin"/>
</jstl:when>
</jstl:choose>





<div class="forumPost">
<p><fmt:formatDate value="${row.publicationMoment}" pattern="${patternDate}" /></p>
	<jstl:choose>
	<jstl:when test="${not empty row.writer.linkPhoto}">
	<img class="${rol}" src="${row.writer.linkPhoto}" alt="${row.writer.userAccount.username}" style="width:100%">
	</jstl:when>
	<jstl:otherwise>
	<img class="anonymous" src="images/anonymous.png" alt="${row.writer.userAccount.username}" style="width:100%">
	</jstl:otherwise>
	</jstl:choose>

  
  <h3><a href="actor/actor/redirect.do?actorId=${row.writer.id}"><jstl:out value="${row.writer.userAccount.username}"/></a></h3>
  <p class="${rol}"><strong><spring:message code="post.writer.rol.${rol}"/></strong></p>
  <p><spring:message code="post.writer.numThreads"/>: <jstl:out value="${fn:length(row.writer.threads)}"/></p>
  <p><spring:message code="post.writer.numPosts"/>: <jstl:out value="${fn:length(row.writer.posts)}"/></p>
  <jstl:if test="${rol eq 'apprentice'}">
  <p><spring:message code="post.apprentice.points"/>: <jstl:out value="${row.writer.points}"/></p>
  </jstl:if>
  <jstl:if test="${rol eq 'investor'}">
  <p><spring:message code="post.investor.numInvestments"/>: <jstl:out value="${fn:length(row.writer.investments)}"/></p>
  </jstl:if>
  <jstl:if test="${rol eq 'expert'}">
  <p><spring:message code="post.expert.expertIn"/>: </p>
  <ul>
  <jstl:forEach items="${row.writer.disciplines}" var="discipline">
  <li> <jstl:out value="${discipline.name}"/> </li>
  </jstl:forEach>
  </ul>
  </jstl:if>
</div>


<div style="position:relative; left:15px; overflow:hidden;">

<jstl:if test="${not empty row.parentPost}">
<div style="border: 2px solid; background:white; margin-top:15px; width:95%">
<p><img src="images/quote_icon.png"> <spring:message code="post.parentPost.writer"/> <a href="actor/actor/redirect.do?actorId=${row.parentPost.writer.id}"><jstl:out value="${row.parentPost.writer.userAccount.username}"/></a></p>
<p>
<jstl:choose>
<jstl:when test="${row.parentPost.text eq 'DELETEDBYUSER'}">
<em><spring:message code="post.deleted.user"/></em>
</jstl:when>
<jstl:when test="${row.parentPost.text eq 'DELETEDBYADMIN'}">
<em><spring:message code="post.deleted.admin"/></em>
</jstl:when>
<jstl:otherwise>
<jstl:out value="${row.parentPost.text}" />
</jstl:otherwise>
</jstl:choose>
</p>
<jstl:if test="${not empty row.parentPost.linksAttachments}">
<br>
<ul>
<jstl:forEach items="${row.parentPost.linksAttachments}" var="attachment">
<jstl:if test="${not empty attachment}">
<li><a href="${attachment}" target="_blank"><img src="${attachment}" alt="${attachment}" height="112" width="112"></a></li>
</jstl:if>
</jstl:forEach>
</ul>
</jstl:if>
</div>
</jstl:if>

<p style="width:95%"> 
<jstl:choose>
<jstl:when test="${row.text eq 'DELETEDBYUSER'}">
<em><spring:message code="post.deleted.user"/></em>
</jstl:when>
<jstl:when test="${row.text eq 'DELETEDBYADMIN'}">
<em><spring:message code="post.deleted.admin"/></em>
</jstl:when>
<jstl:otherwise>
<jstl:out value="${row.text}"/>
</jstl:otherwise>
</jstl:choose>

</p>

<jstl:if test="${not empty row.linksAttachments}">

<ul>
<jstl:forEach items="${row.linksAttachments}" var="attachment">
<jstl:if test="${not empty attachment}">
<li><a href="${attachment}" target="_blank"><img src="${attachment}" alt="${attachment}" height="112" width="112"></a></li>
</jstl:if>
</jstl:forEach>
</ul>
</jstl:if>
<br>
<ul>
<jstl:if test="${(not row.isDeleted) && authenticated && (row.writer.userAccount.username ne principalUsername) }">
<li><a href="post/actor/create.do?postId=${row.id}"><img src="images/quote_button.png"> <spring:message code="post.create.quote"/></a></li>
<br>
</jstl:if>

<jstl:if test="${row.writer.userAccount.username eq principalUsername && not row.isDeleted}">
<li><a href="post/actor/edit.do?postId=${row.id}"><spring:message code="post.edit"/></a></li>
<br>
</jstl:if>
<jstl:if test="${isAdmin && not row.isDeleted}">
<spring:message code="post.confirm.delete" var="confirmDeletePost"/>
<li><a href="post/administrator/delete.do?postId=${row.id}" onclick="javascript: return confirm('${confirmDeletePost}')"><spring:message code="post.delete"/></a></li>
<br>
</jstl:if>
</ul>
<div style="float:right">
<ul style="position:relative;right:25px">
<jstl:if test="${row.isBestAnswer}">
<li><img src="images/best_answer.png" height="64" width="64"><span style="color:blue"><strong> <spring:message code="post.bestAnswer"/></strong></span> </li>
<br />
</jstl:if>
<jstl:if test="${thread.isBestAnswerEnabled && thread.writer.userAccount.username eq principalUsername && (not empty row.thread) && row.writer.userAccount.username ne principalUsername}">
<li><a href="post/apprentice/mark.do?postId=${row.id}"><spring:message code="post.bestAnswer.mark"/> </a> </li>
<br />
</jstl:if>
<jstl:if test="${row.isReliable}">
<li><img src="images/trusted.png" height="64" width="64"><span style="color:green"><strong> <spring:message code="post.reliable"/></strong></span> </li>
</jstl:if>
</ul>
</div>
</div>
	</display:column>
	
	
	
	
</display:table>



