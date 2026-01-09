package in.hiresense.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.hiresense.dbutils.DBConnection;
import in.hiresense.models.QuestionPojo;

public class QuestionDAO {
    
    public static List<QuestionPojo> getQuestionsByQuizId(int quizId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<QuestionPojo> questions = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM questions WHERE quiz_id = ? ORDER BY id";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quizId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                QuestionPojo question = new QuestionPojo(
                    rs.getInt("id"),
                    rs.getInt("quiz_id"),
                    rs.getString("question_text"),
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4"),
                    rs.getInt("correct_answer"),
                    rs.getString("skill_tag"),
                    rs.getInt("points")
                );
                questions.add(question);
            }
            return questions;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public static QuestionPojo getQuestionById(int questionId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM questions WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, questionId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return new QuestionPojo(
                    rs.getInt("id"),
                    rs.getInt("quiz_id"),
                    rs.getString("question_text"),
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4"),
                    rs.getInt("correct_answer"),
                    rs.getString("skill_tag"),
                    rs.getInt("points")
                );
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
}

