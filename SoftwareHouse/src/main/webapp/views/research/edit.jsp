

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



<jstl:choose>
	<jstl:when test="${permission}">
	

		<form:form action="research/expert/edit.do" modelAttribute="research">


			<form:hidden path="id" />
			<form:hidden path="version" />




			<acme:textbox code="research.title" path="title" />
			<br />
			<br />

			<acme:textarea code="research.subject" path="subject" />
			<br />
			<br />

			<acme:textarea code="research.description" path="description" />
			<br />
			<br />

			<acme:textbox code="research.projectWebpage" path="projectWebpage" />
			<br />
			<br />
	
			<jstl:if test="${empty research.startDate}">
			<acme:textbox code="research.minCost.form" path="minCost"/>
			<br />
			<br />
			</jstl:if>


			<jstl:if test="${research.id == 0}">
			<acme:select items="${experts}" itemLabel="userAccount.username" code="research.team" path="team" multiple="true"/>
			<p><em><spring:message code="research.team.note" /> </em></p>
				<br />
				<br />
			</jstl:if>


			<jstl:choose>
			<jstl:when test="${research.id == 0}">
			<spring:message code="research.save.confirm" var="confirmSave"/>
			<input type="submit" onclick="confirmSubmit()" name="${save}" value="<spring:message code="research.save" />" />&nbsp;
			</jstl:when>
			<jstl:otherwise>
			<acme:submit name="${save}" code="research.save" />&nbsp;
			</jstl:otherwise>
			</jstl:choose>
	
			<acme:cancel url="${redirectURI}" code="research.cancel" />

		</form:form>


	</jstl:when>
	<jstl:otherwise>
		<acme:h3 code="research.noPermission.edit" />
	</jstl:otherwise>
</jstl:choose>


<script type="text/javascript">
    function confirmSubmit() {
        confirm("${confirmSave}");
    }
</script>

