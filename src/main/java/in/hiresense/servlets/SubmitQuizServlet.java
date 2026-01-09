package in.hiresense.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.hiresense.dao.QuestionDAO;
import in.hiresense.dao.QuizDAO;
import in.hiresense.dao.QuizResultDAO;
import in.hiresense.models.QuestionPojo;
import in.hiresense.models.QuizPojo;
import in.hiresense.models.QuizResultPojo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/submitQuiz")
public class SubmitQuizServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) 
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
            if (quiz == null) {
                res.sendRedirect("quizList");
                return;
            }
            
            List<QuestionPojo> questions = QuestionDAO.getQuestionsByQuizId(quizId);
            
            int correctAnswers = 0;
            int totalScore = 0;
            int maxScore = 0;
            Map<String, Integer> skillScores = new HashMap<>();
            
            // Evaluate answers
            for (QuestionPojo question : questions) {
                maxScore += question.getPoints();
                String answerParam = req.getParameter("answer_" + question.getId());
                
                if (answerParam != null) {
                    int userAnswer = Integer.parseInt(answerParam);
                    if (userAnswer == question.getCorrectAnswer()) {
                        correctAnswers++;
                        totalScore += question.getPoints();
                        
                        // Track skill scores
                        String skillTag = question.getSkillTag();
                        if (skillTag != null && !skillTag.trim().isEmpty()) {
                            skillScores.put(skillTag, 
                                skillScores.getOrDefault(skillTag, 0) + question.getPoints());
                        }
                    }
                }
            }
            
            double percentage = maxScore > 0 ? (totalScore * 100.0 / maxScore) : 0;
            
            // Build skill scores JSON string
            StringBuilder skillScoresJson = new StringBuilder();
            for (Map.Entry<String, Integer> entry : skillScores.entrySet()) {
                if (skillScoresJson.length() > 0) {
                    skillScoresJson.append(",");
                }
                skillScoresJson.append(entry.getKey()).append(":").append(entry.getValue());
            }
            
            // Save result
            QuizResultPojo result = new QuizResultPojo();
            result.setUserId(userId);
            result.setQuizId(quizId);
            result.setTotalQuestions(questions.size());
            result.setCorrectAnswers(correctAnswers);
            result.setTotalScore(totalScore);
            result.setMaxScore(maxScore);
            result.setPercentage(percentage);
            result.setSkillScores(skillScoresJson.toString());
            
            QuizResultDAO.saveQuizResult(result);
            
            // Redirect to results page
            res.sendRedirect("quizResult?quizId=" + quizId);
        } catch (Exception e) {
            throw new ServletException("Error submitting quiz", e);
        }
    }
}

