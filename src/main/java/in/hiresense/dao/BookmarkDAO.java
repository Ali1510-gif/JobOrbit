package in.hiresense.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.hiresense.dbutils.DBConnection;
import in.hiresense.models.BookmarkPojo;
import in.hiresense.models.JobPojo;

public class BookmarkDAO {
    
    public static boolean addBookmark(int userId, int jobId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBConnection.getConnection();
            // Check if already bookmarked
            if (isBookmarked(userId, jobId)) {
                return false;
            }
            
            String sql = "INSERT INTO bookmarks (user_id, job_id) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, jobId);
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
        }
    }
    
    public static boolean removeBookmark(int userId, int jobId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM bookmarks WHERE user_id = ? AND job_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, jobId);
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
        }
    }
    
    public static boolean isBookmarked(int userId, int jobId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM bookmarks WHERE user_id = ? AND job_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, jobId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public static Set<Integer> getBookmarkedJobIds(int userId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Set<Integer> bookmarkedIds = new HashSet<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT job_id FROM bookmarks WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                bookmarkedIds.add(rs.getInt("job_id"));
            }
            return bookmarkedIds;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public static List<JobPojo> getBookmarkedJobs(int userId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<JobPojo> jobs = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT j.* FROM jobs j INNER JOIN bookmarks b ON j.id = b.job_id " +
                        "WHERE b.user_id = ? AND j.status = 'active' ORDER BY b.created_at DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                JobPojo job = new JobPojo();
                job.setId(rs.getInt("id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setSkills(rs.getString("skills"));
                job.setCompany(rs.getString("company"));
                job.setLocation(rs.getString("location"));
                job.setExperience(rs.getString("experience"));
                job.setPackageLpa(rs.getString("package_lpa"));
                job.setVacancies(rs.getInt("vacancies"));
                job.setEmployerId(rs.getInt("employer_id"));
                job.setCreatedAt(rs.getTimestamp("created_at"));
                job.setStatus(rs.getString("status"));
                jobs.add(job);
            }
            return jobs;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
}

