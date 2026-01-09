<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login | <%=application.getAttribute("appName")%></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<%@include file="/includes/head.jsp"%>
<style>
.login-card {
	animation: slideIn 0.5s ease-out;
}
</style>
</head>
<body>

<%@include file="includes/header.jsp" %>

<div class="login-container">
	<div class="login-card shadow">
		<div class="text-center mb-4">
			<i class="bi bi-person-circle" style="font-size: 4rem; opacity: 0.8;"></i>
		</div>
		<h3 class="text-center mb-4">Welcome Back</h3>
		<p class="text-center text-muted mb-4">Sign in to continue to JobOrbit</p>

		<% String error = (String) request.getAttribute("error"); %>
		<% if (error != null) { %>
			<div class="alert alert-danger text-center py-1"><%= error %></div>
		<% } %>

		<% if ("true".equals(request.getParameter("registered"))) { %>
			<div class="alert alert-success text-center py-1">✅ Registration successful. Please login.</div>
		<% } %>

		<form action="LoginServlet" method="post">
			<div class="mb-3">
				<label class="form-label"><i class="bi bi-envelope"></i> Email</label>
				<input type="email" name="email" class="form-control"
					placeholder="Enter your email" required />
			</div>
			<div class="mb-3">
				<label class="form-label"><i class="bi bi-lock"></i> Password</label>
				<input type="password" name="password" class="form-control"
					placeholder="Enter your password" required />
			</div>
			<button type="submit" class="btn btn-login mt-2 w-100">
				<i class="bi bi-box-arrow-in-right"></i> Login
			</button>
		</form>

		<div class="text-center mt-3">
			<small>Don't have an account? <a href="register.jsp"
				class="text-warning">Register</a></small>
		</div>
	</div>
</div>

<%@include file="includes/footer.jsp" %>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>