<%--
 * 
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ page import = "java.sql.*" %>



<jstl:if test="${permission }">


	<form:form  action="problem/expert/edit.do" modelAttribute="problem">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		<jstl:if test="${problem.id == 0 }">
			<form:hidden path="contest" />
		</jstl:if>
		
		<acme:textarea code="problem.statement" path="statement"/>
		<br>
		
		<acme:textbox code="problem.linkPicture" path="linkPicture"/>
		<br>
		
		<acme:h3 code="problem.properlyMarks"/>
		<acme:textbox code="problem.mark" path="mark" placeholder="2.75"/>
		<br>
		
		<acme:submit name="save" code="problem.save"/>&nbsp;
		
		<jstl:if test="${problem.id != 0 }">
			<acme:delete code="problem.delete" codeConfirm="problem.confirmDelete"/>&nbsp;
		</jstl:if>
		
		<acme:cancel url="/contest/actor/display.do?contestId=${problem.contest.id }" code="problem.cancel"/>
		

		
	</form:form>
</jstl:if>

<jstl:if test="${!permission }">
	<acme:h3 code="problem.nopermission"/>
</jstl:if>

