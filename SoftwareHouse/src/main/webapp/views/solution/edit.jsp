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
	<security:authorize access="hasRole('APPRENTICE')">
		<h3 style="display:inline-block;"><spring:message code="solution.timeleft" />: &nbsp;&nbsp;</h3>
		<h3 style="display:inline-block;" id="demo"></h3>
		<div id="progressBar">
	  		<div class="bar"></div>
		</div>
	</security:authorize>


	<form:form  action="${requestURI }" modelAttribute="solution">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		<jstl:if test="${solution.id == 0 }">
			<form:hidden path="application" />
		</jstl:if>
		
		
		<table class="displayStyle">
			<spring:message code="solution.exam" var="exam" />
				<tr>
					<th colspan="2"><jstl:out value="${exam}" /><jstl:out value=" ${solution.application.contest.title}" /></th>
				</tr>
		<jstl:forEach items="${solution.application.contest.problems }" var="problem">
			<tr>
				<spring:message code="solution.problem" var="p" />
				<td>
					<strong><jstl:out value="${p }" /> <jstl:out value="${problem.number }" /></strong>
					<br>
					<strong><jstl:out value="(${problem.mark }" /> <spring:message code="solution.points"  />)</strong>
				</td>
				<td>
					<p style="margin-left:30px;"><jstl:out value="${problem.statement }" /></p>
					<br>
					<jstl:if test="${problem.linkPicture != null && !problem.linkPicture.equals('') }">
					<img style=" margin-left:30px; max-width:800px; max-height:800px;" src="${problem.linkPicture }"  alt="photo" />
					<br>
					<br>
					</jstl:if>
					
				</td>
			</tr>
			
			<tr>
				<spring:message code="solution.answer" var="a" />
				<td>
					<strong><jstl:out value="${a }" /> <jstl:out value="${problem.number }" /></strong>
					<br>
				</td>
				
				<td>
					<br>
					<security:authorize access="hasRole('APPRENTICE')">
						<form:textarea class="textareaExam" path="answers[${problem.number - 1 }].text"/>
						<br>
						<br>
					</security:authorize>
					
					<security:authorize access="hasRole('EXPERT')">
						<form:textarea disabled="true" class="textareaExam" path="answers[${problem.number - 1 }].text"/>
						<br>
						<br>
						<acme:textbox divinline="true" code="solution.mark" path="answers[${problem.number - 1 }].mark"/>
						<jstl:if test="${solution.answers[problem.number - 1].mark > problem.mark }">
							<span  class="error"><spring:message code="solution.badmark" /><jstl:out value="${problem.mark }" /></span>
							<br>
						</jstl:if>
						<jstl:if test="${solution.answers[problem.number - 1].mark > 10 || solution.answers[problem.number - 1].mark < 0 }">
							<span class="error"><spring:message code="solution.outOfRange" /></span>
							<br>
						</jstl:if>
						<br>
						<br>
					</security:authorize>
				</td>
			</tr>
			
		</jstl:forEach>
		
		</table>
		
		<security:authorize access="hasRole('APPRENTICE')">
		<acme:submit name="send" code="solution.send"/>&nbsp;
		
		
			<acme:cancel url="contest/actor/display.do?contestId=${solution.application.contest.id }" code="solution.cancel"/>
		</security:authorize>
		
		<security:authorize access="hasRole('EXPERT')">
			<acme:submit name="assess" code="solution.assessSolution"/>&nbsp;
			
			<acme:cancel url="solution/expert/list.do?contestId=${solution.application.contest.id }" code="solution.cancel"/>
		</security:authorize>

		
	</form:form>
</jstl:if>

<security:authorize access="hasRole('EXPERT')">
<jstl:if test="${!permission }">
	<acme:h3 code="solution.nopermissionApprentice"/>
</jstl:if>
</security:authorize>

<security:authorize access="hasRole('APPRENTICE')">
<jstl:if test="${!permission }">
	<acme:h3 code="solution.nopermissionApprentice"/>
</jstl:if>
</security:authorize>






<script>
// Set the date we're counting down to
var endMoment = "${solution.application.contest.endMoment}";
var countDownDate = new Date(endMoment);

// Update the count down every 1 second
var x = setInterval(function() {

    // Get todays date and time
    var now = new Date().getTime();
    
    // Find the distance between now an the count down date
    var distance = countDownDate - now;
    
    // Time calculations for days, hours, minutes and seconds
    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
    
    // Output the result in an element with id="demo"
    document.getElementById("demo").innerHTML = days + "d " + hours + "h "
    + minutes + "m " + seconds + "s ";
    
    // If the count down is over, write some text 
    if (distance < 0) {
        clearInterval(x);
        document.getElementById("demo").innerHTML = "EXPIRED";
    }
}, 1000);
</script>

<script>	
	function progress(timeleft, timetotal, $element) {
	    var progressBarWidth = timeleft * $element.width() / timetotal;
	    $element.find('div').animate({ width: progressBarWidth }, 500);
	    if(timeleft > 0) {
	        setTimeout(function() {
	            progress(timeleft - 1, timetotal, $element);
	        }, 1000);
	    }
	};
	
	var now = new Date();
	var finish = new Date("${solution.application.contest.endMoment}");
	
 	var diferenciaEnMilisengundos = finish.getTime()- now.getTime();
 	var diferenciaEnSegundos = diferenciaEnMilisengundos/1000;
 	var result = Math.floor(diferenciaEnSegundos);
 	
	progress(result, result, $('#progressBar'));
</script>

