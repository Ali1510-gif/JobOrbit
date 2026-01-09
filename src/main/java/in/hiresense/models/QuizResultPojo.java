package in.hiresense.models;

import java.util.Date;
import java.util.Map;

public class QuizResultPojo {
    private int id;
    private int userId;
    private int quizId;
    private int totalQuestions;
    private int correctAnswers;
    private int totalScore;
    private int maxScore;
    private double percentage;
    private String skillScores; // JSON string of skill:score pairs
    private Date completedAt;
    
    public QuizResultPojo() {}
    
    public QuizResultPojo(int id, int userId, int quizId, int totalQuestions, 
                         int correctAnswers, int totalScore, int maxScore, 
                         double percentage, String skillScores, Date completedAt) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
        this.percentage = percentage;
        this.skillScores = skillScores;
        this.completedAt = completedAt;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }
    
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    
    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }
    
    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }
    
    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }
    
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    
    public String getSkillScores() { return skillScores; }
    public void setSkillScores(String skillScores) { this.skillScores = skillScores; }
    
    public Date getCompletedAt() { return completedAt; }
    public void setCompletedAt(Date completedAt) { this.completedAt = completedAt; }
}

