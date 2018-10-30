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

<!-- Listing grid -->





<display:table name="actors" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	


	<!-- Action links -->
	
		<jstl:choose>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'APPRENTICE'}">
		<spring:message code="actor.rol.apprentice" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'MANAGER'}">
		<spring:message code="actor.rol.manager" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'EXPERT'}">
		<spring:message code="actor.rol.expert" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'INVESTOR'}">
		<spring:message code="actor.rol.investor" var="rol"/>
		</jstl:when>
		<jstl:otherwise>
		<spring:message code="actor.rol.administrator" var="rol"/>
		</jstl:otherwise>
		</jstl:choose>
		
	
	
	<!-- Attributes -->
	
	<spring:message code="actor.rol" var="rolHeader" />
	<display:column title="${rolHeader}" sortable="true">
	<jstl:out value="${rol}" />
	
	</display:column>
	
	<spring:message code="actor.username" var="usernameHeader" />
	<display:column title="${usernameHeader}" sortable="true"> 
	<jstl:out value="${row.userAccount.username}" />
	</display:column>

	<spring:message code="actor.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true"> 
	<jstl:out value="${row.name}" /> <jstl:out value="${row.surname}" /> 
	</display:column>
	
	
	<spring:message code="actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"
		sortable="true" />

		
		<display:column>
		

			<a href="actor/actor/redirect.do?actorId=${row.id}"  >
				<spring:message code="actor.show"  />
			</a>					
		</display:column>
	
	
	
</display:table>



