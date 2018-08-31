<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>

	<meta charset="ISO-8859-1">
	<title>Users</title>

	<!-- main style -->
	<link href="../css/index.css" rel="stylesheet">
	
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
	
		<section class="content">
		
			<div class="content__table">
			
			<!-- //////////////// ERROR MESSAGES /////////////////////////// -->
			
			<% if (request.getParameter("error") != null) { %>
				<div class="error">
					<p>
						Unexpected error. try again later.
					</p>
				</div>
			<% } %>
			
			<!-- ////////////////////////////////////////////////////////// -->
		
				<div class="table__header">
					<div class="table__row">
						<div class="row__data">
							<div class="row__celd"><span class="celd__header">Username</span></div>
							<div class="row__celd"><span class="celd__header">Age</span></div>
						</div>
					</div>
				</div>
			
				<div class="table__body">
					<c:forEach var="user" items="${users}">
						<div class="table__row">
							<div class="row__data">
								<div class="row__celd">
									<span> <c:out value="${user.getUsername()}" /> </span>
								</div>
								<div class="row__celd">
									<span> <c:out value="${user.getAge()}" /> </span>
								</div>
							</div>
							
							<div class="row__options">
								<div class="row__celd">
									<a class="celd__button" href="/users/update?id=${user.getId()}">Update</a>
								</div>
								
								<div class="row__celd">
									<form action="/users/deleteUserById/${user.getId()}" method="POST">
										<input class="celd__button" type="submit" value="Delete" />
									</form>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>	
				
				<div class="table__footer">
						
						<div class="row__options">
							<a href="/users/newUser" class="options__create">Create User</a>
						</div>
					
				</div>
			
			</div>		

		</section>
	
	</main>
	
	<!-- Footer component -->
	<jsp:include page="../components/footer.jsp" />

</body>

</html>