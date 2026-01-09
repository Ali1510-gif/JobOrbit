<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Set"%>
<%@ page import="in.hiresense.models.JobPojo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Bookmarks | ${applicationScope.appName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<%@include file="/includes/head.jsp"%>
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
    if (bookmarkedIds == null) bookmarkedIds = new java.util.HashSet<>();
    %>

    <main class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-bookmark-fill"></i> My Bookmarks</h2>
            <a href="userDashboard" class="btn btn-outline-primary">
                <i class="bi bi-arrow-left"></i> Back to Dashboard
            </a>
        </div>

        <div class="row g-4">
            <%
            if (jobs != null && !jobs.isEmpty()) {
                for (JobPojo job : jobs) {
            %>
            <div class="col-md-6 col-lg-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title"><%= job.getTitle() %></h5>
                        <p class="text-muted"><%= job.getCompany() %></p>
                        <p class="small text-muted">
                            <i class="bi bi-geo-alt"></i> <%= job.getLocation() %> | 
                            <i class="bi bi-briefcase"></i> <%= job.getExperience() %>
                        </p>
                        <div class="d-flex gap-2">
                            <a href="userDashboard?jobId=<%= job.getId() %>" class="btn btn-primary btn-sm">View Details</a>
                            <button type="button" class="btn btn-outline-danger btn-sm" 
                                    onclick="removeBookmark(<%= job.getId() %>)">
                                <i class="bi bi-bookmark-x"></i> Remove
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
                    <i class="bi bi-bookmark"></i> You haven't bookmarked any jobs yet.
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
        function removeBookmark(jobId) {
            const formData = new FormData();
            formData.append("jobId", jobId);
            formData.append("action", "remove");
            
            fetch("bookmarkJob", {
                method: "POST",
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    Swal.fire("Success", "Bookmark removed", "success").then(() => location.reload());
                } else {
                    Swal.fire("Error", "Failed to remove bookmark", "error");
                }
            });
        }
    </script>
</body>
</html>

