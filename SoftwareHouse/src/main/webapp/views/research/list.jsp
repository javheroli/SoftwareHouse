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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<security:authorize access="hasRole('INVESTOR')" var="isInvestor" />
<security:authorize access="hasRole('ADMIN')" var="isAdmin" />
<security:authorize access="hasRole('EXPERT')" var="isExpert" />


<jstl:if test="${isExpert}" >
<a class="actionButton" href="research/expert/create.do"> <spring:message code="research.create" /> </a>
<br>
</jstl:if>

<!-- Listing grid -->


<display:table name="researches" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">
	
<jstl:if test="${isExpert || isInvestor}">	
<display:column>
<jstl:if test="${isInvestor && not row.isCancelled && empty row.endDate}">
<spring:message code="research.investment.create" var="invest" />
<jstl:set value="${row.id}" var="researchId"/>
<jstl:if test="${requestURI eq 'research/investor/listAll.do'}" >
<a href="investment/investor/create.do?researchId=${researchId}&redirect=listAll"> ${invest} </a>
</jstl:if> 
<jstl:if test="${requestURI eq 'research/investor/listForFunding.do'}" >
<a href="investment/investor/create.do?researchId=${researchId}&redirect=listForFunding"> ${invest} </a>
</jstl:if> 
</jstl:if>

<jstl:if test="${isExpert}">
<spring:message code="research.edit" var="editResearch" />
<jstl:set value="${row.id}" var="researchId"/>
<a href="research/expert/edit.do?researchId=${researchId}&listResearch=true"> ${editResearch} </a> 
</jstl:if>
</display:column>
</jstl:if>
	
	<spring:message code="research.title" var="researchTitle" />
	<display:column property="title" title="${researchTitle}" sortable="true" />


	<spring:message code="research.projectWebpage" var="webpageTitle" />
	<display:column title="${webpageTitle}" sortable="true" >
	<a href="${row.projectWebpage}" target="_blank"> <jstl:out value="${row.projectWebpage}"/></a>
	</display:column>


	<spring:message code="research.minCost" var="minCostHeader" />
		<display:column title="${minCostHeader}" sortable="true" >
	<fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${row.minCost}" /> euros
	</display:column>
		
		
	<spring:message code="research.budget" var="budgetHeader" />
		<display:column title="${budgetHeader}"  sortable="true" >
		<fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${row.budget}" /> euros
		</display:column>			
			
		
	<spring:message code="research.show" var="showResearch" />
	<jstl:set value="${row.id}" var="researchId"/>
	<display:column>
	<jstl:choose>
	<jstl:when test="${isInvestor}">
	<a href="research/investor/display.do?researchId=${researchId}"> ${showResearch} </a> 
	</jstl:when>
	<jstl:when test="${isExpert}">
	<a href="research/expert/display.do?researchId=${researchId}"> ${showResearch} </a> 
	</jstl:when>
	<jstl:otherwise>
	<a href="research/administrator/display.do?researchId=${researchId}"> ${showResearch} </a> 
	</jstl:otherwise>
	</jstl:choose>
	
	</display:column>
		

	
	
</display:table>



