<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="in.hiresense.models.EmployerProfilePojo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Company Profile | ${applicationScope.appName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<%@include file="/includes/head.jsp"%>
<style>
.profile-header {
    background: linear-gradient(135deg, #0ea5e9 0%, #3b82f6 50%, #8b5cf6 100%);
    color: white;
    padding: 3rem 0;
    border-radius: 15px;
    margin-bottom: 2rem;
    box-shadow: 0 8px 25px rgba(14, 165, 233, 0.3);
}
.form-card {
    border: none;
    border-radius: 15px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    transition: transform 0.3s;
}
.form-card:hover {
    transform: translateY(-5px);
}
</style>
</head>
<body>
    <%@ include file="includes/header.jsp"%>
    <%
    if (session.getAttribute("userId") == null || !"employer".equals(session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    EmployerProfilePojo profile = (EmployerProfilePojo) request.getAttribute("profile");
    String success = request.getParameter("success");
    %>

    <main class="container py-5">
        <div class="profile-header text-center">
            <h1><i class="bi bi-building"></i> Company Profile</h1>
            <p class="mb-0">Manage your company information and branding</p>
        </div>

        <% if ("true".equals(success)) { %>
        <div class="alert alert-success alert-dismissible fade show">
            <i class="bi bi-check-circle"></i> Profile updated successfully!
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <div class="row">
            <div class="col-lg-10 mx-auto">
                <div class="card form-card shadow-lg">
                    <div class="card-body p-4">
                        <form method="post" action="employerProfile">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-bold"><i class="bi bi-building"></i> Company Name *</label>
                                    <input type="text" class="form-control" name="companyName" 
                                           value="<%= profile != null && profile.getCompanyName() != null ? profile.getCompanyName() : "" %>" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold"><i class="bi bi-briefcase"></i> Company Type *</label>
                                    <select class="form-select" name="companyType" required>
                                        <option value="">Select Type</option>
                                        <option value="Startup" <%= profile != null && "Startup".equals(profile.getCompanyType()) ? "selected" : "" %>>Startup</option>
                                        <option value="SME" <%= profile != null && "SME".equals(profile.getCompanyType()) ? "selected" : "" %>>SME</option>
                                        <option value="Enterprise" <%= profile != null && "Enterprise".equals(profile.getCompanyType()) ? "selected" : "" %>>Enterprise</option>
                                        <option value="MNC" <%= profile != null && "MNC".equals(profile.getCompanyType()) ? "selected" : "" %>>MNC</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-bold"><i class="bi bi-industry"></i> Industry *</label>
                                    <select class="form-select" name="industry" required>
                                        <option value="">Select Industry</option>
                                        <option value="IT/Software" <%= profile != null && "IT/Software".equals(profile.getIndustry()) ? "selected" : "" %>>IT/Software</option>
                                        <option value="Finance" <%= profile != null && "Finance".equals(profile.getIndustry()) ? "selected" : "" %>>Finance</option>
                                        <option value="Healthcare" <%= profile != null && "Healthcare".equals(profile.getIndustry()) ? "selected" : "" %>>Healthcare</option>
                                        <option value="Education" <%= profile != null && "Education".equals(profile.getIndustry()) ? "selected" : "" %>>Education</option>
                                        <option value="Manufacturing" <%= profile != null && "Manufacturing".equals(profile.getIndustry()) ? "selected" : "" %>>Manufacturing</option>
                                        <option value="Retail" <%= profile != null && "Retail".equals(profile.getIndustry()) ? "selected" : "" %>>Retail</option>
                                        <option value="Other" <%= profile != null && "Other".equals(profile.getIndustry()) ? "selected" : "" %>>Other</option>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold"><i class="bi bi-people"></i> Company Size *</label>
                                    <select class="form-select" name="companySize" required>
                                        <option value="">Select Size</option>
                                        <option value="1-10" <%= profile != null && "1-10".equals(profile.getCompanySize()) ? "selected" : "" %>>1-10 employees</option>
                                        <option value="11-50" <%= profile != null && "11-50".equals(profile.getCompanySize()) ? "selected" : "" %>>11-50 employees</option>
                                        <option value="51-200" <%= profile != null && "51-200".equals(profile.getCompanySize()) ? "selected" : "" %>>51-200 employees</option>
                                        <option value="201-500" <%= profile != null && "201-500".equals(profile.getCompanySize()) ? "selected" : "" %>>201-500 employees</option>
                                        <option value="500+" <%= profile != null && "500+".equals(profile.getCompanySize()) ? "selected" : "" %>>500+ employees</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label fw-bold"><i class="bi bi-globe"></i> Website</label>
                                    <input type="url" class="form-control" name="website" 
                                           placeholder="https://www.example.com"
                                           value="<%= profile != null && profile.getWebsite() != null ? profile.getWebsite() : "" %>">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold"><i class="bi bi-telephone"></i> Phone</label>
                                    <input type="tel" class="form-control" name="phone" 
                                           value="<%= profile != null && profile.getPhone() != null ? profile.getPhone() : "" %>">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label fw-bold"><i class="bi bi-geo-alt"></i> Address</label>
                                <textarea class="form-control" name="address" rows="2"><%= profile != null && profile.getAddress() != null ? profile.getAddress() : "" %></textarea>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">City</label>
                                    <input type="text" class="form-control" name="city" 
                                           value="<%= profile != null && profile.getCity() != null ? profile.getCity() : "" %>">
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">State</label>
                                    <input type="text" class="form-control" name="state" 
                                           value="<%= profile != null && profile.getState() != null ? profile.getState() : "" %>">
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label fw-bold">Pincode</label>
                                    <input type="text" class="form-control" name="pincode" 
                                           value="<%= profile != null && profile.getPincode() != null ? profile.getPincode() : "" %>">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label fw-bold"><i class="bi bi-file-text"></i> Company Description</label>
                                <textarea class="form-control" name="description" rows="4" 
                                          placeholder="Describe your company, culture, values, etc."><%= profile != null && profile.getDescription() != null ? profile.getDescription() : "" %></textarea>
                            </div>
                            
                            <div class="d-flex justify-content-between">
                                <a href="employerDashboard" class="btn btn-outline-secondary">
                                    <i class="bi bi-arrow-left"></i> Back to Dashboard
                                </a>
                                <button type="submit" class="btn btn-primary btn-lg">
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

