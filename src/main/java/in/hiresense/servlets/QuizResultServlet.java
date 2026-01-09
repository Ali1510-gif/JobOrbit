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

@WebServlet("/quizResult")
public class QuizResultServlet extends HttpServlet {
    
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
            int quizId = Integer.parseInt(req.getParameter("quizId"));
            
            QuizPojo quiz = QuizDAO.getQuizById(quizId);
            QuizResultPojo result = QuizResultDAO.getLatestResultByUserAndQuiz(userId, quizId);
            
            if (quiz == null || result == null) {
                res.sendRedirect("quizList");
                return;
            }
            
            req.setAttribute("quiz", quiz);
            req.setAttribute("result", result);
            req.getRequestDispatcher("quizResult.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Error loading quiz result", e);
        }
    }
}

