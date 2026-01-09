<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="in.hiresense.models.JobPojo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employer Dashboard | ${applicationScope.appName}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<%@include file="/includes/head.jsp"%>
<style>
.dashboard-header {
    background: linear-gradient(135deg, #0ea5e9 0%, #3b82f6 50%, #8b5cf6 100%);
    color: white;
    padding: 2rem;
    border-radius: 15px;
    margin-bottom: 2rem;
    box-shadow: 0 8px 25px rgba(14, 165, 233, 0.3);
}
.quick-actions {
    display: flex;
    gap: 1rem;
    flex-wrap: wrap;
}
</style>
</head>
<body>
    <%@include file="includes/header.jsp"%>
    <%
    if ((session.getAttribute("userId") == null) || !"employer".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    List<JobPojo> jobList = (List<JobPojo>) request.getAttribute("jobList");
    Integer totalJobs = (Integer) request.getAttribute("totalJobs");
    Integer activeJobs = (Integer) request.getAttribute("activeJobs");
    Integer totalApplicants = (Integer) request.getAttribute("totalApplicants");
    
    if (totalJobs == null) totalJobs = 0;
    if (activeJobs == null) activeJobs = 0;
    if (totalApplicants == null) totalApplicants = 0;
    %>

    <main class="container py-5">
        <!-- Dashboard Header -->
        <div class="dashboard-header">
            <div class="d-flex justify-content-between align-items-center flex-wrap">
                <div>
                    <h2 class="mb-2">
                        <i class="bi bi-briefcase"></i> Welcome, <%=session.getAttribute("userName")%>
                    </h2>
                    <p class="mb-0 opacity-75">Manage your job postings and applicants</p>
                </div>
                <div class="quick-actions mt-3 mt-md-0">
                    <a href="employerProfile" class="btn btn-light btn-modern">
                        <i class="bi bi-building"></i> Company Profile
                    </a>
                </div>
            </div>
        </div>

        <!-- Statistics Cards -->
        <div class="row g-4 mb-4">
            <div class="col-md-4">
                <div class="stat-card">
                    <div class="d-flex align-items-center justify-content-between">
                        <div>
                            <p class="stat-label">Total Jobs</p>
                            <h3 class="stat-number"><%= totalJobs %></h3>
                        </div>
                        <i class="bi bi-briefcase-fill" style="font-size: 3rem; opacity: 0.3;"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card" style="background: linear-gradient(135deg, #f97316 0%, #fb923c 100%);">
                    <div class="d-flex align-items-center justify-content-between">
                        <div>
                            <p class="stat-label">Active Jobs</p>
                            <h3 class="stat-number"><%= activeJobs %></h3>
                        </div>
                        <i class="bi bi-check-circle-fill" style="font-size: 3rem; opacity: 0.3;"></i>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card" style="background: linear-gradient(135deg, #10b981 0%, #34d399 100%);">
                    <div class="d-flex align-items-center justify-content-between">
                        <div>
                            <p class="stat-label">Total Applicants</p>
                            <h3 class="stat-number"><%= totalApplicants %></h3>
                        </div>
                        <i class="bi bi-people-fill" style="font-size: 3rem; opacity: 0.3;"></i>
                    </div>
                </div>
            </div>
        </div>

        <!-- Post a Job Form -->
        <div class="card card-modern mb-4">
            <div class="card-body p-4">
                <h5 class="card-title mb-4">
                    <i class="bi bi-plus-circle"></i> Post a New Job
                </h5>
                <form action="PostJobServlet" method="post">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Job Title *</label>
                            <input type="text" name="title" class="form-control form-control-modern" placeholder="e.g., Senior Java Developer" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Company Name *</label>
                            <input type="text" name="company" class="form-control form-control-modern" placeholder="Your Company Name" required />
                        </div>
                        <div class="col-12">
                            <label class="form-label fw-bold">Job Description *</label>
                            <textarea name="description" class="form-control form-control-modern" rows="3" placeholder="Describe the role, responsibilities, and requirements..." required></textarea>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Required Skills *</label>
                            <input type="text" name="skills" class="form-control form-control-modern" placeholder="Java, Spring Boot, MySQL" required />
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Location *</label>
                            <select name="location" class="form-select form-control-modern" required>
                                <option value="">Select Location</option>
                                <option>Bangalore</option>
                                <option>Mumbai</option>
                                <option>Delhi</option>
                                <option>Chennai</option>
                                <option>Pune</option>
                                <option>Hyderabad</option>
                                <option>Remote</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-bold">Experience *</label>
                            <select name="experience" class="form-select form-control-modern" required>
                                <option value="">Select Experience</option>
                                <option>Fresher</option>
                                <option>0 - 1 year</option>
                                <option>1 - 2 years</option>
                                <option>2 - 3 years</option>
                                <option>3 - 5 years</option>
                                <option>5+ years</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-bold">Package (LPA) *</label>
                            <select name="packageLpa" class="form-select form-control-modern" required>
                                <option value="">Select Package</option>
                                <option>1-2 Lacs P.A.</option>
                                <option>2-3 Lacs P.A.</option>
                                <option>3-4 Lacs P.A.</option>
                                <option>4-5 Lacs P.A.</option>
                                <option>5-10 Lacs P.A.</option>
                                <option>10-20 Lacs P.A.</option>
                                <option>20+ Lacs P.A.</option>
                                <option>Not disclosed</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label fw-bold">Vacancies *</label>
                            <input type="number" name="vacancies" min="1" class="form-control form-control-modern" placeholder="Number of openings" required />
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary-modern btn-modern">
                                <i class="bi bi-send"></i> Post Job
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Search & Filter -->
        <div class="card card-modern mb-4">
            <div class="card-body p-4">
                <form method="get" action="employerDashboard">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label class="form-label fw-bold">Search</label>
                            <input type="text" name="search" class="form-control form-control-modern" placeholder="Search by job title..." value="${param.search}" />
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Status</label>
                            <select name="status" class="form-select form-control-modern">
                                <option value="">All Status</option>
                                <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
                                <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Sort By</label>
                            <select name="sort" class="form-select form-control-modern">
                                <option value="">Default</option>
                                <option value="asc" ${param.sort == 'asc' ? 'selected' : ''}>Least Applicants</option>
                                <option value="desc" ${param.sort == 'desc' ? 'selected' : ''}>Most Applicants</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary-modern btn-modern w-100">
                                <i class="bi bi-search"></i> Search
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Posted Jobs Table -->
        <div class="card card-modern">
            <div class="card-body p-4">
                <h5 class="card-title mb-4">
                    <i class="bi bi-list-ul"></i> Your Posted Jobs
                </h5>
                <%
                if (jobList != null && !jobList.isEmpty()) {
                %>
                <div class="table-responsive">
                    <table class="table table-modern table-hover">
                        <thead>
                            <tr>
                                <th>Job Title</th>
                                <th>Company</th>
                                <th>Location</th>
                                <th>Applicants</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            for (JobPojo job : jobList) {
                            %>
                            <tr>
                                <td><strong><%=job.getTitle()%></strong></td>
                                <td><%=job.getCompany()%></td>
                                <td><i class="bi bi-geo-alt"></i> <%=job.getLocation()%></td>
                                <td>
                                    <span class="badge bg-primary badge-modern">
                                        <i class="bi bi-people"></i> <%=job.getApplicantCount()%>
                                    </span>
                                </td>
                                <td>
                                    <span class="badge <%= "active".equals(job.getStatus()) ? "bg-success" : "bg-secondary" %> badge-modern">
                                        <%=job.getStatus().toUpperCase()%>
                                    </span>
                                </td>
                                <td>
                                    <div class="d-flex gap-2">
                                        <a href="ViewApplicantsServlet?jobId=<%=job.getId()%>" 
                                           class="btn btn-sm btn-primary-modern btn-modern">
                                            <i class="bi bi-eye"></i> View
                                        </a>
                                        <a href="ToggleJobStatusServlet?jobId=<%=job.getId()%>"
                                           class="btn btn-sm <%= "active".equals(job.getStatus()) ? "btn-warning" : "btn-success" %> btn-modern">
                                            <%="active".equals(job.getStatus()) ? "<i class='bi bi-pause'></i> Deactivate" : "<i class='bi bi-play'></i> Activate"%>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                            <%
                            }
                            %>
                        </tbody>
                    </table>
                </div>
                <%
                } else {
                %>
                <div class="text-center py-5">
                    <i class="bi bi-inbox" style="font-size: 4rem; color: #ccc;"></i>
                    <p class="text-muted mt-3">No jobs posted yet. Start by posting your first job!</p>
                </div>
                <%
                }
                %>
            </div>
        </div>
    </main>

    <%
    String success = request.getParameter("success");
    if ("1".equals(success)) {
    %>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        Swal.fire({
            icon: 'success',
            title: 'Job Posted!',
            text: 'Your job has been posted successfully.',
            timer: 2000,
            showConfirmButton: false
        });
    </script>
    <%
    }
    %>

    <%
    String error = request.getParameter("error");
    if ("1".equals(error)) {
    %>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        Swal.fire({
            icon: 'error',
            title: 'Failed!',
            text: 'Something went wrong. Please try again.',
            confirmButtonText: 'Okay'
        });
    </script>
    <%
    }
    %>

    <%@include file="includes/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
