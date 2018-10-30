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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.sql.*"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<!-- Listing grid -->
<form action="${requestURI }" method="get">
	<jstl:if test="${applicationStatus == null || applicationStatus == 0 }">
		<jstl:set value="checked" var="checked0"></jstl:set>
	</jstl:if>
	
	<jstl:if test="${applicationStatus == 1 }">
		<jstl:set value="checked" var="checked1"></jstl:set>
	</jstl:if>
	
	<jstl:if test="${applicationStatus == 2 }">
		<jstl:set value="checked" var="checked2"></jstl:set>
	</jstl:if>
	
	<jstl:if test="${applicationStatus == 3 }">
		<jstl:set value="checked" var="checked3"></jstl:set>
	</jstl:if>
	
	<jstl:if test="${applicationStatus == 4 }">
		<jstl:set value="checked" var="checked4"></jstl:set>
	</jstl:if>
	
	<input type="radio" name="applicationStatus" value="0" ${checked0 } > <spring:message code="application.status.all" />
	<input type="radio" name="applicationStatus" value="1" ${checked1 }> <spring:message code="application.status.accepted" />
	<input type="radio" name="applicationStatus" value="2" ${checked2 }>  <spring:message code="application.status.pending" />
	<input type="radio" name="applicationStatus" value="3" ${checked3 }> 		<spring:message code="application.status.due" />
	<input type="radio" name="applicationStatus" value="4" ${checked4 }>  <spring:message code="application.status.rejected" />
	<br />
	<br>
	<spring:message code="application.status.choose" var="choose"/>
	<input type="submit" value="${choose}">
	
	</form>
	<br>

<jstl:set var="currentMoment" value="<%= new Timestamp(System.currentTimeMillis()) %>" />

<display:table name="applications" id="solicitud"
	requestURI="${requestURI }" pagesize="7" class="displaytag" >
	

	<spring:message code="application.ticker" var="tickerHeader" />
	<display:column title="${tickerHeader}" property="ticker" sortable="true" />
	
	<jstl:choose>
		<jstl:when test="${solicitud.status.equals('PENDING') }">
			<jstl:set value="pendingApplication" var="color"></jstl:set>
		</jstl:when>
		
		<jstl:when test="${solicitud.status.equals('REJECTED') }">
			<jstl:set value="rejectedApplication" var="color"></jstl:set>
		</jstl:when>
		
		<jstl:when test="${solicitud.status.equals('ACCEPTED') }">
			<jstl:set value="acceptedApplication" var="color"></jstl:set>
		</jstl:when>
		
		<jstl:when test="${solicitud.status.equals('DUE') }">
			<jstl:set value="dueApplication" var="color"></jstl:set>
		</jstl:when>
	</jstl:choose>
	
	
	 
	<spring:message code="application.status" var="statusHeader" />
	<display:column class="${color }" title="${statusHeader}" property="status" sortable="true" />

	<spring:message code="application.moment"
			var="momentHeader" />
		<display:column title="${momentHeader}" sortable="true" >
		<spring:message code="application.moment.pattern" var="patternMoment" />
			<fmt:formatDate value="${solicitud.moment}"
					pattern="${patternMoment}" />
			</display:column>
			
	<spring:message code="application.motivationLetter" var="motivationLetterHeader" />
	<display:column title="${motivationLetterHeader}"  sortable="false" >
	
		<jstl:if test="${solicitud.motivationLetter.equals('') || solicitud.motivationLetter == null }">
			<spring:message code="application.noMotivationLetter" var="noMotivationLetter" />
			<span class="error"><jstl:out value="${noMotivationLetter }"></jstl:out></span>
		</jstl:if>
		
		<jstl:if test="${!solicitud.motivationLetter.equals('') || solicitud.motivationLetter != null }">
			<jstl:out value="${solicitud.motivationLetter }"></jstl:out>
		</jstl:if>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="application.applicant" var="applicantHeader" />
		<display:column title="${applicantHeader}"  sortable="false" >
			<a href="apprentice/actor/display.do?apprenticeId=${solicitud.applicant.id }">
				<jstl:out value="${solicitud.applicant.userAccount.username }"></jstl:out>
			</a>
		</display:column>
	</security:authorize>	
	
	<spring:message code="application.availablePlaces" var="availablePlacesHeader" />
	<display:column title="${availablePlacesHeader}"  sortable="false" >
		<jstl:out value="${solicitud.contest.availablePlaces }"></jstl:out>
	</display:column>
	
	
	
	<spring:message code="application.contest" var="contestHeader" />
	<display:column title="${contestHeader}"  sortable="true" >
		<a href="contest/actor/display.do?contestId=${solicitud.contest.id }">
			<jstl:out value="${solicitud.contest.title }"></jstl:out>
		</a>
	</display:column>
	
	
	<security:authorize access="hasRole('APPRENTICE')">
		<display:column>
			<jstl:if test="${solicitud.contest.startMoment.compareTo(currentMoment) > 0 && solicitud.contest.availablePlaces > 0 && solicitud.status.equals('DUE') }">
				<jstl:if test="${solicitud.applicant.points >= solicitud.contest.requiredPoints }">
					<acme:button name="confirm" url="application/apprentice/confirm.do?applicationId=${solicitud.id }" code="application.confirm" />
				</jstl:if>
				
				<jstl:if test="${solicitud.applicant.points < solicitud.contest.requiredPoints }">
					<acme:button name="edit" url="application/apprentice/edit.do?applicationId=${solicitud.id }" code="application.pay" />
				</jstl:if>
				
			</jstl:if>
		</display:column>
	</security:authorize>
	
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if test="${solicitud.contest.startMoment.compareTo(currentMoment) > 0 && solicitud.contest.availablePlaces > 0 && solicitud.status.equals('PENDING') }">
			<acme:button name="approve${solicitud.id }" url="application/manager/approve.do?applicationId=${solicitud.id }" code="application.approve" codeConfirm="application.confirm.approve"/>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${solicitud.contest.startMoment.compareTo(currentMoment) > 0 && solicitud.status.equals('PENDING')}">
			<acme:button name="reject${solicitud.id }" url="application/manager/reject.do?applicationId=${solicitud.id }" code="application.reject" codeConfirm="application.confirm.reject"/>
			</jstl:if>
		</display:column>
	</security:authorize>	
	
	



</display:table>


