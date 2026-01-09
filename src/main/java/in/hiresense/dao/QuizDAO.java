package in.hiresense.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.hiresense.dbutils.DBConnection;
import in.hiresense.models.QuizPojo;

public class QuizDAO {
    
    public static List<QuizPojo> getAllActiveQuizzes() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<QuizPojo> quizzes = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM quizzes WHERE status = 'active' ORDER BY created_at DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                QuizPojo quiz = new QuizPojo(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getInt("duration_minutes"),
                    rs.getInt("total_questions"),
                    rs.getTimestamp("created_at"),
                    rs.getString("status")
                );
                quizzes.add(quiz);
            }
            return quizzes;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public static QuizPojo getQuizById(int quizId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM quizzes WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quizId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return new QuizPojo(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getInt("duration_minutes"),
                    rs.getInt("total_questions"),
                    rs.getTimestamp("created_at"),
                    rs.getString("status")
                );
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
}

