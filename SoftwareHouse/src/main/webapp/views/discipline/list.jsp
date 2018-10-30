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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<div>
<a class="actionButton" href="discipline/administrator/create.do"> <spring:message code="discipline.create" /> </a>
</div>
<br />
<br />

<!-- Listing grid -->

<display:table name="disciplines" id="row"
	requestURI="discipline/administrator/list.do" pagesize="5" class="displaytag">
	
	



		<spring:message code="discipline.name" var="nameHeader" />
			<display:column title="${nameHeader}" property="name" sortable="true" />
			
			
	
			
		<spring:message code="discipline.delete" var="deleteDiscipline" />
		<spring:message code="discipline.confirm.delete" var="confirmDelete"/>
		<spring:message code="discipline.used" var="usedDisciplineMessage"/>
			<jstl:set value="${row.id}" var="disciplineId"/>
	 <display:column>
	 <jstl:choose>
	 <jstl:when test="${!usedDisciplines.contains(row)}">
	  <a href="discipline/administrator/delete.do?disciplineId=${disciplineId}" onclick="javascript: return confirm('${confirmDelete}')"> ${deleteDiscipline} </a>
	 </jstl:when>
	 <jstl:otherwise>
	 <span title="${usedDisciplineMessage}">---</span>
	 </jstl:otherwise>
	 </jstl:choose>
	
	 
	 </display:column>
		

	
	
</display:table>



