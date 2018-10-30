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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ page import="java.sql.*"%>


	




<jstl:if test="${permission }">
	<jstl:set var="currentMoment" value="<%= new Timestamp(System.currentTimeMillis()) %>" />
	
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${contest.isDraft && isOwner }">
		<acme:button url="contest/manager/edit.do?contestId=${contest.id }" code="contest.edit"/>
	</jstl:if>
	</security:authorize>
	
	
	<security:authorize access="hasRole('APPRENTICE')">
	<jstl:if test="${!hasApplication &&  !contest.isDraft && contest.startMoment.compareTo(currentMoment) > 0 && contest.availablePlaces > 0 }">
		<acme:button url="application/apprentice/create.do?contestId=${contest.id }" code="contest.createApplication"/>
	</jstl:if>
	
	
	<jstl:if test="${hasApplication }">
		
		<jstl:if test="${solicitud.status.equals('REJECTED') }">
			<img class ="rejectedImage" src="images/rejected.jpg" alt="REJECTED" />
			<br>
		</jstl:if>
		<jstl:if test="${solicitud.status.equals('PENDING') }">
			<img class="pendingImage" src="images/pending.jpg" alt="PENDING" />
			<br>
		</jstl:if>
		
		<jstl:if test="${solicitud.status.equals('DUE') }">
			<img class ="dueImage" src="images/due.png" alt="DUE"/>
			<br>
		</jstl:if>
		
		<jstl:if test="${solicitud.status.equals('ACCEPTED') }">
			<img class="acceptedImage" src="images/accepted.jpg" alt="ACCEPTED" />
			<br>
		</jstl:if>
		
		<h3 style="display: inline-block;"><spring:message code="contest.applicationWithStatus"  />  </h3>
		
		
		
		<jstl:if test="${solicitud.status.equals('ACCEPTED') && contest.startMoment.compareTo(currentMoment) <= 0 && contest.endMoment.compareTo(currentMoment) >= 0 }">
			<jstl:if test="${solution == null }">
				<acme:button url="solution/apprentice/create.do?applicationId=${solicitud.id }" code="contest.createSolution"/>
			</jstl:if>
			
			<jstl:if test="${solution != null }">
				<acme:button url="solution/apprentice/edit.do?solutionId=${solution.id }" code="contest.editSolution"/>
			</jstl:if>
		</jstl:if>
		
	</jstl:if>
	
	
	</security:authorize>
	

		<table class="displayStyle">

			<spring:message code="contest.displayContest" var="display" />
			<tr>
				<th colspan="2"><jstl:out value="${display}" /></th>
			</tr>
			
			<jstl:if test="${empty winners}">
				<jstl:set value="#ff6666" var="color"></jstl:set>
			</jstl:if>
			<jstl:if test="${not empty winners}">
				<jstl:set value="#80ff80" var="color"></jstl:set>
			</jstl:if>
			
			<jstl:if test="${!contest.isDraft && contest.endMoment.compareTo(currentMoment) < 0}">
				<tr style="background-color: ${color};">
					<td><strong><spring:message code="contest.winner" />: </strong></td>
					<td>
						<jstl:if test="${empty winners}">
							<spring:message code="contest.noWinnerYet" />
						</jstl:if>
						<jstl:if test="${not empty winners }">
							<jstl:forEach items="${winners }" var="winner" varStatus="i">
								<a href="apprentice/actor/display.do?apprenticeId=${winner.id }" >
									<jstl:out value="${winner.name } "></jstl:out>
									<jstl:out value="${winner.surname } "></jstl:out>
									<jstl:out value="(${winner.userAccount.username })"></jstl:out>
								</a>
								&nbsp;
								<jstl:if test="${winnersSize > 1 && i.index < winnersSize - 1}">
									<jstl:out value="& "></jstl:out>
								</jstl:if>
							</jstl:forEach>
							<spring:message code="contest.congratulations" />
							
						</jstl:if>
					</td>
				</tr>
			</jstl:if>
			
			<tr>
				<td><strong><spring:message code="contest.status" />: </strong></td>
				<td>
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
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.title" />: </strong></td>
				<td><jstl:out value="${contest.title }" /></td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.description" />: </strong></td>
				<td><jstl:out value="${contest.description }" /></td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.startMoment" />: </strong></td>
				<td>
					<spring:message code="contest.startMoment.pattern" var="patternMoment" />
					<fmt:formatDate value="${contest.startMoment}" pattern="${patternMoment}" />
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.endMoment" />: </strong></td>
				<td>
					<spring:message code="contest.startMoment.pattern" var="patternMoment" />
					<fmt:formatDate value="${contest.endMoment}" pattern="${patternMoment}" />
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.duration" />: </strong></td>
				<td>
					<jstl:out value="${contest.duration } "></jstl:out><spring:message code="contest.minutes" />
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.difficultyGrade" />: </strong></td>
				<td>
					<jstl:out value="${contest.difficultyGrade } " />
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.requiredPoints" />: </strong></td>
				<td>
					<jstl:out value="${contest.requiredPoints }   " />
					<spring:message code="contest.freeApplication" />
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.availablePlaces" />: </strong></td>
				<td>
					<jstl:out value="${contest.availablePlaces } " />
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.price" />: </strong></td>
				<td>
					<jstl:out value="${contest.price } "></jstl:out>&euro;
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.prize" />: </strong></td>
				<td>
					<jstl:out value="${contest.prize } "></jstl:out>&euro;
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.discipline" />: </strong></td>
				<td>
					<jstl:out value="${contest.discipline.name }"></jstl:out>
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.rules" />: </strong></td>
				<td>
					<jstl:if test="${not empty contest.rules }">
						<ul>
							<jstl:forEach items="${contest.rules }" var="rule">
								<li><jstl:out value="${rule }" /></li>
							</jstl:forEach>
						</ul>
					</jstl:if>
					<jstl:if test="${ empty contest.rules }">
						<spring:message code="contest.noRules" var="noRules"/>
						<jstl:out value="${noRules }"></jstl:out>
					</jstl:if>
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.manager" />: </strong></td>
				<td>
					<a href="manager/actor/display.do?managerId=${contest.manager.id }">						<jstl:out value="${contest.manager.name} "></jstl:out>
					<jstl:out value="${contest.manager.surname} "></jstl:out>
					<jstl:out value="(${contest.manager.userAccount.username})"></jstl:out>
					</a>
				</td>
			</tr>
				
			
			<security:authorize access="hasRole('EXPERT')||hasRole('MANAGER') ||hasRole('ADMIN')">
			<tr>
				<td><strong><spring:message code="contest.editor" />: </strong></td>
				<td>
					<a href="expert/actor/display.do?expertId=${contest.editor.id }">
					<jstl:out value="${contest.editor.name} "></jstl:out>
					<jstl:out value="${contest.editor.surname} "></jstl:out>
					<jstl:out value="(${contest.editor.userAccount.username})"></jstl:out>
					</a>
				</td>
			</tr>
			</security:authorize>
			
			<tr>
				<td><strong><spring:message code="contest.judges" />: </strong></td>
				<td>
					<ul>
						<jstl:forEach items="${contest.judges }" var="judge">
							<li>
								<a href="expert/actor/display.do?expertId=${judge.id }">
								<jstl:out value="${judge.name} "></jstl:out>
								<jstl:out value="${judge.surname} "></jstl:out>
								<jstl:out value="(${judge.userAccount.username})"></jstl:out>
								</a>
							</li>
						</jstl:forEach>
					</ul>
				</td>
			</tr>
			
			<tr>
				<td><strong><spring:message code="contest.participants" />: </strong></td>
				<td>
					<ul>
						<jstl:forEach items="${contest.applications }" var="application">
							<jstl:if test="${application.status.equals('ACCEPTED') }">
							<li>
								<a href="apprentice/actor/display.do?apprenticeId=${application.applicant.id }">
								<jstl:out value="${application.applicant.name} "></jstl:out>
								<jstl:out value="${application.applicant.surname} "></jstl:out>
								<jstl:out value="(${application.applicant.userAccount.username})"></jstl:out>
								</a>
							</li>
							</jstl:if>
						</jstl:forEach>
					</ul>
				</td>
			</tr>
			
			<jstl:if test="${isEditor || isOwner || isAdmin}">
				<tr>
					<td><strong><spring:message code="contest.problems" />: </strong></td>
					<td>
						<jstl:set value="0" var="sumMarks"></jstl:set>
						<display:table name="${contest.problems}" id="problem"
							requestURI="contest/actor/display.do" pagesize="5" class="displaytag">
							
							<spring:message code="contest.number" var="numberHeader" />
							<spring:message code="contest.problem" var="pr" />
							<display:column title="${numberHeader }">
								<jstl:out value="${pr } ${problem.number }"/>
							</display:column>
							
							<spring:message code="contest.statement" var="statementHeader" />
							<display:column title="${statementHeader}" property="statement" sortable="false" />
							
							<spring:message code="contest.linkPicture" var="linkPictureHeader" />
							<display:column title="${linkPictureHeader}"  sortable="false" >
								<jstl:if test="${problem.linkPicture != null }">
									<a href="${problem.linkPicture }">${problem.linkPicture }</a>
								</jstl:if>
								
								<jstl:if test="${problem.linkPicture == null || problem.linkPicture.equals('') }">
									<spring:message code="contest.noLinkPicture" var="noLinkPicture" />
									<jstl:out value="${noLinkPicture }"></jstl:out>
								</jstl:if>
							</display:column>
							
							<spring:message code="contest.mark" var="markHeader" />
							<display:column title="${markHeader}" property="mark" sortable="false" />
							
							<jstl:if test="${contest.isDraft && isEditor}">
								<display:column>
									<acme:button  url="problem/expert/edit.do?problemId=${problem.id }" code="contest.problem.edit"/>
								</display:column>
							</jstl:if>
							
							<jstl:set value="${sumMarks + problem.mark }" var="sumMarks"></jstl:set>
							
						</display:table>
						<jstl:if test="${contest.isDraft}">
							<jstl:if test="${ empty contest.problems }"><br></jstl:if>
							<jstl:if test="${ not empty contest.problems && sumMarks != 10.00 }">
								<span class="error"><spring:message code="contest.badMarks"  /><jstl:out value=" ${sumMarks }" /></span>
								<br>
								<br>
							</jstl:if>
							
							<jstl:if test="${ not empty contest.problems && sumMarks == 10.00 }">
								<span class="good"><spring:message code="contest.goodMarks"  /></span>
								<br>
								<br>
							</jstl:if>
							<jstl:if test="${isEditor }">
								<a href="problem/expert/create.do?contestId=${contest.id }"><spring:message code="contest.createProblem" /></a>
							</jstl:if>
						</jstl:if>
					</td>
				</tr>
			</jstl:if>
			

		</table>
		
</jstl:if>


<jstl:if test="${!permission }">
	
	<acme:h3 code="contest.nopermissionDisplay" />
</jstl:if>




