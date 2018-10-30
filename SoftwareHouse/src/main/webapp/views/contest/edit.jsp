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


<jstl:set var="currentDate" value="<%= new Timestamp(System.currentTimeMillis()) %>" />

<jstl:if test="${permission }">


	<form:form  action="${requestURI }" modelAttribute="contest">
	
		<form:hidden path="id" />
		
		<form:hidden path="version" />
		
		
		
		<acme:textbox code="contest.title" path="title"/>
		<br>
		
		<acme:textarea code="contest.description" path="description"/>
		<br>
		
		
		<jstl:if test="${contest.endMoment.compareTo(contest.startMoment) <= 0 }">
			<span class="error">
			
			<spring:message code="contest.invalidStartAndEndMoment" />
			
			
			</span>
			<br>
			<br>
		</jstl:if>
		
		
		<acme:textboxMoment code="contest.startMoment" path="startMoment"/>
		<jstl:if test="${contest.startMoment.compareTo(currentDate) < 0 }">
			<span class="error"><spring:message code="contest.invalidmoment" /></span>
		</jstl:if>
		<br>
		<br>
		
		<acme:textboxMoment code="contest.endMoment" path="endMoment"/>
		<jstl:if test="${contest.endMoment.compareTo(currentDate) < 0 }">
			<span class="error"><spring:message code="contest.invalidmoment" /></span>
		</jstl:if>
		<br>
		<br>
		
		<form:label path="difficultyGrade">
			<spring:message code="contest.difficultyGrade" />:
		</form:label>
		<jstl:if test="${contest.id == 0 }">
			<jstl:set var="checked" value="checked"/>
		</jstl:if>
		<form:radiobutton path="difficultyGrade" value="1"/>1
		<form:radiobutton path="difficultyGrade" value="2" />2
		<form:radiobutton path="difficultyGrade" value="3" checked="${checked }"/>3
		<form:radiobutton path="difficultyGrade" value="4" />4
		<form:radiobutton path="difficultyGrade" value="5"/>5
		<br>
		<br>
		
		<span><strong><spring:message code="contest.placesGreaterThanZero" />:</strong></span>
		<br>
		<br>
		<acme:textbox code="contest.availablePlaces" path="availablePlaces"/>
		<br>
		
		<acme:textbox code="contest.requiredPoints" path="requiredPoints"/>
		<br>
		
		<acme:textbox code="contest.price" path="price" placeholder="29.99"/>
		<br>
		
		<acme:textbox code="contest.prize" path="prize" placeholder="1499.99"/>
		<br>
		
		
		
		<fieldset style="width: 40%;">
	
		
		<legend> <spring:message code="contest.rules"/> : </legend>
	
	    <div id="list">
	    <jstl:choose> 
		<jstl:when test="${empty contest.rules}">
		<div class="list-item">
		 <form:input path="rules[0]"/>
		 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="contest.rules.remove"/> </a>
		 </div>
		 <br /> 
		</jstl:when>
		<jstl:otherwise>
		<jstl:forEach items="${contest.rules}" var="r" varStatus="i" begin="0">
	    <div class="list-item">
	      <form:input path="rules[${i.index}]"/>
	      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="contest.rules.remove"/> </a>
		    </div>
	            <br />
	        </jstl:forEach>
		</jstl:otherwise>
		</jstl:choose>
	     
	    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="contest.rules.add"/> </a>&nbsp;&nbsp;
	    </div>
	    
		
		<form:errors cssClass="error" path="rules" />
	
		</fieldset>
		<br>
		<br>
		
		<jstl:if test="${contest.id==0 }">
		
			<acme:select id="discipline" items="${disciplines }" itemLabel="name" code="contest.discipline" path="discipline" multiple="false" />
			<br>
			
			<acme:h3 code="contest.ajax"/>
			
			<acme:select id="editors" items="${editors }" itemLabel="userAccount.username" code="contest.editor" path="editor" multiple="false"/>
			<br>
			
			<acme:select id="judges"  items="${judges }" itemLabel="userAccount.username" code="contest.judges" path="judges" multiple="true" />
			<br>
		
		</jstl:if>
		
		<jstl:if test="${contest.id!=0 }">
			<fieldset style="width:20%;">
				<legend><spring:message code="contest.choices" />:</legend>
				<br>
				<span><strong><spring:message code="contest.discipline" />:  </strong></span>
				<jstl:out value="${contest.discipline.name }"></jstl:out>
				<br>
				<br>
				
				<span><strong><spring:message code="contest.editor" />:  </strong></span>
				<jstl:out value="${contest.editor.name} "></jstl:out>
				<jstl:out value="${contest.editor.surname} "></jstl:out>
				<jstl:out value="(${contest.editor.userAccount.username})"></jstl:out>
				<br>
				<br>
				
				<span><strong><spring:message code="contest.judges" />:  </strong></span>
				<ul>
						<jstl:forEach items="${contest.judges }" var="judge">
							<li>
								<jstl:out value="${judge.name} "></jstl:out>
								<jstl:out value="${judge.surname} "></jstl:out>
								<jstl:out value="(${judge.userAccount.username})"></jstl:out>
							</li>
						</jstl:forEach>
					</ul>
				
				
			</fieldset>
			<br>
			<br>
			
			<jstl:if test="${empty contest.problems }">
				<span><strong><spring:message code="contest.emptyProblems" /></strong> </span>
				<br>
				<br>
				
			</jstl:if>
			
			<jstl:set value="0" var="sumMarks"></jstl:set>
			<jstl:forEach items="${contest.problems }" var="problem">
				<jstl:set value="${sumMarks + problem.mark }" var="sumMarks"></jstl:set>
			</jstl:forEach>
			
			<jstl:if test="${not empty contest.problems && sumMarks != 10.00 }">
				<span><strong><spring:message code="contest.untilGoodMarks" /></strong> </span>
				<br>
				<br>
			</jstl:if>
		</jstl:if>
		
		
		<acme:submit name="saveDraft" code="contest.saveDraft"/>&nbsp;
		
		<jstl:if test="${not empty contest.problems && sumMarks == 10.00 }">
			<acme:submit name="saveFinal" code="contest.saveFinal"/>&nbsp;
		</jstl:if> 
		
		<jstl:if test="${contest.id != 0 }">
			<acme:delete code="contest.delete" codeConfirm="contest.deleteConfirm"/>&nbsp;
		</jstl:if>
		
		<jstl:if test="${contest.id == 0 }">
			<acme:cancel url="/contest/manager/list.do" code="contest.cancel"/>	
		</jstl:if>
		
		<jstl:if test="${contest.id != 0 }">
			<acme:cancel url="/contest/actor/display.do?contestId=${contest.id }" code="contest.cancel"/>	
		</jstl:if>
		
	</form:form>
</jstl:if>

<jstl:if test="${!permission }">
	<acme:h3 code="contest.nopermission"/>
</jstl:if>

<script>
	    $(document).ready(function() {
	        $("#list").dynamiclist();
	    });
	    
	    
	    $(document).ready(function() {
	       $("#discipline").on("input", function(){
	    	   $.get("expert/manager/list.do", {disciplineId: $('#discipline').val()}, function(data){
	    			$("#editors").empty();
	    		  	$("#judges").empty();
	    		  	
	    		  	var o1 = new Option("----", "0");
	    		  	$(o1).html("----");
	    		  	$("#editors").append(o1);
	    		  	
	    		  	var splitted = data.split(";");
	    		  	var splittedLength = splitted.length;
	    		  	var i;
	    		  	for(i = 0; i < splittedLength-1; i++) {
	    		  		var splitted2 = splitted[i].split(",");
	    		  		var o1 = new Option(splitted2[1], splitted2[0]);
	    		  		var o2 = new Option(splitted2[1], splitted2[0]);
	    		  		$(o1).html(splitted2[1]);
	    		  		$(o2).html(splitted2[1]);
	    		  		$("#editors").append(o1);
	    		  		$("#judges").append(o2);
	    		  	}
	    	   });
	       });
	    });
	    
	    $(document).ready(function() {
	    	if($("#discipline").val() != null){
	    		$.get("expert/manager/list.do", {disciplineId: $('#discipline').val()}, function(data){
	    			$("#editors").empty();
	    		  	$("#judges").empty();
	    		  	
	    		  	var o1 = new Option("----", "0");
	    		  	$(o1).html("----");
	    		  	$("#editors").append(o1);
	    		  	
	    		  	var splitted = data.split(";");
	    		  	var splittedLength = splitted.length;
	    		  	var i;
	    		  	for(i = 0; i < splittedLength-1; i++) {
	    		  		var splitted2 = splitted[i].split(",");
	    		  		var o1 = new Option(splitted2[1], splitted2[0]);
	    		  		var o2 = new Option(splitted2[1], splitted2[0]);
	    		  		$(o1).html(splitted2[1]);
	    		  		$(o2).html(splitted2[1]);
	    		  		$("#editors").append(o1);
	    		  		$("#judges").append(o2);
	    		  	}
	    	   });
	    	}
	    });
	    
	    
</script>



