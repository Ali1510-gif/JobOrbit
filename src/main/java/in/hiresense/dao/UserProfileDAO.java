package in.hiresense.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import in.hiresense.dbutils.DBConnection;
import in.hiresense.models.UserProfilePojo;

public class UserProfileDAO {
    
    public static UserProfilePojo getProfileByUserId(int userId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM user_profiles WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                UserProfilePojo profile = new UserProfilePojo();
                profile.setUserId(rs.getInt("user_id"));
                profile.setPhone(rs.getString("phone"));
                profile.setAddress(rs.getString("address"));
                profile.setCity(rs.getString("city"));
                profile.setState(rs.getString("state"));
                profile.setCountry(rs.getString("country"));
                profile.setPincode(rs.getString("pincode"));
                profile.setSkills(rs.getString("skills"));
                profile.setExperience(rs.getString("experience"));
                profile.setEducation(rs.getString("education"));
                profile.setResumePath(rs.getString("resume_path"));
                profile.setPreferredLocation(rs.getString("preferred_location"));
                profile.setPreferredJobType(rs.getString("preferred_job_type"));
                profile.setExpectedSalary(rs.getDouble("expected_salary"));
                profile.setUpdatedAt(rs.getTimestamp("updated_at"));
                return profile;
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public static int saveOrUpdateProfile(UserProfilePojo profile) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBConnection.getConnection();
            // Check if profile exists
            UserProfilePojo existing = getProfileByUserId(profile.getUserId());
            
            if (existing != null) {
                // Update
                String sql = "UPDATE user_profiles SET phone=?, address=?, city=?, state=?, " +
                            "country=?, pincode=?, skills=?, experience=?, education=?, " +
                            "resume_path=?, preferred_location=?, preferred_job_type=?, " +
                            "expected_salary=?, updated_at=NOW() WHERE user_id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, profile.getPhone());
                ps.setString(2, profile.getAddress());
                ps.setString(3, profile.getCity());
                ps.setString(4, profile.getState());
                ps.setString(5, profile.getCountry());
                ps.setString(6, profile.getPincode());
                ps.setString(7, profile.getSkills());
                ps.setString(8, profile.getExperience());
                ps.setString(9, profile.getEducation());
                ps.setString(10, profile.getResumePath());
                ps.setString(11, profile.getPreferredLocation());
                ps.setString(12, profile.getPreferredJobType());
                ps.setDouble(13, profile.getExpectedSalary());
                ps.setInt(14, profile.getUserId());
            } else {
                // Insert
                String sql = "INSERT INTO user_profiles (user_id, phone, address, city, state, " +
                            "country, pincode, skills, experience, education, resume_path, " +
                            "preferred_location, preferred_job_type, expected_salary) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, profile.getUserId());
                ps.setString(2, profile.getPhone());
                ps.setString(3, profile.getAddress());
                ps.setString(4, profile.getCity());
                ps.setString(5, profile.getState());
                ps.setString(6, profile.getCountry());
                ps.setString(7, profile.getPincode());
                ps.setString(8, profile.getSkills());
                ps.setString(9, profile.getExperience());
                ps.setString(10, profile.getEducation());
                ps.setString(11, profile.getResumePath());
                ps.setString(12, profile.getPreferredLocation());
                ps.setString(13, profile.getPreferredJobType());
                ps.setDouble(14, profile.getExpectedSalary());
            }
            return ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
        }
    }
}

