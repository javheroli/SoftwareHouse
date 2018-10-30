<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="SoftwareHouse, Inc." height="180" width="750" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		
		<li><a href="forum/list.do"><spring:message
								code="master.page.forum" /></a> </li> 
		
		
		<security:authorize access="hasRole('ADMIN')">
	<li><a href="dashboard/administrator/display.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
				
					<li><a href="administrator/administrator/create.do"><spring:message
								code="master.page.userAccounts.administrator" /></a></li>
					<li><a href="manager/administrator/create.do"><spring:message
								code="master.page.userAccounts.manager" /></a></li>		
			<li><a href="expert/administrator/create.do"><spring:message
								code="master.page.userAccounts.expert" /></a></li>		
					
				</ul></li>
				
					<li><a class="fNiv"><spring:message	code="master.page.contests" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="contest/administrator/list.do"><spring:message code="master.page.contest.systemcontest" /></a></li>
					
				</ul>
				</li>
				
				<li><a href="discipline/administrator/list.do"><spring:message
								code="master.page.administrator.disciplines" /></a> </li> 
				<li><a href="research/administrator/list.do"><spring:message
								code="master.page.researches" /></a> </li> 
					<li><a href="actor/administrator/list.do"><spring:message
								code="master.page.users" /></a> </li> 
								
		</security:authorize>
		
		
		
		
		
		<security:authorize access="hasRole('APPRENTICE')">
			<li><a class="fNiv"><spring:message	code="master.page.contests" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="contest/actor/list.do"><spring:message code="master.page.contest.listPublished" /></a></li>
					
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.applications" /></a>
			<ul>
					<li class="arrow"></li>
					<li><a href="application/apprentice/list.do"><spring:message code="master.page.myapplications" /></a></li>
					
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.solutions" /></a>
			<ul>
					<li class="arrow"></li>
					<li><a href="solution/apprentice/list.do"><spring:message code="master.page.mysolutions" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('EXPERT')">
			<li><a class="fNiv"><spring:message	code="master.page.contests" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="contest/actor/list.do"><spring:message code="master.page.contest.listPublished" /></a></li>
					<li><a href="contest/expert/listEditor.do"><spring:message code="master.page.contest.listEditor" /></a></li>
					<li><a href="contest/expert/listJudge.do"><spring:message code="master.page.contest.listJudge" /></a></li>
					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.researches" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="research/expert/list.do"><spring:message code="master.page.researches.my" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('INVESTOR')">
			<li><a class="fNiv"><spring:message	code="master.page.researches" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="research/investor/listAll.do"><spring:message code="master.page.researches.all" /></a></li>
					<li><a href="research/investor/listForFunding.do"><spring:message code="master.page.researches.forFunding" /></a></li>
					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.investments" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="investment/investor/list.do"><spring:message code="master.page.investments.my" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message	code="master.page.contests" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="contest/actor/list.do"><spring:message code="master.page.contest.listPublished" /></a></li>
					<li><a href="contest/manager/list.do"><spring:message code="master.page.contest.listCreatedByManager" /></a></li>
					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.applications" /></a>
			<ul>
					<li class="arrow"></li>
					<li><a href="application/manager/list.do"><spring:message code="master.page.applicationsToMyContest" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
						<li><a class="fNiv"><spring:message code="master.page.actions" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="security/login.do"><spring:message
								code="master.page.login" /></a></li>
					<li><a href="apprentice/create.do"><spring:message
								code="master.page.register.apprentice" /></a></li>
					<li><a href="investor/create.do"><spring:message
								code="master.page.register.investor" /></a></li>
				</ul></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
		<security:authorize access="!hasRole('ADMIN')">
		<li><a href="actor/actor/list.do"><spring:message
								code="master.page.users" /></a> </li> 
		</security:authorize>
				<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/actor/display.do"><spring:message
								code="master.page.personalData" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

