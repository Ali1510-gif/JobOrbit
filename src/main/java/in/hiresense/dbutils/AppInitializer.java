package in.hiresense.dbutils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppInitializer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();

		Properties props = new Properties();
		try (InputStream input = sc.getResourceAsStream("/WEB-INF/db.properties")) {
			if (input == null) {
				System.out.println(
						"CRITICAL ERROR: /WEB-INF/db.properties not found. Database connection cannot be established.");
				return;
			}
			props.load(input);

			// Database credentials
			String url = props.getProperty("db.url");
			String username = props.getProperty("db.username");
			String password = props.getProperty("db.password");

			// Connect with the database
			DBConnection.openConnection(url, username, password);

			// Set application name
			String appName = props.getProperty("app.name");
			sc.setAttribute("appName", appName);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DBConnection.closeConnection();
	}

}
