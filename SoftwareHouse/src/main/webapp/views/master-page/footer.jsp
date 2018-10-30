<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />
<p>
SoftwareHouse (VAT: ESX90417312)
<br />
<spring:message code="master.page.footer.address.1" />
<br />
<spring:message code="master.page.footer.address.2" />
</p>

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> SoftwareHouse, Inc. </b>