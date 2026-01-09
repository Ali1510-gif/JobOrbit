package in.hiresense.servlets;

import java.io.IOException;

import in.hiresense.dao.UserProfileDAO;
import in.hiresense.models.UserProfilePojo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {
    
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
            UserProfilePojo profile = UserProfileDAO.getProfileByUserId(userId);
            
            req.setAttribute("profile", profile);
            req.getRequestDispatcher("userProfile.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Error loading profile", e);
        }
    }
    
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
            
            UserProfilePojo profile = new UserProfilePojo();
            profile.setUserId(userId);
            profile.setPhone(req.getParameter("phone"));
            profile.setAddress(req.getParameter("address"));
            profile.setCity(req.getParameter("city"));
            profile.setState(req.getParameter("state"));
            profile.setCountry(req.getParameter("country"));
            profile.setPincode(req.getParameter("pincode"));
            profile.setSkills(req.getParameter("skills"));
            profile.setExperience(req.getParameter("experience"));
            profile.setEducation(req.getParameter("education"));
            profile.setPreferredLocation(req.getParameter("preferredLocation"));
            profile.setPreferredJobType(req.getParameter("preferredJobType"));
            
            String salaryStr = req.getParameter("expectedSalary");
            if (salaryStr != null && !salaryStr.trim().isEmpty()) {
                try {
                    profile.setExpectedSalary(Double.parseDouble(salaryStr));
                } catch (NumberFormatException e) {
                    profile.setExpectedSalary(0.0);
                }
            }
            
            UserProfileDAO.saveOrUpdateProfile(profile);
            
            res.sendRedirect("userProfile?success=true");
        } catch (Exception e) {
            throw new ServletException("Error saving profile", e);
        }
    }
}

