package in.hiresense.utils;

/**
 * Model to hold match score breakdown details
 */
public class MatchScoreBreakdown {
    private double skillsScore;
    private double experienceScore;
    private double locationScore;
    private double educationScore;
    private double descriptionScore;
    private double totalScore;
    
    public MatchScoreBreakdown() {}
    
    // Getters and Setters
    public double getSkillsScore() { return skillsScore; }
    public void setSkillsScore(double skillsScore) { this.skillsScore = skillsScore; }
    
    public double getExperienceScore() { return experienceScore; }
    public void setExperienceScore(double experienceScore) { this.experienceScore = experienceScore; }
    
    public double getLocationScore() { return locationScore; }
    public void setLocationScore(double locationScore) { this.locationScore = locationScore; }
    
    public double getEducationScore() { return educationScore; }
    public void setEducationScore(double educationScore) { this.educationScore = educationScore; }
    
    public double getDescriptionScore() { return descriptionScore; }
    public void setDescriptionScore(double descriptionScore) { this.descriptionScore = descriptionScore; }
    
    public double getTotalScore() { return totalScore; }
    public void setTotalScore(double totalScore) { this.totalScore = totalScore; }
}

