<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Set"%>
<%@ page import="in.hiresense.models.JobPojo"%>
<%@ page import="in.hiresense.models.QuizResultPojo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Matching Jobs | ${applicationScope.appName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<%@include file="/includes/head.jsp"%>
<style>
.match-badge {
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 0.9rem;
    padding: 5px 10px;
}
.job-card {
    transition: transform 0.3s;
    border-left: 4px solid #0d6efd;
}
.job-card:hover {
    transform: translateY(-5px);
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
    
    List<JobPojo> jobs = (List<JobPojo>) request.getAttribute("jobs");
    Set<Integer> bookmarkedIds = (Set<Integer>) request.getAttribute("bookmarkedIds");
    Set<Integer> appliedJobIds = (Set<Integer>) request.getAttribute("appliedJobIds");
    QuizResultPojo quizResult = (QuizResultPojo) request.getAttribute("quizResult");
    
    if (bookmarkedIds == null) bookmarkedIds = new java.util.HashSet<>();
    if (appliedJobIds == null) appliedJobIds = new java.util.HashSet<>();
    %>

    <main class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2><i class="bi bi-briefcase"></i> Jobs Matching Your Quiz Results</h2>
                <% if (quizResult != null) { %>
                <p class="text-muted">Based on your quiz score: <%= String.format("%.1f", quizResult.getPercentage()) %>%</p>
                <% } %>
            </div>
            <a href="quizList" class="btn btn-outline-primary">
                <i class="bi bi-arrow-left"></i> Back to Quizzes
            </a>
        </div>

        <div class="row g-4">
            <%
            if (jobs != null && !jobs.isEmpty()) {
                for (JobPojo job : jobs) {
                    boolean isBookmarked = bookmarkedIds.contains(job.getId());
                    boolean isApplied = appliedJobIds.contains(job.getId());
            %>
            <div class="col-md-6 col-lg-4">
                <div class="card job-card shadow-sm h-100 position-relative">
                    <span class="badge bg-success match-badge">
                        <%= String.format("%.0f", job.getScore()) %>% Match
                    </span>
                    
                    <div class="card-body">
                        <h5 class="card-title fw-bold"><%= job.getTitle() %></h5>
                        <p class="text-muted mb-2"><%= job.getCompany() %></p>
                        
                        <div class="d-flex flex-wrap text-muted small mb-3 gap-2">
                            <span><i class="bi bi-briefcase"></i> <%= job.getExperience() %></span>
                            <span><i class="bi bi-currency-rupee"></i> <%= job.getPackageLpa() %></span>
                            <span><i class="bi bi-geo-alt"></i> <%= job.getLocation() %></span>
                        </div>
                        
                        <div class="d-flex gap-2">
                            <% if (isApplied) { %>
                            <span class="badge bg-success">✅ Applied</span>
                            <% } else { %>
                            <button type="button" class="btn btn-primary btn-sm" 
                                    onclick="applyJob(<%= job.getId() %>, <%= job.getScore() %>)">
                                Apply Now
                            </button>
                            <% } %>
                            
                            <button type="button" class="btn btn-outline-secondary btn-sm" 
                                    onclick="toggleBookmark(<%= job.getId() %>, <%= isBookmarked %>)">
                                <i class="bi <%= isBookmarked ? "bi-bookmark-fill" : "bi-bookmark" %>"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <div class="col-12">
                <div class="alert alert-info text-center">
                    <i class="bi bi-info-circle"></i> No matching jobs found. Try completing more quizzes or updating your profile.
                </div>
            </div>
            <%
            }
            %>
        </div>
    </main>

    <%@ include file="includes/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        function applyJob(jobId, score) {
            const form = document.createElement("form");
            form.method = "POST";
            form.action = "ApplyJobServlet";
            
            const input1 = document.createElement("input");
            input1.type = "hidden";
            input1.name = "jobId";
            input1.value = jobId;
            form.appendChild(input1);
            
            const input2 = document.createElement("input");
            input2.type = "hidden";
            input2.name = "score";
            input2.value = score;
            form.appendChild(input2);
            
            document.body.appendChild(form);
            form.submit();
        }
        
        function toggleBookmark(jobId, isBookmarked) {
            const action = isBookmarked ? "remove" : "add";
            const formData = new FormData();
            formData.append("jobId", jobId);
            formData.append("action", action);
            
            fetch("bookmarkJob", {
                method: "POST",
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    Swal.fire("Error", "Failed to update bookmark", "error");
                }
            });
        }
    </script>
</body>
</html>

