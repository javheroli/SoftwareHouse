

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
<jstl:when test="${post.id == 0}">
<jstl:set value="${(empty post.parentPost) || (not post.parentPost.isDeleted)}" var="permission"/>
</jstl:when>
<jstl:otherwise>
<jstl:set value="${((empty post.parentPost) || (not post.parentPost.isDeleted)) && principalUsername eq post.writer.userAccount.username}" var="permission"/>
</jstl:otherwise>
</jstl:choose>


<jstl:choose>
	<jstl:when
		test="${permission && not post.isDeleted}">


		<form:form action="post/actor/edit.do" modelAttribute="post">


			<form:hidden path="id" />
			<form:hidden path="version" />

		
		
			<jstl:if test="${post.id == 0}">
			
			<form:hidden path="topic" />
			
			<form:hidden path="parentPost" />
			
			<form:hidden path="thread" />
			</jstl:if>
			
		<jstl:choose>
			<jstl:when test="${not empty post.thread}">
			<textarea disabled rows="8" cols="80"> <jstl:out value="${post.thread.text}"/></textarea>
			</jstl:when>
			<jstl:otherwise>
			<textarea disabled rows="8" cols="80"> <jstl:out value="${post.parentPost.text}"/></textarea>
			</jstl:otherwise>
			</jstl:choose>
			<br />
			<br />
			
			<acme:textarea code="post.text" path="text" />
			
		
			<br />
			<br />
					
			<fieldset>

	
	<legend> <form:label path="linksAttachments"> <spring:message code="post.linksAttachments"/> :  </form:label> </legend>
	
    <div id="list">
    <jstl:choose> 
	<jstl:when test="${empty post.linksAttachments}">
	<div class="list-item">
	 <form:input path="linksAttachments[0]"/>
	 <a href="#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="post.linksAttachments.remove"/> </a>
	 </div>
	 <br /> 
	</jstl:when>
	<jstl:otherwise>
	<jstl:forEach items="${post.linksAttachments}" var="link" varStatus="i" begin="0">
    <div class="list-item">
      <form:input path="linksAttachments[${i.index}]"/>
      <a href= "#" class="list-remove" onclick="event.preventDefault();"> <spring:message code="post.linksAttachments.remove"/> </a>
	    </div>
            <br />
        </jstl:forEach>
	</jstl:otherwise>
	</jstl:choose>
     
    <a href="#" class="list-add" onclick="event.preventDefault();"> <spring:message code="post.linksAttachments.add"/> </a>&nbsp;&nbsp;
    </div>
    
	
	<form:errors cssClass="error" path="linksAttachments" />

	</fieldset>
	
			<br />
			<br />
			
			

			<acme:submit name="save" code="post.save" />&nbsp;
	<jstl:if test="${post.id != 0}">
				<acme:delete code="post.delete"
					codeConfirm="post.confirm.delete" />&nbsp;
	</jstl:if>
			<acme:cancel url="post/list.do?threadId=${post.topic.id}" code="post.cancel" />
			

		</form:form>


	</jstl:when>
	<jstl:otherwise>
		<acme:h3 code="post.noPermission.edit" />
	</jstl:otherwise>
</jstl:choose>

<script>
    $(document).ready(function() {
        $("#list").dynamiclist();
    });
</script>

