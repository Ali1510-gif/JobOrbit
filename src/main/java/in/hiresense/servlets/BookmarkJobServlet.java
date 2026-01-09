package in.hiresense.servlets;

import java.io.IOException;

import in.hiresense.dao.BookmarkDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bookmarkJob")
public class BookmarkJobServlet extends HttpServlet {
    
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
            int jobId = Integer.parseInt(req.getParameter("jobId"));
            String action = req.getParameter("action"); // "add" or "remove"
            
            boolean success = false;
            if ("add".equals(action)) {
                success = BookmarkDAO.addBookmark(userId, jobId);
            } else if ("remove".equals(action)) {
                success = BookmarkDAO.removeBookmark(userId, jobId);
            }
            
            if (success) {
                res.getWriter().write("{\"success\": true}");
            } else {
                res.getWriter().write("{\"success\": false}");
            }
            res.setContentType("application/json");
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
}

