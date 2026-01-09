<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=application.getAttribute("appName")%> - Smart Job
	Portal</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">
<%@include file="/includes/head.jsp"%>

</head>
<body>

	<!-- Navbar -->
	<%@include file="/includes/header.jsp"%>

	<main>
		<!-- Hero Section -->
		<div class="hero">
			<h1>Get Hired Smarter with AI</h1>
			<p>AI-powered resume analysis and smart job matching in one
				platform.</p>
			<a href="register.jsp" class="btn btn-cta mt-4">Get Started</a>
		</div>

		<!-- Features Section -->
		<div class="container py-5">
			<div class="row text-center g-4">
				<div class="col-md-4 mb-4">
					<div class="feature-card h-100">
						<div class="mb-3" style="font-size: 3rem;">🧠</div>
						<h4>AI Resume Insights</h4>
						<p>Let our AI analyze your resume and extract deep insights
							like skills, experience, and summary.</p>
					</div>
				</div>

				<div class="col-md-4 mb-4">
					<div class="feature-card h-100">
						<div class="mb-3" style="font-size: 3rem;">🛠️</div>
						<h4>Skill Gap Analyzer</h4>
						<p>Identify missing skills by comparing your resume with job
							requirements — instantly.</p>
					</div>
				</div>

				<div class="col-md-4 mb-4">
					<div class="feature-card h-100">
						<div class="mb-3" style="font-size: 3rem;">🎯</div>
						<h4>Smart Job Matching</h4>
						<p>Get job suggestions that best match your resume, skills,
							and goals — powered by intelligent AI.</p>
					</div>
				</div>

				<div class="col-md-4 mb-4">
					<div class="feature-card h-100">
						<div class="mb-3" style="font-size: 3rem;">📝</div>
						<h4>Skill Assessments</h4>
						<p>Take comprehensive skill assessment quizzes to showcase your expertise and get matched with relevant jobs.</p>
					</div>
				</div>

				<div class="col-md-4 mb-4">
					<div class="feature-card h-100">
						<div class="mb-3" style="font-size: 3rem;">⭐</div>
						<h4>Job Bookmarks</h4>
						<p>Save your favorite job listings for later and never miss an opportunity that interests you.</p>
					</div>
				</div>

				<div class="col-md-4 mb-4">
					<div class="feature-card h-100">
						<div class="mb-3" style="font-size: 3rem;">📊</div>
						<h4>Analytics Dashboard</h4>
						<p>Track your applications, view match scores, and get insights into your job search progress.</p>
					</div>
				</div>
			</div>
		</div>

		<!-- CTA Section -->
		<div class="container py-5">
			<div class="row">
				<div class="col-md-8 mx-auto text-center">
					<div class="feature-card">
						<h3 class="mb-3">Ready to Find Your Dream Job?</h3>
						<p class="mb-4">Join thousands of job seekers who have found their perfect match</p>
						<div class="d-flex gap-3 justify-content-center flex-wrap">
							<a href="register.jsp" class="btn btn-cta">Get Started as Job Seeker</a>
							<a href="register.jsp" class="btn btn-cta" style="background: rgba(255,255,255,0.2); border: 2px solid white;">Post Jobs as Employer</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<!-- Footer -->
	<%@include file="includes/footer.jsp"%>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>