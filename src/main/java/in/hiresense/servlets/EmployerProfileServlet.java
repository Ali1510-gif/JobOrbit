package in.hiresense.servlets;

import java.io.IOException;

import in.hiresense.dao.EmployerProfileDAO;
import in.hiresense.models.EmployerProfilePojo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/employerProfile")
public class EmployerProfileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        if (session.getAttribute("userId") == null || !"employer".equals(session.getAttribute("userRole"))) {
            res.sendRedirect("login.jsp");
            return;
        }
        
        try {
            int userId = (Integer) session.getAttribute("userId");
            EmployerProfilePojo profile = EmployerProfileDAO.getProfileByUserId(userId);
            
            req.setAttribute("profile", profile);
            req.getRequestDispatcher("employerProfile.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Error loading profile", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        if (session.getAttribute("userId") == null || !"employer".equals(session.getAttribute("userRole"))) {
            res.sendRedirect("login.jsp");
            return;
        }
        
        try {
            int userId = (Integer) session.getAttribute("userId");
            
            EmployerProfilePojo profile = new EmployerProfilePojo();
            profile.setUserId(userId);
            profile.setCompanyName(req.getParameter("companyName"));
            profile.setCompanyType(req.getParameter("companyType"));
            profile.setIndustry(req.getParameter("industry"));
            profile.setWebsite(req.getParameter("website"));
            profile.setPhone(req.getParameter("phone"));
            profile.setAddress(req.getParameter("address"));
            profile.setCity(req.getParameter("city"));
            profile.setState(req.getParameter("state"));
            profile.setCountry(req.getParameter("country"));
            profile.setPincode(req.getParameter("pincode"));
            profile.setCompanySize(req.getParameter("companySize"));
            profile.setDescription(req.getParameter("description"));
            
            EmployerProfileDAO.saveOrUpdateProfile(profile);
            
            res.sendRedirect("employerProfile?success=true");
        } catch (Exception e) {
            throw new ServletException("Error saving profile", e);
        }
    }
}

