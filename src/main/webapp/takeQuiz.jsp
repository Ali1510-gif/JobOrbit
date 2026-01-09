<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="in.hiresense.models.QuizPojo"%>
<%@ page import="in.hiresense.models.QuestionPojo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Take Quiz | ${applicationScope.appName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<%@include file="/includes/head.jsp"%>
<style>
.question-card {
    margin-bottom: 20px;
    border-left: 4px solid #0d6efd;
}
.timer {
    position: fixed;
    top: 80px;
    right: 20px;
    background: #dc3545;
    color: white;
    padding: 15px 20px;
    border-radius: 10px;
    font-size: 1.2rem;
    font-weight: bold;
    z-index: 1000;
    box-shadow: 0 4px 10px rgba(0,0,0,0.2);
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
    List<QuestionPojo> questions = (List<QuestionPojo>) request.getAttribute("questions");
    
    if (quiz == null || questions == null || questions.isEmpty()) {
        response.sendRedirect("quizList");
        return;
    }
    %>

    <div class="timer" id="timer">
        <i class="bi bi-clock"></i> <span id="timeDisplay">00:00</span>
    </div>

    <main class="container py-5">
        <div class="mb-4">
            <h2><%= quiz.getTitle() %></h2>
            <p class="text-muted"><%= quiz.getDescription() %></p>
            <div class="d-flex gap-3 text-muted">
                <span><i class="bi bi-question-circle"></i> <%= questions.size() %> Questions</span>
                <span><i class="bi bi-clock"></i> <%= quiz.getDurationMinutes() %> Minutes</span>
                <span><i class="bi bi-tag"></i> <%= quiz.getCategory() %></span>
            </div>
        </div>

        <form id="quizForm" method="post" action="submitQuiz">
            <input type="hidden" name="quizId" value="<%= quiz.getId() %>">
            
            <%
            int questionNum = 1;
            for (QuestionPojo question : questions) {
            %>
            <div class="card question-card shadow-sm mb-4">
                <div class="card-body">
                    <h5 class="card-title">Question <%= questionNum %>:</h5>
                    <p class="card-text mb-4"><%= question.getQuestionText() %></p>
                    
                    <div class="form-check mb-2">
                        <input class="form-check-input" type="radio" name="answer_<%= question.getId() %>" 
                               id="q<%= question.getId() %>_1" value="1" required>
                        <label class="form-check-label" for="q<%= question.getId() %>_1">
                            <%= question.getOption1() %>
                        </label>
                    </div>
                    <div class="form-check mb-2">
                        <input class="form-check-input" type="radio" name="answer_<%= question.getId() %>" 
                               id="q<%= question.getId() %>_2" value="2" required>
                        <label class="form-check-label" for="q<%= question.getId() %>_2">
                            <%= question.getOption2() %>
                        </label>
                    </div>
                    <div class="form-check mb-2">
                        <input class="form-check-input" type="radio" name="answer_<%= question.getId() %>" 
                               id="q<%= question.getId() %>_3" value="3" required>
                        <label class="form-check-label" for="q<%= question.getId() %>_3">
                            <%= question.getOption3() %>
                        </label>
                    </div>
                    <div class="form-check mb-2">
                        <input class="form-check-input" type="radio" name="answer_<%= question.getId() %>" 
                               id="q<%= question.getId() %>_4" value="4" required>
                        <label class="form-check-label" for="q<%= question.getId() %>_4">
                            <%= question.getOption4() %>
                        </label>
                    </div>
                </div>
            </div>
            <%
                questionNum++;
            }
            %>

            <div class="d-flex justify-content-between mb-4">
                <a href="quizList" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Cancel
                </a>
                <button type="submit" class="btn btn-primary btn-lg">
                    <i class="bi bi-check-circle"></i> Submit Quiz
                </button>
            </div>
        </form>
    </main>

    <%@ include file="includes/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Timer functionality
        const durationMinutes = <%= quiz.getDurationMinutes() %>;
        let timeLeft = durationMinutes * 60; // Convert to seconds
        
        function updateTimer() {
            const minutes = Math.floor(timeLeft / 60);
            const seconds = timeLeft % 60;
            document.getElementById('timeDisplay').textContent = 
                String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0');
            
            if (timeLeft <= 0) {
                alert('Time is up! Submitting quiz...');
                document.getElementById('quizForm').submit();
            } else {
                timeLeft--;
                setTimeout(updateTimer, 1000);
            }
        }
        
        updateTimer();
    </script>
</body>
</html>

