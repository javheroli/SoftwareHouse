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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<security:authorize access="hasRole('EXPERT')" var="isExpert" />
<security:authorize access="hasRole('INVESTOR')" var="isInvestor" />
<security:authorize access="hasRole('ADMIN')" var="isAdmin" />

<jstl:choose>

<jstl:when test="${isExpert}">
<jstl:set var="permission" value="${isPrincipalMemberOfTeam}"/>
</jstl:when>

<jstl:when test="${isAdmin || isInvestor}">
<jstl:set var="permission" value="true"/>
</jstl:when>


</jstl:choose>




<jstl:choose>
<jstl:when test="${permission}">




<jstl:if test="${isInvestor && empty research.endDate && not research.isCancelled}" >
<a class="actionButton" href="investment/investor/create.do?researchId=${research.id}&redirect=display"> <spring:message code="research.investment.create" /> </a>
</jstl:if>


<jstl:if test="${isPrincipalMemberOfTeam}" >
<a class="actionButton" href="research/expert/edit.do?researchId=${research.id}&listResearch=false"> <spring:message code="research.edit" /> </a>
</jstl:if>


<jstl:if test="${isPrincipalMemberOfTeam && (not empty research.startDate) && (empty research.endDate) && (not research.isCancelled)}" >
<spring:message code="research.cancel.confirm" var="confirmCancel"/>
<a class="actionButton" href="research/expert/cancel.do?researchId=${research.id}" onclick="return confirm('${confirmCancel}')"> <spring:message code="research.cancel" /> </a>
</jstl:if>

<jstl:if test="${isPrincipalMemberOfTeam && (not empty research.startDate) && (empty research.endDate) && (not research.isCancelled)}" >
<spring:message code="research.finalize.confirm" var="confirmFinalize"/>
<a class="actionButton" href="research/expert/finalize.do?researchId=${research.id}" onclick="return confirm('${confirmFinalize}')"> <spring:message code="research.finalize" /> </a>
</jstl:if>

<spring:message code="research.format.date" var="formatDate" />

<br />
<br />
<jstl:if test="${cancelSuccess}">
<div class="alertSuccess">
  <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
  <strong><spring:message code="research.cancel.success"/></strong>
</div>
<br>
</jstl:if>

<jstl:if test="${cancelFail}">
<div class="alertFail">
  <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
  <strong><spring:message code="research.cancel.fail"/></strong>
</div>
<br>
</jstl:if>


<jstl:if test="${finalizeSuccess}">
<div class="alertSuccess">
  <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
  <strong><spring:message code="research.finalize.success"/></strong>
</div>
<br>
</jstl:if>

<jstl:if test="${finalizeFail}">
<div class="alertFail">
  <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
  <strong><spring:message code="research.finalize.fail"/></strong>
</div>
<br>
</jstl:if>

<table class="displayStyle">

<tr>
<th> <spring:message code="research" /> </th>
<th> </th>
</tr>


<tr>
<td> <strong> <spring:message code="research.title" /> : </strong> </td>
<td> <jstl:out value="${research.title}"> </jstl:out>  </td>
</tr>

<jstl:if test="${research.isCancelled}">
<tr>
<td class="redCell" colspan="2"> <strong> <spring:message code="research.isCancelled" /> </strong> </td>
</tr>

</jstl:if>


<tr>
<td> <strong> <spring:message code="research.subject" /> : </strong> </td>
<td> <jstl:out value="${research.subject}"> </jstl:out>  </td>
</tr>

<tr>
<td> <strong> <spring:message code="research.description" /> : </strong> </td>
<td> <jstl:out value="${research.description}"> </jstl:out>  </td>
</tr>

<tr>
<td> <strong> <spring:message code="research.projectWebpage" /> : </strong> </td>
<td><a href="${research.projectWebpage}" target="_blank"> <jstl:out value="${research.projectWebpage}"/></a> </td>
</tr>

<tr>
<td> <strong> <spring:message code="research.startDate" /> : </strong> </td>
<td> 
<jstl:choose>
<jstl:when test="${not empty research.startDate}">
<fmt:formatDate value="${research.startDate}" pattern="${formatDate}" />
</jstl:when>
<jstl:otherwise>
<em><spring:message code="research.startDate.empty"/></em>
</jstl:otherwise>
</jstl:choose>
</td>
</tr>

<tr>
<td> <strong> <spring:message code="research.endDate" /> : </strong> </td>
<td> 
<jstl:choose>
<jstl:when test="${not empty research.endDate}">
<fmt:formatDate value="${research.endDate}" pattern="${formatDate}" />
</jstl:when>
<jstl:otherwise>
<em><spring:message code="research.endDate.empty"/></em>
</jstl:otherwise>
</jstl:choose>
</td>
</tr>


<tr>
<td> <strong> <spring:message code="research.minCost" /> : </strong> </td>
<td> <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${research.minCost}" /> euros</td>
</tr>

<tr>
<td> <strong> <spring:message code="research.budget" /> : </strong> </td>
<td><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${research.budget}" /> euros </td>
</tr>


<tr>
<td> <strong> <spring:message code="research.team" /> : </strong> </td>
<td>
<div class="forumPost">
<jstl:forEach items="${research.team}" var="expert">
<div style="clear:both;">
<br>
<jstl:choose>
<jstl:when test="${not empty expert.linkPhoto}">
<img alt="${expert.userAccount.username }" style="float:left" class="expert" src="${expert.linkPhoto}">
</jstl:when>
<jstl:otherwise>
<img style="float:left" class="expert" src="images/anonymous.png">
</jstl:otherwise>
</jstl:choose>
<p style="position:relative; left:10px; padding-top:50px;"><a href="expert/actor/display.do?expertId=${expert.id}"> <jstl:out value="${expert.userAccount.username}"/></a></p>
</div>
</jstl:forEach>
</div>
</td>
</tr>


<tr>
<td> <strong> <spring:message code="research.investments" /> : </strong> </td>
<td>
<jstl:choose>
<jstl:when test="${not empty research.investments}">
<div class="forumPost">
<jstl:forEach items="${research.investments}" var="investment">
<div style="clear:both;">
<br>
<jstl:choose>
<jstl:when test="${not empty investment.investor.linkPhoto}">
<img style="float:left" class="investor" alt="${investment.investor.userAccount.username }" src="${investment.investor.linkPhoto}">
</jstl:when>
<jstl:otherwise>
<img style="float:left" class="investor" src="images/anonymous.png">
</jstl:otherwise>
</jstl:choose>
<p style="position:relative; left:10px; padding-top:50px;"><a href="investor/actor/display.do?investorId=${investment.investor.id}"> <jstl:out value="${investment.investor.userAccount.username}"/></a> 
<spring:message code="research.investments.text"/> <fmt:formatNumber value="${investment.amount}" minFractionDigits="2" maxFractionDigits="2" /> euros</p>
</div>
</jstl:forEach>
</div>
</jstl:when>
<jstl:otherwise>
<spring:message code="research.investments.empty"/>
</jstl:otherwise>
</jstl:choose>
</td>
</tr>


</table>



</jstl:when>
<jstl:otherwise>
<acme:h3 code="research.noPermission.view" />
</jstl:otherwise>
</jstl:choose>





