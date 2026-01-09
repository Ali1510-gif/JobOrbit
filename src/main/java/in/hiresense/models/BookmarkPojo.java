package in.hiresense.models;

import java.util.Date;

public class BookmarkPojo {
    private int id;
    private int userId;
    private int jobId;
    private Date createdAt;
    
    public BookmarkPojo() {}
    
    public BookmarkPojo(int id, int userId, int jobId, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}

