package in.hiresense.models;

import java.util.Date;

public class UserProfilePojo {
    private int userId;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String skills; // Comma-separated skills
    private String experience;
    private String education;
    private String resumePath;
    private String preferredLocation;
    private String preferredJobType; // Full-time, Part-time, Contract
    private double expectedSalary;
    private Date updatedAt;
    
    public UserProfilePojo() {}
    
    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    
    public String getResumePath() { return resumePath; }
    public void setResumePath(String resumePath) { this.resumePath = resumePath; }
    
    public String getPreferredLocation() { return preferredLocation; }
    public void setPreferredLocation(String preferredLocation) { this.preferredLocation = preferredLocation; }
    
    public String getPreferredJobType() { return preferredJobType; }
    public void setPreferredJobType(String preferredJobType) { this.preferredJobType = preferredJobType; }
    
    public double getExpectedSalary() { return expectedSalary; }
    public void setExpectedSalary(double expectedSalary) { this.expectedSalary = expectedSalary; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}

