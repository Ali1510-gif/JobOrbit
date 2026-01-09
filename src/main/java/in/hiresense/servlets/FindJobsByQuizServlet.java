package in.hiresense.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.hiresense.dao.BookmarkDAO;
import in.hiresense.dao.JobDAO;
import in.hiresense.dao.QuizResultDAO;
import in.hiresense.dao.UserProfileDAO;
import in.hiresense.models.QuizResultPojo;
import in.hiresense.models.UserProfilePojo;
import in.hiresense.utils.JobMatchingUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/findJobsByQuiz")
public class FindJobsByQuizServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        if (session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }
        
        try {
            int userId = (Integer) session.getAttribute("userId");
            String quizIdParam = req.getParameter("quizId");
            
            if (quizIdParam == null) {
                res.sendRedirect("quizList");
                return;
            }
            
            int quizId = Integer.parseInt(quizIdParam);
            QuizResultPojo quizResult = QuizResultDAO.getLatestResultByUserAndQuiz(userId, quizId);
            
            if (quizResult == null) {
                req.setAttribute("error", "Please complete the quiz first to find matching jobs");
                req.getRequestDispatcher("quizList").forward(req, res);
                return;
            }
            
            // Get user skills from profile
            Set<String> userSkills = new HashSet<>();
            UserProfilePojo profile = UserProfileDAO.getProfileByUserId(userId);
            if (profile != null && profile.getSkills() != null) {
                String[] skills = profile.getSkills().split("[,;]");
                for (String skill : skills) {
                    userSkills.add(skill.trim());
                }
            }
            
            // Get all active jobs
            List<in.hiresense.models.JobPojo> allJobs = JobDAO.getAllJobsForUserDashboard(null, null, null, null, null);
            
            // Match jobs based on quiz results
            List<in.hiresense.models.JobPojo> matchedJobs = JobMatchingUtil.matchJobs(allJobs, quizResult, userSkills);
            
            // Get bookmarked job IDs
            Set<Integer> bookmarkedIds = BookmarkDAO.getBookmarkedJobIds(userId);
            Set<Integer> appliedJobIds = in.hiresense.dao.ApplicationDAO.getAppliedJobIds(userId);
            
            req.setAttribute("jobs", matchedJobs);
            req.setAttribute("bookmarkedIds", bookmarkedIds);
            req.setAttribute("appliedJobIds", appliedJobIds);
            req.setAttribute("quizResult", quizResult);
            req.getRequestDispatcher("findJobsByQuiz.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Error finding jobs", e);
        }
    }
}

