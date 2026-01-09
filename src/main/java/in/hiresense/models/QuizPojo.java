package in.hiresense.models;

import java.util.Date;
import java.util.List;

public class QuizPojo {
    private int id;
    private String title;
    private String description;
    private String category; // e.g., "Technical", "Soft Skills", "Domain Specific"
    private int durationMinutes;
    private int totalQuestions;
    private Date createdAt;
    private String status; // active, inactive
    
    public QuizPojo() {}
    
    public QuizPojo(int id, String title, String description, String category, 
                   int durationMinutes, int totalQuestions, Date createdAt, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.durationMinutes = durationMinutes;
        this.totalQuestions = totalQuestions;
        this.createdAt = createdAt;
        this.status = status;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

