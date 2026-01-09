package in.hiresense.utils;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class MailUtil {

    public static void sendTextEmail(String toEmail, String subject, String body) throws MessagingException {
        Session session = MailConfig.getSession();

        Message msg = new MimeMessage(session);
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(subject);
        msg.setText(body);

        Transport.send(msg);
    }

    public static void sendApplicationConfirmation(String name,String toEmail, String jobTitle, String company) throws MessagingException {
        String subject = "✅ Application Submitted - " + jobTitle;
        String body = "Dear "+name+",\n\n"
                    + "You have successfully applied for the position of " + jobTitle + " at " + company + ".\n"
                    + "We wish you the best of luck!\n\n"
                    + "Regards,\nJobOrbit Team";
        sendTextEmail(toEmail, subject, body);
    }

    public static void sendApplicationStatusUpdate(String name, String toEmail, String jobTitle, String company, String status) throws MessagingException {
        String subject;
        String body;

        if ("shortlisted".equalsIgnoreCase(status) || "accepted".equalsIgnoreCase(status)) {
            subject = "🎉 Congratulations! Application Accepted - " + jobTitle;
            body = "Dear " + name + ",\n\n"
                 + "We are thrilled to inform you that your application for the position of " + jobTitle + " at " + company + " has been ACCEPTED!\n\n"
                 + "Congratulations on this achievement! The employer has reviewed your profile and is interested in moving forward with your candidacy.\n\n"
                 + "Next Steps:\n"
                 + "• The employer may contact you directly via email or phone for further discussions\n"
                 + "• Be prepared for potential interviews or additional assessments\n"
                 + "• Keep your profile updated on JobOrbit\n\n"
                 + "We wish you the very best in your career journey!\n\n"
                 + "Best regards,\n"
                 + "JobOrbit Team\n\n"
                 + "---\n"
                 + "This is an automated notification from JobOrbit. Please do not reply to this email.";
        } else if ("rejected".equalsIgnoreCase(status)) {
            subject = "Application Update - " + jobTitle;
            body = "Dear " + name + ",\n\n"
                 + "Thank you for your interest in the position of " + jobTitle + " at " + company + ".\n\n"
                 + "After careful consideration, we regret to inform you that your application was not selected for this position at this time.\n\n"
                 + "This decision does not reflect on your qualifications or potential. We encourage you to:\n"
                 + "• Continue exploring other opportunities on JobOrbit\n"
                 + "• Update your profile and skills to improve your match score\n"
                 + "• Apply for positions that align with your experience and interests\n\n"
                 + "We wish you success in your job search and hope you find the perfect opportunity soon.\n\n"
                 + "Best regards,\n"
                 + "JobOrbit Team\n\n"
                 + "---\n"
                 + "This is an automated notification from JobOrbit. Please do not reply to this email.";
        } else {
            subject = "📢 Application Status Update - " + jobTitle;
            body = "Dear " + name + ",\n\n"
                 + "Your application status for the position of " + jobTitle + " at " + company + " has been updated to: " + status + ".\n\n"
                 + "Please log in to your JobOrbit account to view more details about your application.\n\n"
                 + "Best regards,\n"
                 + "JobOrbit Team\n\n"
                 + "---\n"
                 + "This is an automated notification from JobOrbit. Please do not reply to this email.";
        }

        sendTextEmail(toEmail, subject, body);
    }

    public static void sendAccountActionNotice(String toEmail, String userName, String action) throws MessagingException {
        String subject = "⚠️ Account Update - JobOrbit";
        String body;

        switch (action.toLowerCase()) {
            case "blocked":
                body = "Hello " + userName + ",\n\n"
                     + "Your account on JobOrbit has been *blocked* due to policy violations or suspicious activity.\n"
                     + "Please contact support for more details.\n\n"
                     + "Regards,\nAdmin Team";
                break;

            case "unblocked":
                body = "Hello " + userName + ",\n\n"
                     + "Your account has been *unblocked* and you may now access all services again.\n"
                     + "Thank you for your patience.\n\n"
                     + "Regards,\nAdmin Team";
                break;

            case "removed":
                body = "Hello " + userName + ",\n\n"
                     + "Your account has been *permanently removed* from JobOrbit by the administrator.\n"
                     + "If you believe this is a mistake, please contact support.\n\n"
                     + "Regards,\nAdmin Team";
                break;

            default:
                body = "Hello " + userName + ",\n\n"
                     + "There has been an update to your JobOrbit account: *" + action + "*.\n\n"
                     + "Regards,\nAdmin Team";
                break;
        }

        sendTextEmail(toEmail, subject, body);
    }

    public static void sendNewApplicationNotificationToEmployer(String employerName, String toEmail, String applicantName, String jobTitle) throws MessagingException  {

                String subject = "New Application Received";
                String message = "Dear " + employerName + ",\n\n"
                        + "A new candidate (" + applicantName + ") has applied for your job posting: \"" + jobTitle + "\".\n"
                        + "You can review the applicant’s details in your dashboard.\n\n"
                        + "Regards,\nJobOrbit Team";

                MailUtil.sendTextEmail(toEmail, subject, message);
    }
}