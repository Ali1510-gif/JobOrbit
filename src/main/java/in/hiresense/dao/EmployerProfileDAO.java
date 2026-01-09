package in.hiresense.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import in.hiresense.dbutils.DBConnection;
import in.hiresense.models.EmployerProfilePojo;

public class EmployerProfileDAO {
    
    public static EmployerProfilePojo getProfileByUserId(int userId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM employer_profiles WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                EmployerProfilePojo profile = new EmployerProfilePojo();
                profile.setUserId(rs.getInt("user_id"));
                profile.setCompanyName(rs.getString("company_name"));
                profile.setCompanyType(rs.getString("company_type"));
                profile.setIndustry(rs.getString("industry"));
                profile.setWebsite(rs.getString("website"));
                profile.setPhone(rs.getString("phone"));
                profile.setAddress(rs.getString("address"));
                profile.setCity(rs.getString("city"));
                profile.setState(rs.getString("state"));
                profile.setCountry(rs.getString("country"));
                profile.setPincode(rs.getString("pincode"));
                profile.setCompanySize(rs.getString("company_size"));
                profile.setDescription(rs.getString("description"));
                profile.setLogoPath(rs.getString("logo_path"));
                profile.setUpdatedAt(rs.getTimestamp("updated_at"));
                return profile;
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
    }
    
    public static int saveOrUpdateProfile(EmployerProfilePojo profile) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBConnection.getConnection();
            // Check if profile exists
            EmployerProfilePojo existing = getProfileByUserId(profile.getUserId());
            
            if (existing != null) {
                // Update
                String sql = "UPDATE employer_profiles SET company_name=?, company_type=?, industry=?, " +
                            "website=?, phone=?, address=?, city=?, state=?, country=?, pincode=?, " +
                            "company_size=?, description=?, logo_path=?, updated_at=NOW() WHERE user_id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, profile.getCompanyName());
                ps.setString(2, profile.getCompanyType());
                ps.setString(3, profile.getIndustry());
                ps.setString(4, profile.getWebsite());
                ps.setString(5, profile.getPhone());
                ps.setString(6, profile.getAddress());
                ps.setString(7, profile.getCity());
                ps.setString(8, profile.getState());
                ps.setString(9, profile.getCountry());
                ps.setString(10, profile.getPincode());
                ps.setString(11, profile.getCompanySize());
                ps.setString(12, profile.getDescription());
                ps.setString(13, profile.getLogoPath());
                ps.setInt(14, profile.getUserId());
            } else {
                // Insert
                String sql = "INSERT INTO employer_profiles (user_id, company_name, company_type, industry, " +
                            "website, phone, address, city, state, country, pincode, company_size, description, logo_path) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, profile.getUserId());
                ps.setString(2, profile.getCompanyName());
                ps.setString(3, profile.getCompanyType());
                ps.setString(4, profile.getIndustry());
                ps.setString(5, profile.getWebsite());
                ps.setString(6, profile.getPhone());
                ps.setString(7, profile.getAddress());
                ps.setString(8, profile.getCity());
                ps.setString(9, profile.getState());
                ps.setString(10, profile.getCountry());
                ps.setString(11, profile.getPincode());
                ps.setString(12, profile.getCompanySize());
                ps.setString(13, profile.getDescription());
                ps.setString(14, profile.getLogoPath());
            }
            return ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
        }
    }
}

