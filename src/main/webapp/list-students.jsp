<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

	<div id="wrapper">
		<div id="header">
			<h2>Tik School</h2>
		</div>
	</div>

	<div id="container">

		<div id="content">


			<input type="button" value="Add Student"
				onclick="window.location.href= 'add-student-form.jsp'"
				class="add-student-button" />

			<table>

				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>

				<c:forEach var="tempStudent" items="${students_list }">

					<!-- setup a single link for each user in the parameter-->

					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD"></c:param>
						<c:param name="studentId" value="${tempStudent.id }"></c:param>
					</c:url>
					<!--  set up a link to delete a student -->
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${tempStudent.id}" />
					</c:url>
					
					
					<tr>
						<td>${tempStudent.firstName }</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td><a href="${tempLink}">Update</a> | <a
							href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">
								Delete</a></td>
					</tr>
				</c:forEach>

			</table>

		</div>

	</div>
</body>
</html>