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

<security:authorize access="hasRole('MANAGER')">
	<a href="contest/manager/create.do"><spring:message
			code="contest.create" /></a>
	<br>
</security:authorize>

<!-- Listing grid -->


<display:table name="contests" id="contest"
	requestURI="${requestURI }" pagesize="7" class="displaytag" >
	
	<display:column>
		<jstl:set var="currentMoment" value="<%= new Timestamp(System.currentTimeMillis()) %>" />
		<jstl:choose>
			<jstl:when test="${!contest.isDraft && contest.startMoment.compareTo(currentMoment) <= 0 && contest.endMoment.compareTo(currentMoment) >= 0 }">
				<img src="images/inProgress.png" alt="inProgress" style="height:50px; width:100px;"/>
			</jstl:when>
				
			<jstl:when test="${!contest.isDraft && contest.startMoment.compareTo(currentMoment) > 0 && contest.availablePlaces > 0}">
				<img src="images/available.png" alt="available" style="height:70px; width:70px;"/>
			</jstl:when>
				
			<jstl:when test="${!contest.isDraft && contest.startMoment.compareTo(currentMoment) > 0 && contest.availablePlaces == 0}">
				<img src="images/full.png" alt="full" style="height:55px; width:80px;"/>
			</jstl:when>
				
			<jstl:when test="${!contest.isDraft && contest.endMoment.compareTo(currentMoment) < 0}">
				<img src="images/finished.png" alt="finished" style="height:50px; width:100px;"/>
			</jstl:when>
			
			<jstl:when test="${contest.isDraft}">
				<img src="images/draft.png" alt="draft" style="height:50px; width:50px;"/>
			</jstl:when>
				
		</jstl:choose>
	</display:column>
	


	<spring:message code="contest.title" var="contestHeader" />
	<display:column title="${contestHeader}" property="title" sortable="true" />

	<spring:message code="contest.startMoment"
			var="startMomentHeader" />
		<display:column title="${startMomentHeader}" sortable="true" >
		<spring:message code="contest.startMoment.pattern" var="patternMoment" />
			<fmt:formatDate value="${contest.startMoment}"
					pattern="${patternMoment}" />
			</display:column>
			
	
	<spring:message code="contest.duration" var="durationHeader" />
	<display:column title="${durationHeader}" sortable="true" >
		<jstl:out value="${contest.duration } "></jstl:out><spring:message code="contest.minutes" />
	</display:column>
	
	<spring:message code="contest.availablePlaces" var="availablePlacesHeader" />
	<display:column title="${availablePlacesHeader}" sortable="false" property="availablePlaces" />
	
	<spring:message code="contest.discipline" var="disciplineHeader" />
	<display:column title="${disciplineHeader}" sortable="true" property="discipline.name" />
	
	<spring:message code="contest.difficultyGrade" var="difficultyGradeHeader" />
	<display:column title="${difficultyGradeHeader}" sortable="true" property="difficultyGrade" />
	
	<spring:message code="contest.price" var="priceHeader" />
	<display:column title="${priceHeader}" sortable="true" >
		<jstl:out value="${contest.price } "></jstl:out>&euro;
	</display:column>
	
	<spring:message code="contest.requiredPoints" var="requiredPointsHeader" />
	<display:column title="${requiredPointsHeader}" sortable="true" property="requiredPoints" />
	
	<spring:message code="contest.prize" var="prizeHeader" />
	<display:column title="${prizeHeader}" sortable="true" >
	<jstl:out value="${contest.prize } "></jstl:out>&euro;
	</display:column>
		
	<security:authorize access="hasRole('EXPERT')">
		
			<display:column>
				<jstl:if test="${!contest.isDraft && contest.endMoment.compareTo(currentMoment) < 0 && principal.contestsForEvaluation.contains(contest) }">
					<a href="solution/expert/list.do?contestId=${contest.id }" ><spring:message code="contest.showSolutions" /></a>
				</jstl:if>
			</display:column>
	</security:authorize>
	
	
	<display:column>
		<a href="contest/actor/display.do?contestId=${contest.id }"><spring:message code="contest.displayContest"  /></a>
	</display:column>
	
	




</display:table>


