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

<jstl:if test="${permission }">
	<display:table name="solutions" id="solution"
		requestURI="${requestURI }" pagesize="7" class="displaytag" >
	
	
		<spring:message code="solution.assessed" var="assessedHeader" />
		<display:column title="${assessedHeader}">
			<jstl:if test="${solution.mark == null }">
				<img src="images/cross.png" alt="NO" />
			</jstl:if>
			<jstl:if test="${solution.mark != null }">
				<img src="images/check.png" alt="YES" />
			</jstl:if>
		</display:column>
	
		<spring:message code="solution.mark"
				var="markHeader" />
			<display:column title="${markHeader}" sortable="true" >
				<jstl:if test="${solution.mark == null }">
					<spring:message code="solution.notAssessed" var="notAssessed"/>
					<jstl:out value="${notAssessed }"></jstl:out>
				</jstl:if>
				
				<jstl:if test="${solution.mark != null }">
					<jstl:out value="${solution.mark }"></jstl:out>
				</jstl:if>
			</display:column>
				
		<security:authorize access="hasRole('EXPERT')">
		<display:column >
			<jstl:if test="${solution.mark == null }">
				<a href="solution/expert/edit.do?solutionId=${solution.id }"><spring:message code="solution.assessSolution" /></a>
			</jstl:if>
		</display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('APPRENTICE')">
			<spring:message code="solution.contest" var="contestHeader" />
			<display:column title="${contestHeader}"  sortable="false" >
				<a href="contest/actor/display.do?contestId=${solution.application.contest.id }">
				<jstl:out value="${solution.application.contest.title }"></jstl:out>
				</a>
			</display:column>
		</security:authorize>
	
	
	</display:table>
</jstl:if>

<jstl:if test="${!permission }">
	<acme:h3 code="solution.nopermission"/>
</jstl:if>
