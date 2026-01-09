package in.hiresense.models;

import java.util.List;

/**
 * Model to hold extracted resume data from Affinda API
 */
public class ResumeData {
    private List<String> skills;
    private String summary;
    private String education;
    private String workExperience;
    private Double totalYearsExperience;
    private String location;
    private List<String> certifications;
    private List<String> languages;
    private String rawJson;
    
    public ResumeData() {}
    
    // Getters and Setters
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    
    public String getWorkExperience() { return workExperience; }
    public void setWorkExperience(String workExperience) { this.workExperience = workExperience; }
    
    public Double getTotalYearsExperience() { return totalYearsExperience; }
    public void setTotalYearsExperience(Double totalYearsExperience) { this.totalYearsExperience = totalYearsExperience; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public List<String> getCertifications() { return certifications; }
    public void setCertifications(List<String> certifications) { this.certifications = certifications; }
    
    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }
    
    public String getRawJson() { return rawJson; }
    public void setRawJson(String rawJson) { this.rawJson = rawJson; }
}

