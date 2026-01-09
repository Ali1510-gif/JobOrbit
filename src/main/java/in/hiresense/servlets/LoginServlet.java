package in.hiresense.servlets;

import java.io.IOException;

import in.hiresense.dao.UserDAO;
import in.hiresense.models.UserPojo;
import in.hiresense.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		try {
			UserPojo user = UserDAO.getUserByEmail(email);
			if (user != null && "active".equals(user.getStatus())) {
				// Check password - support both hashed (new) and plain text (old) for backward
				// compatibility
				boolean passwordValid = false;
				String storedPassword = user.getPassword();

				if (storedPassword != null && storedPassword.contains(":")) {
					// New format: salt:hash
					passwordValid = PasswordUtil.verifyPasswordFromStored(password, storedPassword);
				} else {
					// Old format: plain text (for backward compatibility)
					passwordValid = storedPassword != null && storedPassword.equals(password);
				}

				if (passwordValid) {
					HttpSession session = req.getSession();
					session.setAttribute("userId", user.getId());
					session.setAttribute("userName", user.getName());
					session.setAttribute("userRole", user.getRole());

					switch (user.getRole()) {
					case "admin":
						res.sendRedirect("adminPanel");
						break;
					case "employer":
						res.sendRedirect("employerDashboard");
						break;
					default:
						res.sendRedirect("userDashboard");
					}
				} else {
					req.setAttribute("error", "Invalid credentials or blocked account.");
					req.getRequestDispatcher("login.jsp").forward(req, res);
				}
			}
		} catch (Exception e) {
			throw new ServletException("Login failed", e);
		}
	}
}