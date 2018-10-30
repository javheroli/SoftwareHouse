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


<!-- Listing grid -->


<display:table name="investments" id="row"
	requestURI="investment/investor/list.do" pagesize="5" class="displaytag">
	
	


	<spring:message code="investment.amount" var="amountTitle" />
	<display:column title="${amountTitle}" sortable="true" >
	<fmt:formatNumber value="${row.amount}" minFractionDigits="2" maxFractionDigits="2" /> euros
	</display:column>

	
		
	<jstl:set value="${row.research.id}" var="researchId"/>
	<spring:message code="investment.research" var="researchHeader" />
	<display:column title="${researchHeader}" sortable="true">
	<a href="research/investor/display.do?researchId=${researchId}"> <jstl:out value="${row.research.title}"/></a> 
	</display:column>
		

	
	
</display:table>



