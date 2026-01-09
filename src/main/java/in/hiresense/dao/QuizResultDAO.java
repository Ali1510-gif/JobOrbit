package in.hiresense.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.hiresense.dbutils.DBConnection;
import in.hiresense.models.QuizResultPojo;

public class QuizResultDAO {
    
    public static int saveQuizResult(QuizResultPojo result) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO quiz_results (user_id, quiz_id, total_questions, correct_answers, " +
                        "total_score, max_score, percentage, skill_scores) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, result.getUserId());
            ps.setInt(2, result.getQuizId());
            ps.setInt(3, result.getTotalQuestions());
            ps.setInt(4, result.getCorrectAnswers());
            ps.setInt(5, result.getTotalScore());
            ps.setInt(6, result.getMaxScore());
            ps.setDouble(7, result.getPercentage());
            ps.setString(8, result.getSkillScores());
            return ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
        }
    }
    
    public static List<QuizResultPojo> getResultsByUserId(int userId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<QuizResultPojo> results = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM quiz_results WHERE user_id = ? ORDER BY completed_at DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                QuizResultPojo result = new QuizResultPojo(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("quiz_id"),
                    rs.getInt("total_questions"),
                    rs.getInt("correct_answers"),
                    rs.getInt("total_score"),
                    rs.getInt("max_score"),
                    rs.getDouble("percentage"),
                    rs.getString("skill_scores"),
                    rs.getTimestamp("completed_at")
                );
                results.add(result);
            }
            return results;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public static QuizResultPojo getLatestResultByUserAndQuiz(int userId, int quizId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM quiz_results WHERE user_id = ? AND quiz_id = ? " +
                        "ORDER BY completed_at DESC LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, quizId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return new QuizResultPojo(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("quiz_id"),
                    rs.getInt("total_questions"),
                    rs.getInt("correct_answers"),
                    rs.getInt("total_score"),
                    rs.getInt("max_score"),
                    rs.getDouble("percentage"),
                    rs.getString("skill_scores"),
                    rs.getTimestamp("completed_at")
                );
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
}

