<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="in.hiresense.models.QuizPojo"%>
<%@ page import="in.hiresense.models.QuizResultPojo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quiz Results | ${applicationScope.appName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<%@include file="/includes/head.jsp"%>
<style>
.score-circle {
    width: 200px;
    height: 200px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 3rem;
    font-weight: bold;
    margin: 0 auto;
    background: linear-gradient(135deg, #0ea5e9 0%, #3b82f6 50%, #8b5cf6 100%);
    color: white;
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
    
    QuizPojo quiz = (QuizPojo) request.getAttribute("quiz");
    QuizResultPojo result = (QuizResultPojo) request.getAttribute("result");
    
    if (quiz == null || result == null) {
        response.sendRedirect("quizList");
        return;
    }
    %>

    <main class="container py-5">
        <div class="text-center mb-5">
            <h2><%= quiz.getTitle() %> - Results</h2>
            <p class="text-muted">Completed on <%= new java.text.SimpleDateFormat("dd MMM yyyy 'at' HH:mm").format(result.getCompletedAt()) %></p>
        </div>

        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <div class="score-circle mb-3">
                            <%= String.format("%.1f", result.getPercentage()) %>%
                        </div>
                        <h4>Your Score</h4>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6 mb-4">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <h5 class="mb-3">Performance Summary</h5>
                        <div class="mb-2">
                            <strong>Total Questions:</strong> <%= result.getTotalQuestions() %>
                        </div>
                        <div class="mb-2">
                            <strong>Correct Answers:</strong> <%= result.getCorrectAnswers() %>
                        </div>
                        <div class="mb-2">
                            <strong>Score:</strong> <%= result.getTotalScore() %> / <%= result.getMaxScore() %>
                        </div>
                        <div class="mb-2">
                            <strong>Percentage:</strong> <%= String.format("%.2f", result.getPercentage()) %>%
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <h5 class="mb-3">Skill Breakdown</h5>
                <%
                String skillScores = result.getSkillScores();
                if (skillScores != null && !skillScores.trim().isEmpty()) {
                    String[] pairs = skillScores.split(",");
                    for (String pair : pairs) {
                        String[] keyValue = pair.split(":");
                        if (keyValue.length == 2) {
                            String skill = keyValue[0].trim();
                            String score = keyValue[1].trim();
                %>
                <div class="d-flex justify-content-between align-items-center mb-2">
                    <span><%= skill %></span>
                    <span class="badge bg-primary"><%= score %> points</span>
                </div>
                <%
                        }
                    }
                } else {
                %>
                <p class="text-muted">No skill breakdown available.</p>
                <%
                }
                %>
            </div>
        </div>

        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
            <a href="findJobsByQuiz?quizId=<%= quiz.getId() %>" class="btn btn-primary btn-lg">
                <i class="bi bi-briefcase"></i> Find Matching Jobs
            </a>
            <a href="quizList" class="btn btn-outline-secondary btn-lg">
                <i class="bi bi-arrow-left"></i> Back to Quizzes
            </a>
        </div>
    </main>

    <%@ include file="includes/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

