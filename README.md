# JobOrbit

**JobOrbit** is a comprehensive Job Portal Web Application designed to connect job seekers with potential employers. It features a robust platform for posting jobs, managing applications, and a unique quiz-based skill assessment system to help match candidates with the right opportunities.

## Key Features

### 👤 Job Seekers (Users)
*   **Job Search**: Browse and search for available job openings.
*   **Profile Management**: Create and update professional profiles.
*   **Resume Management**: Upload and manage resumes.
*   **Job Application**: Apply for jobs directly through the portal and track application status.
*   **Skill Quizzes**: Take quizzes to assess skills and find jobs based on quiz results.
*   **Bookmarks**: Save interesting jobs for later.

### 🏢 Employers
*   **Job Posting**: Create and manage job listings.
*   **Applicant Management**: View job applicants, download resumes, and update application statuses (e.g., Shortlisted, Rejected).
*   **Dashboard**: Overview of posted jobs and recent activities.
*   **Profile**: Manage company details.

### 🛠 Admin
*   **User Management**: Block/Unblock users to maintain platform quality.
*   **System Overview**: Access to administrative panels.

## Technologies Used

*   **Backend**: Java (Jakarta EE / Servlets)
*   **Frontend**: JSP (JavaServer Pages), HTML5, CSS3, JavaScript
*   **Database**: MySQL
*   **Build/Dependency**: Standard Web Project Structure

## Installation and Setup

To run this project locally, follow these steps:

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/yourusername/JobOrbit-WebApp.git
    cd JobOrbit-WebApp
    ```

2.  **Database Configuration**
    *   Ensure you have MySQL installed and running.
    *   Create a database named `joborbit`.
    *   Import the database schema (if provided) or ensure the necessary tables are created.

3.  **Configure Application Secrets**
    *   Navigate to `src/main/webapp/WEB-INF/`.
    *   Rename (or copy) `db.properties.example` to `db.properties`.
    *   Open `db.properties` and update the values with your local database credentials:
    ```properties
    db.url=jdbc:mysql://localhost:3306/joborbit
    db.username=YOUR_USERNAME
    db.password=YOUR_PASSWORD
    app.name=JobOrbit
    ```
    *   **Note**: `db.properties` is ignored by Git to keep your secrets safe.

4.  **Deploy**
    *   Import the project into your preferred IDE (Eclipse, IntelliJ IDEA).
    *   Run the project on a Servlet Container like **Apache Tomcat (v9 or later recommended)**.

5.  **Access the Application**
    *   Open your browser and navigate to `http://localhost:8080/JobOrbit` (port may vary based on your Tomcat configuration).


