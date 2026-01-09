package in.hiresense.servlets;

import java.io.IOException;
import java.util.List;

import in.hiresense.dao.QuizDAO;
import in.hiresense.dao.QuizResultDAO;
import in.hiresense.models.QuizPojo;
import in.hiresense.models.QuizResultPojo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/quizList")
public class QuizListServlet extends HttpServlet {
    
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
            List<QuizPojo> quizzes = QuizDAO.getAllActiveQuizzes();
            
            // Get latest results for each quiz
            for (QuizPojo quiz : quizzes) {
                QuizResultPojo latestResult = QuizResultDAO.getLatestResultByUserAndQuiz(userId, quiz.getId());
                if (latestResult != null) {
                    // Store in request attribute for display
                    req.setAttribute("quiz_" + quiz.getId() + "_result", latestResult);
                }
            }
            
            req.setAttribute("quizzes", quizzes);
            req.getRequestDispatcher("quizList.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Error loading quizzes", e);
        }
    }
}

