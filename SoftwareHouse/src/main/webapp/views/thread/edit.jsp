

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authentication property="principal.username" var="principalUsername" /> 



<jstl:choose>
	<jstl:when
		test="${thread.writer.userAccount.username eq principalUsername}">


		<form:form action="thread/actor/edit.do" modelAttribute="thread">


			<form:hidden path="id" />
			<form:hidden path="version" />

		
		
			
			
			<acme:textbox code="thread.title" path="title"/>
			<br />
			<br />
			
			<acme:textarea code="thread.text" path="text" />
			
			<br />
			<br />
			
			
			<jstl:if test="${thread.id == 0}">
			<form:hidden path="forum" />
			<acme:select multiple="true" items="${disciplines}"  itemLabel="name" code="thread.disciplines" path="disciplines"/>
			<br />
			<br />
			
			</jstl:if>
			
			

			
			<fieldset>

	
	<legend> <form:label path="linksAttachments"> <spring:message code="thread.linksAttachments"/> :  </form:label> </legend>
	
    <div id="list">
    <jstl:choose> 
	<jstl:when test="${empty thread.linksAttachments}">
	<div class="list-item">
	 <form:input path="linksAttachments[0]"/>
	 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="thread.linksAttachments.remove"/> </a>
	 </div>
	 <br /> 
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${thread.linksAttachments}" var="link" varStatus="i" begin="0">
    <div class="list-item">
      <form:input path="linksAttachments[${i.index}]"/>
      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="thread.linksAttachments.remove"/> </a>
	    </div>
            <br />
        </jstl:forEach>
	</jstl:otherwise>
	</jstl:choose>
     
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="thread.linksAttachments.add"/> </a>&nbsp;&nbsp;
    </div>
    
	
	<form:errors cssClass="error" path="linksAttachments" />

	</fieldset>
	
			<br />
			<br />
			
			

			<acme:submit name="save" code="thread.save" />&nbsp;
	
	
			<acme:cancel url="forum/display.do?forumId=${thread.forum.id}" code="thread.cancel" />
			

		</form:form>


	</jstl:when>
	<jstl:otherwise>
		<acme:h3 code="thread.noPermission.edit" />
	</jstl:otherwise>
</jstl:choose>

<script>
    $(document).ready(function() {
        $("#list").dynamiclist();
    });
</script>

