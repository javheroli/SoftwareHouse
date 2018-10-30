

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ page import = "java.sql.*" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<!-- Listing grid -->

	
	<h3><spring:message code="administrator.statistics" /></h3>
	
	<table class="displayStyle">
	
		<tr>
			<th colspan="5"><spring:message code="administrator.statistics" /></th>
		</tr>
		
		<tr>
			<th><spring:message code="administrator.metrics" /></th>
			<th><spring:message code="administrator.average" /></th>
			<th><spring:message code="administrator.min" /></th>
			<th><spring:message code="administrator.max" /></th>
			<th><spring:message code="administrator.std" /></th>
			
			
		</tr>
		
		<tr>
			<td><spring:message code="administrator.threadsPerForum" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getAvgThreadsPerForum}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMinThreadsPerForum}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMaxThreadsPerForum}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getStdDeviationThreadsPerForum}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.postsPerThread" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getAvgPostsPerThread}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMinPostsPerThread}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMaxPostsPerThread}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getStdDeviationPostsPerThread}" /></td>
			
		</tr>
		
		<tr>
			<td><spring:message code="administrator.applicationsPerContest" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getAvgApplicationsPerContest}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMinApplicationsPerContest}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMaxApplicationsPerContest}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getStdDeviationApplicationsPerContest}" /></td>
			
		</tr>
		
		<tr>
			<td><spring:message code="administrator.problemsPerContest" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getAvgProblemsPerContest}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMinProblemsPerContest}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMaxProblemsPerContest}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getStdDeviationProblemsPerContest}" /></td>
		</tr>
		
		
		<tr>
			<td><spring:message code="administrator.investmentsPerResearch" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getAvgInvestmentsPerResearch}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMinInvestmentsPerResearch}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getMaxInvestmentsPerResearch}" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getStdDeviationInvestmentsPerResearch}" /></td>
		</tr>
		
		
		
	</table>
	
	
	<h3><spring:message code="administrator.ratios" /></h3>
	
	<table class="displayStyle">
		<tr>
			<th colspan="2"><spring:message code="administrator.ratios" /></th>
		</tr>
		
		<tr>
			<th><spring:message code="administrator.metrics" /></th>
			<th><spring:message code="administrator.value" /></th>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioAcceptedApplications" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getRatioAcceptedApplications}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioDueApplications" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getRatioDueApplications}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioPendingApplications" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getRatioPendingApplications}" /></td>
		</tr>
		
		<tr>
			<td><spring:message code="administrator.ratioRejectedApplications" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getRatioRejectedApplications}" /></td>
		</tr>
		
		
		<tr>
			<td><spring:message code="administrator.ratioResearchesNotStarted" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getRatioResearchesNotStarted}" /></td>
		</tr>
		
		
		<tr>
			<td><spring:message code="administrator.ratioDeletedPosts" /></td>
			<td><fmt:formatNumber maxFractionDigits="2" value="${getRatioDeletedPosts}" /></td>
		</tr>
		
	</table>
	
	
<br>
<acme:h3 code="administrator.actorsWith10PercentMorePostsThanAverage"/>


<display:table name="findActorsWith10PercentMorePostsThanAverage" id="row" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">
	


	<!-- Action links -->
	
		<jstl:choose>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'APPRENTICE'}">
		<spring:message code="administrator.actor.rol.apprentice" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'MANAGER'}">
		<spring:message code="administrator.actor.rol.manager" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'EXPERT'}">
		<spring:message code="administrator.actor.rol.expert" var="rol"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.authorities[0].authority == 'INVESTOR'}">
		<spring:message code="administrator.actor.rol.investor" var="rol"/>
		</jstl:when>
		<jstl:otherwise>
		<spring:message code="administrator.actor.rol.administrator" var="rol"/>
		</jstl:otherwise>
		</jstl:choose>
		
	


	<!-- Attributes -->
	
	<spring:message code="administrator.actor.rol" var="rolHeader" />
	<display:column title="${rolHeader}" sortable="true">
	<jstl:out value="${rol}" />
	
	</display:column>
	
	<spring:message code="administrator.actor.username" var="usernameHeader" />
	<display:column title="${usernameHeader}" > 
	<jstl:out value="${row.userAccount.username}" />
	</display:column>

	<spring:message code="administrator.actor.name" var="nameHeader" />
	<display:column title="${nameHeader}" > 
	<jstl:out value="${row.name}" /> <jstl:out value="${row.surname}" /> 
	</display:column>
	
	
	<spring:message code="administrator.actor.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"
		sortable="true" />

		
		<display:column>
		

			<a href="actor/actor/redirect.do?actorId=${row.id}"  >
				<spring:message code="administrator.actor.show"  />
			</a>					
		</display:column>
	
	
	
</display:table>
	
	<br>
<acme:h3 code="administrator.avgMinMaxMarksForEvaluatedContests"/>
<jstl:set var="counter" value="0"/>
<display:table name="findEvaluatedContests" id="contest" requestURI="dashboard/administrator/display.do" pagesize="5" class="displaytag">



<spring:message code="administrator.contests.title" var="titleHeader"/>
<display:column sortable="true" title="${titleHeader}">
<a href="contest/actor/display.do?contestId=${contest.id}"><jstl:out value="${contest.title}"/></a>
</display:column>

<spring:message code="administrator.contests.marks.avg" var="avgMarkHeader"/>
<display:column sortable="true" title="${avgMarkHeader}">
<fmt:formatNumber maxFractionDigits="2" value="${getAvgMinMaxMarksForEvaluatedContests[0][counter]}" />
</display:column>

<spring:message code="administrator.contests.marks.min" var="minMarkHeader"/>
<display:column sortable="true" title="${minMarkHeader}">
<fmt:formatNumber maxFractionDigits="2" value="${getAvgMinMaxMarksForEvaluatedContests[1][counter]}" />
</display:column>

<spring:message code="administrator.contests.marks.max" var="maxMarkHeader"/>
<display:column sortable="true" title="${maxMarkHeader}">
<fmt:formatNumber maxFractionDigits="2" value="${getAvgMinMaxMarksForEvaluatedContests[2][counter]}" />
</display:column>

<jstl:set var="counter" value="${counter + 1}"/>

</display:table>
	
	


	
