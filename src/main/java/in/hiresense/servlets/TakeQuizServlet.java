package in.hiresense.servlets;

import java.io.IOException;
import java.util.List;

import in.hiresense.dao.QuestionDAO;
import in.hiresense.dao.QuizDAO;
import in.hiresense.models.QuestionPojo;
import in.hiresense.models.QuizPojo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/takeQuiz")
public class TakeQuizServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        if (session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }
        
        try {
            String quizIdParam = req.getParameter("quizId");
            if (quizIdParam == null) {
                res.sendRedirect("quizList");
                return;
            }
            
            int quizId = Integer.parseInt(quizIdParam);
            QuizPojo quiz = QuizDAO.getQuizById(quizId);
            
            if (quiz == null || !"active".equals(quiz.getStatus())) {
                req.setAttribute("error", "Quiz not found or inactive");
                req.getRequestDispatcher("quizList.jsp").forward(req, res);
                return;
            }
            
            List<QuestionPojo> questions = QuestionDAO.getQuestionsByQuizId(quizId);
            
            req.setAttribute("quiz", quiz);
            req.setAttribute("questions", questions);
            req.getRequestDispatcher("takeQuiz.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Error loading quiz", e);
        }
    }
}

