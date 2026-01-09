<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="in.hiresense.models.QuizPojo"%>
<%@ page import="in.hiresense.models.QuizResultPojo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Skill Assessment Quizzes | ${applicationScope.appName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<%@include file="/includes/head.jsp"%>
<style>
.quiz-card {
    transition: transform 0.3s, box-shadow 0.3s;
    border: none;
    border-radius: 15px;
}
.quiz-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(0,0,0,0.15);
}
.badge-score {
    font-size: 0.9rem;
    padding: 8px 12px;
}
</style>
</head>
<body>
    <%@ include file="includes/header.jsp"%>
    <%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    %>

    <main class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-clipboard-check"></i> Skill Assessment Quizzes</h2>
            <a href="userDashboard" class="btn btn-outline-primary">
                <i class="bi bi-arrow-left"></i> Back to Dashboard
            </a>
        </div>

        <div class="alert alert-info">
            <i class="bi bi-info-circle"></i> Complete quizzes to assess your skills and get matched with relevant jobs!
        </div>

        <div class="row g-4">
            <%
            List<QuizPojo> quizzes = (List<QuizPojo>) request.getAttribute("quizzes");
            if (quizzes != null && !quizzes.isEmpty()) {
                for (QuizPojo quiz : quizzes) {
                    QuizResultPojo result = (QuizResultPojo) request.getAttribute("quiz_" + quiz.getId() + "_result");
            %>
            <div class="col-md-6 col-lg-4">
                <div class="card quiz-card shadow-sm h-100">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <h5 class="card-title fw-bold"><%= quiz.getTitle() %></h5>
                            <span class="badge bg-primary"><%= quiz.getCategory() %></span>
                        </div>
                        <p class="text-muted mb-3"><%= quiz.getDescription() %></p>
                        
                        <div class="d-flex justify-content-between text-muted small mb-3">
                            <span><i class="bi bi-question-circle"></i> <%= quiz.getTotalQuestions() %> Questions</span>
                            <span><i class="bi bi-clock"></i> <%= quiz.getDurationMinutes() %> min</span>
                        </div>

                        <% if (result != null) { %>
                        <div class="alert alert-success mb-3">
                            <strong>Best Score:</strong> <%= String.format("%.1f", result.getPercentage()) %>%
                            <br><small>Completed: <%= new java.text.SimpleDateFormat("dd MMM yyyy").format(result.getCompletedAt()) %></small>
                        </div>
                        <div class="d-grid gap-2">
                            <a href="takeQuiz?quizId=<%= quiz.getId() %>" class="btn btn-outline-primary">
                                <i class="bi bi-arrow-repeat"></i> Retake Quiz
                            </a>
                            <a href="findJobsByQuiz?quizId=<%= quiz.getId() %>" class="btn btn-success">
                                <i class="bi bi-briefcase"></i> Find Matching Jobs
                            </a>
                        </div>
                        <% } else { %>
                        <a href="takeQuiz?quizId=<%= quiz.getId() %>" class="btn btn-primary w-100">
                            <i class="bi bi-play-circle"></i> Start Quiz
                        </a>
                        <% } %>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="col-12">
                <div class="alert alert-warning text-center">
                    <i class="bi bi-exclamation-triangle"></i> No quizzes available at the moment.
                </div>
            </div>
            <%
            }
            %>
        </div>
    </main>

    <%@ include file="includes/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

