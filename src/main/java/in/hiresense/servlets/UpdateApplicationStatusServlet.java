package in.hiresense.servlets;

import java.io.IOException;

import in.hiresense.dao.ApplicationDAO;
import in.hiresense.dao.JobDAO;
import in.hiresense.dao.UserDAO;
import in.hiresense.models.ApplicationPojo;
import in.hiresense.models.JobPojo;
import in.hiresense.models.UserPojo;
import in.hiresense.utils.MailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateApplicationStatusServlet")
public class UpdateApplicationStatusServlet extends HttpServlet {
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null ||
            !"employer".equals(session.getAttribute("userRole"))) {
            res.sendRedirect("login.jsp");
            return;
        }

        try {
            int appId = Integer.parseInt(req.getParameter("appId"));
            String newStatus = req.getParameter("status");
            int jobId = Integer.parseInt(req.getParameter("jobId"));

            if (!newStatus.equals("shortlisted") && !newStatus.equals("rejected")) {
                res.sendRedirect("ViewApplicantsServlet?jobId=" + jobId + "&error=invalid_status");
                return;
            }

            // Get application details to find the applicant
            ApplicationPojo application = ApplicationDAO.getApplicationById(appId);
            if (application == null) {
                res.sendRedirect("ViewApplicantsServlet?jobId=" + jobId + "&error=application_not_found");
                return;
            }
            
            boolean updated = ApplicationDAO.updateApplicationStatus(appId, newStatus);
            if (updated) {
                // Get applicant's information (not employer's)
                UserPojo applicant = UserDAO.getUserById(application.getUserId());
                JobPojo job = JobDAO.getJobById(jobId);
                
                // Send email notification to the applicant
                if (applicant != null && job != null) {
                    try {
                        MailUtil.sendApplicationStatusUpdate(
                            applicant.getName(),
                            applicant.getEmail(),
                            job.getTitle(),
                            job.getCompany(),
                            newStatus
                        );
                    } catch (Exception e) {
                        // Log error but don't fail the status update
                        System.err.println("Failed to send email notification: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                res.sendRedirect("ViewApplicantsServlet?jobId=" + jobId + "&status=" + newStatus + "&success=updated");
            } else {
                res.sendRedirect("ViewApplicantsServlet?jobId=" + jobId + "&error=update_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("error.jsp");
        }
    }
}