<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="in.hiresense.models.UserProfilePojo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Profile | ${applicationScope.appName}</title>
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
    
    UserProfilePojo profile = (UserProfilePojo) request.getAttribute("profile");
    String success = request.getParameter("success");
    %>

    <main class="container py-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-person-circle"></i> My Profile</h2>
            <a href="userDashboard" class="btn btn-outline-primary">
                <i class="bi bi-arrow-left"></i> Back to Dashboard
            </a>
        </div>

        <% if ("true".equals(success)) { %>
        <div class="alert alert-success alert-dismissible fade show">
            <i class="bi bi-check-circle"></i> Profile updated successfully!
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <div class="row">
            <div class="col-md-8 mx-auto">
                <div class="card shadow-sm">
                    <div class="card-body">
                        <form method="post" action="userProfile">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Phone</label>
                                    <input type="tel" class="form-control" name="phone" 
                                           value="<%= profile != null && profile.getPhone() != null ? profile.getPhone() : "" %>">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">City</label>
                                    <input type="text" class="form-control" name="city" 
                                           value="<%= profile != null && profile.getCity() != null ? profile.getCity() : "" %>">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Address</label>
                                <textarea class="form-control" name="address" rows="2"><%= profile != null && profile.getAddress() != null ? profile.getAddress() : "" %></textarea>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <label class="form-label">State</label>
                                    <input type="text" class="form-control" name="state" 
                                           value="<%= profile != null && profile.getState() != null ? profile.getState() : "" %>">
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Country</label>
                                    <input type="text" class="form-control" name="country" 
                                           value="<%= profile != null && profile.getCountry() != null ? profile.getCountry() : "" %>">
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Pincode</label>
                                    <input type="text" class="form-control" name="pincode" 
                                           value="<%= profile != null && profile.getPincode() != null ? profile.getPincode() : "" %>">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Skills (comma-separated)</label>
                                <input type="text" class="form-control" name="skills" 
                                       placeholder="Java, Python, SQL, etc."
                                       value="<%= profile != null && profile.getSkills() != null ? profile.getSkills() : "" %>">
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Experience</label>
                                <input type="text" class="form-control" name="experience" 
                                       placeholder="e.g., 2 years in Software Development"
                                       value="<%= profile != null && profile.getExperience() != null ? profile.getExperience() : "" %>">
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Education</label>
                                <input type="text" class="form-control" name="education" 
                                       placeholder="e.g., B.Tech in Computer Science"
                                       value="<%= profile != null && profile.getEducation() != null ? profile.getEducation() : "" %>">
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Preferred Location</label>
                                    <input type="text" class="form-control" name="preferredLocation" 
                                           value="<%= profile != null && profile.getPreferredLocation() != null ? profile.getPreferredLocation() : "" %>">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Preferred Job Type</label>
                                    <select class="form-select" name="preferredJobType">
                                        <option value="">Select...</option>
                                        <option value="Full-time" <%= profile != null && "Full-time".equals(profile.getPreferredJobType()) ? "selected" : "" %>>Full-time</option>
                                        <option value="Part-time" <%= profile != null && "Part-time".equals(profile.getPreferredJobType()) ? "selected" : "" %>>Part-time</option>
                                        <option value="Contract" <%= profile != null && "Contract".equals(profile.getPreferredJobType()) ? "selected" : "" %>>Contract</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Expected Salary (LPA)</label>
                                <input type="number" step="0.1" class="form-control" name="expectedSalary" 
                                       value="<%= profile != null ? profile.getExpectedSalary() : "" %>">
                            </div>
                            
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-save"></i> Save Profile
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <%@ include file="includes/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

