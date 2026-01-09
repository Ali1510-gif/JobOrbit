package in.hiresense.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import in.hiresense.dao.BookmarkDAO;
import in.hiresense.models.JobPojo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bookmarks")
public class BookmarksServlet extends HttpServlet {
    
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
            List<JobPojo> bookmarkedJobs = BookmarkDAO.getBookmarkedJobs(userId);
            Set<Integer> bookmarkedIds = BookmarkDAO.getBookmarkedJobIds(userId);
            
            req.setAttribute("jobs", bookmarkedJobs);
            req.setAttribute("bookmarkedIds", bookmarkedIds);
            req.getRequestDispatcher("bookmarks.jsp").forward(req, res);
        } catch (Exception e) {
            throw new ServletException("Error loading bookmarks", e);
        }
    }
}

