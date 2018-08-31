<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Create User</title>
	
	<!-- Main style -->
	<link href="../css/user-form.css" rel="stylesheet">
	
	<!-- components styles -->
	<link href="../css/components/header.css" rel="stylesheet">
	<link href="../css/components/footer.css" rel="stylesheet">
	
	<!-- utilities style -->
	<link href="../css/util.css" rel="stylesheet">
	
</head>
<body>

	<!-- Header component -->
	<jsp:include page="../components/header.jsp" />

	<main>
			
		<c:if test="${pageLoadException != null}"> 	
			<%
				response.sendRedirect("http://localhost:8080/users?error=1");
			%>
		</c:if>

		<section class="content">
		
			<form:form class="content__form" action="/users/persistUser?id=${user.getId()}" method="POST" commandName="user">
				
				<div class="form__fields">
				
					<form:hidden name="id" path="id" />
				
					<div class="form__field">
					
						<label class="field__label">Username</label>

						<c:choose>
							<c:when test="${create == true}">
								<form:input path="username"/>
							</c:when>
							
							<c:when test="${update == true}">
								<form:hidden path="username" />
								<form:input path="username" disabled="true" />
							</c:when>
						</c:choose>
						
					</div>
				
					
					<div class="form__field">
						<label class="field__label">Age</label>
						<input type="number" name="age" min="10" max="110" value="${user.getAge()}" required>
					</div>
				
				</div>
				
				<h2 class="languages__caption">Languages</h2>
				
				<div class="form__list">
				
					<ul class="form__languages">
						<li class="form__language dummy__language">
							
							<div class="language__data">
								<input class="data__id" type="hidden" name="" value="">
								<span class="language__name"></span>
							</div>
							
							<div class="language__options">
								<span class="options__delete">Delete</span>
							</div>
						</li>
						
						
						<c:forEach var="userLanguage" items="${user.getLanguages()}" varStatus="loop">
						
							<li class="form__language">
									
								<div class="language__data">
									<input class="data__id" type="hidden" name="languages[${loop.index}].id" value="${userLanguage.getId()}">
									<span class="language__name"><c:out value="${userLanguage.getName()}" /></span>
								</div>
									
								<div class="language__options">
									<span data-languagename="${userLanguage.getName()}" data-languageid="${userLanguage.getId()}" class="options__delete">Delete</span>
								</div>
									
							</li>
						</c:forEach>
						
					</ul>
							
					<div class="add__container">
				
						<div class="add__language">
							<select class="add__select">
								<c:forEach var="language" items="${languages}">
									<option value="${language.getName()}" data-languageid="${language.getId()}">
										<c:out value="${language.getName()}" />
									</option>
								</c:forEach>
							</select>
						</div>
										
						<div>
							<input class="add__submit" type="button" value="Add">
						</div>
									
					</div>
				
				</div>
				
				<div class="form__submit">
				
					<c:choose>
						<c:when test="${create == true}">
							<input type="submit" value="Create">
						</c:when>
						
						<c:when test="${update == true}">
							<input type="submit" value="Update">
						</c:when>
					</c:choose>
					
					
					<c:if test="${createException != null}">
						<div class="error">
							<p>
								<c:out value="${createException}" />
							</p>
						</div>
					</c:if>
					
					<c:if test="${generalException != null}">
						<div class="error">
							<p>
								<c:out value="${generalException}" />
							</p>
						</div>
					</c:if>
	
				</div>
				
			</form:form>
			
			<div class="content__cancel">
				<a class="cancel__button" href="/users">Cancel</a>
			</div>
		
		</section>
	
	</main>
	
	<!-- Footer component -->
	<jsp:include page="../components/footer.jsp" />
	
	<!-- Scripts -->
	<script src="../js/user-form.js"></script>

</body>
</html>