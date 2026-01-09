package in.hiresense.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import in.hiresense.models.JobPojo;
import in.hiresense.models.ResumeData;
import in.hiresense.utils.MatchScoreBreakdown;

public class AffindaAPI {
    private static final String API_KEY = "aff_f1f814bbb0bfea6cfe31ff1aed91e7fa5ff98a66";

    public static String analyzeResume(File resumeFile) throws IOException {
        String boundary = "----WebKitFormBoundary" + UUID.randomUUID();
        String LINE_FEED = "\r\n";

        URL url = new URL("https://api.affinda.com/v2/resumes");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        connection.setDoOutput(true);

        try (OutputStream output = connection.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true)) {

            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + resumeFile.getName() + "\"")
                  .append(LINE_FEED);
            writer.append("Content-Type: application/pdf").append(LINE_FEED);
            writer.append(LINE_FEED).flush();

            Files.copy(resumeFile.toPath(), output);
            output.flush();

            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.flush();
        }

        InputStream responseStream = connection.getResponseCode() == 200
                ? connection.getInputStream()
                : connection.getErrorStream();

        return new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    // ✅ Extract skills from Affinda JSON result
    public static List<String> extractSkills(String resultJson) {
        List<String> skills = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(resultJson);
            JSONObject data = result.getJSONObject("data");
            JSONArray skillArray = data.optJSONArray("skills");
            if (skillArray != null) {
                for (int i = 0; i < skillArray.length(); i++) {
                    JSONObject skillObj = skillArray.getJSONObject(i);
                    String name = skillObj.optString("name");
                    if (name != null && !name.isEmpty()) {
                        skills.add(name.trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skills;
    }

 // ✅ Extract summary from Affinda JSON result
    public static String extractSummary(String resultJson) {
    	String summary=null;
        try {
            JSONObject result = new JSONObject(resultJson);
            JSONObject data = result.getJSONObject("data");
            JSONArray summaryArr = data.optJSONArray("sections");
            if (summaryArr != null) {
                for (int i = 0; i < summaryArr.length(); i++) {
                    JSONObject summaryObj = summaryArr.getJSONObject(i);
                    String sectionType = summaryObj.optString("sectionType");
                    if(sectionType.equalsIgnoreCase("Summary")) {
                    	summary = summaryObj.optString("text");
                    	break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summary;
    }

    // ✅ Extract personal details from Affinda JSON result
    public static String extractPersonalDetails(String resultJson) {
        String personalDetails=null;
        try {
            JSONObject result = new JSONObject(resultJson);
            JSONObject data = result.getJSONObject("data");
            JSONArray sectionArr = data.optJSONArray("sections");
            if (sectionArr != null) {
                for (int i = 0; i < sectionArr.length(); i++) {
                    JSONObject sectionObj = sectionArr.getJSONObject(i);
                    String sectionType = sectionObj.optString("sectionType");
                    if(sectionType.equalsIgnoreCase("PersonalDetails")) {
                    	personalDetails = sectionObj.optString("text");
                    	break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personalDetails;
    }

 // ✅ Extract education from Affinda JSON result
    public static String extractEducation(String resultJson) {
        String education=null;
        try {
            JSONObject result = new JSONObject(resultJson);
            JSONObject data = result.getJSONObject("data");
            JSONArray educationArr = data.optJSONArray("sections");
            if (educationArr != null) {
                for (int i = 0; i < educationArr.length(); i++) {
                    JSONObject educationObj = educationArr.getJSONObject(i);
                    String sectionType = educationObj.optString("sectionType");
                    if(sectionType.equalsIgnoreCase("Education")) {
                    	education = educationObj.optString("text");
                    	break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return education;
    }

 // ✅ Extract work experience from Affinda JSON result
    public static String extracteWorkExperience(String resultJson) {
        String experience=null;
        String totalExperience=null;
        try {
            JSONObject result = new JSONObject(resultJson);
            JSONObject data = result.getJSONObject("data");
            JSONArray experienceArr = data.optJSONArray("sections");
            totalExperience = data.optString("totalYearsExperience");            if (experienceArr != null) {
                for (int i = 0; i < experienceArr.length(); i++) {
                    JSONObject experienceObj = experienceArr.getJSONObject(i);
                    String sectionType = experienceObj.optString("sectionType");
                    if(sectionType.equalsIgnoreCase("WorkExperience")) {
                    	experience = experienceObj.optString("text");
                    	break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Total Experience : "+totalExperience+"\n"+experience;
    }

    // ✅ Extract comprehensive resume data from Affinda JSON
    public static ResumeData extractResumeData(String resultJson) {
        ResumeData resumeData = new ResumeData();
        try {
            JSONObject result = new JSONObject(resultJson);
            JSONObject data = result.getJSONObject("data");
            
            // Extract skills
            List<String> skills = extractSkills(resultJson);
            resumeData.setSkills(skills);
            
            // Extract summary
            resumeData.setSummary(extractSummary(resultJson));
            
            // Extract education
            resumeData.setEducation(extractEducation(resultJson));
            
            // Extract work experience
            resumeData.setWorkExperience(extracteWorkExperience(resultJson));
            
            // Extract total years of experience
            try {
                String expStr = data.optString("totalYearsExperience");
                if (expStr != null && !expStr.isEmpty()) {
                    resumeData.setTotalYearsExperience(Double.parseDouble(expStr));
                }
            } catch (Exception e) {
                // Ignore parsing errors
            }
            
            // Extract location
            try {
                JSONObject locationObj = data.optJSONObject("location");
                if (locationObj != null) {
                    String city = locationObj.optString("city");
                    String country = locationObj.optString("country");
                    if (city != null && !city.isEmpty()) {
                        resumeData.setLocation(city + (country != null ? ", " + country : ""));
                    }
                }
            } catch (Exception e) {
                // Ignore errors
            }
            
            // Extract certifications
            List<String> certifications = new ArrayList<>();
            try {
                JSONArray certArray = data.optJSONArray("certifications");
                if (certArray != null) {
                    for (int i = 0; i < certArray.length(); i++) {
                        JSONObject cert = certArray.getJSONObject(i);
                        String name = cert.optString("name");
                        if (name != null && !name.isEmpty()) {
                            certifications.add(name);
                        }
                    }
                }
            } catch (Exception e) {
                // Ignore errors
            }
            resumeData.setCertifications(certifications);
            
            // Extract languages
            List<String> languages = new ArrayList<>();
            try {
                JSONArray langArray = data.optJSONArray("languages");
                if (langArray != null) {
                    for (int i = 0; i < langArray.length(); i++) {
                        JSONObject lang = langArray.getJSONObject(i);
                        String name = lang.optString("name");
                        if (name != null && !name.isEmpty()) {
                            languages.add(name);
                        }
                    }
                }
            } catch (Exception e) {
                // Ignore errors
            }
            resumeData.setLanguages(languages);
            
            resumeData.setRawJson(resultJson);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resumeData;
    }
    
    // ✅ Extract experience level from years of experience
    public static String getExperienceLevel(Double years) {
        if (years == null) return "Unknown";
        if (years == 0) return "Fresher";
        if (years <= 1) return "0 - 1 year";
        if (years <= 2) return "1 - 2 years";
        if (years <= 3) return "2 - 3 years";
        if (years <= 5) return "3 - 5 years";
        return "5+ years";
    }
    
    // ✅ Calculate comprehensive match score using all Affinda data
    public static double calculateComprehensiveMatchScore(JobPojo job, ResumeData resumeData) {
        MatchScoreBreakdown breakdown = calculateComprehensiveMatchScoreWithBreakdown(job, resumeData);
        return breakdown != null ? breakdown.getTotalScore() : 0.0;
    }
    
    // ✅ Calculate comprehensive match score with detailed breakdown
    public static MatchScoreBreakdown calculateComprehensiveMatchScoreWithBreakdown(JobPojo job, ResumeData resumeData) {
        if (job == null || resumeData == null) {
            return null;
        }
        
        MatchScoreBreakdown breakdown = new MatchScoreBreakdown();
        double totalScore = 0.0;
        double totalWeight = 0.0;
        
        // 1. Skills Match (40% weight) - Most important
        double skillsScore = calculateSkillsMatchScore(job.getSkills(), resumeData.getSkills());
        breakdown.setSkillsScore(skillsScore);
        totalScore += skillsScore * 0.40;
        totalWeight += 0.40;
        
        // 2. Experience Match (25% weight)
        double experienceScore = calculateExperienceMatch(job.getExperience(), resumeData.getTotalYearsExperience());
        breakdown.setExperienceScore(experienceScore);
        totalScore += experienceScore * 0.25;
        totalWeight += 0.25;
        
        // 3. Location Match (15% weight)
        double locationScore = calculateLocationMatch(job.getLocation(), resumeData.getLocation());
        breakdown.setLocationScore(locationScore);
        totalScore += locationScore * 0.15;
        totalWeight += 0.15;
        
        // 4. Education Match (10% weight) - Check if education keywords match
        double educationScore = calculateEducationMatch(job.getDescription(), resumeData.getEducation());
        breakdown.setEducationScore(educationScore);
        totalScore += educationScore * 0.10;
        totalWeight += 0.10;
        
        // 5. Summary/Description Match (10% weight) - Check if job description keywords match resume summary
        double descriptionScore = calculateDescriptionMatch(job.getDescription(), resumeData.getSummary());
        breakdown.setDescriptionScore(descriptionScore);
        totalScore += descriptionScore * 0.10;
        totalWeight += 0.10;
        
        // Normalize score
        if (totalWeight > 0) {
            totalScore = totalScore / totalWeight;
        }
        
        breakdown.setTotalScore(Math.min(100.0, Math.max(0.0, totalScore)));
        return breakdown;
    }
    
    // ✅ Calculate skills match score (improved with fuzzy matching)
    private static double calculateSkillsMatchScore(String jobSkillsCsv, List<String> resumeSkills) {
        if (jobSkillsCsv == null || jobSkillsCsv.trim().isEmpty()) {
            return 50.0; // If no skills specified, give neutral score
        }
        
        if (resumeSkills == null || resumeSkills.isEmpty()) {
            return 0.0;
		}

        String[] jobSkills = jobSkillsCsv.split("[,;]");
        Set<String> required = new HashSet<>();
        for (String js : jobSkills) {
            String skill = js.trim().toLowerCase();
            if (!skill.isEmpty()) {
                required.add(skill);
            }
        }
        
        if (required.isEmpty()) {
            return 50.0;
        }
        
        int exactMatches = 0;
        int partialMatches = 0;
        
        for (String resumeSkill : resumeSkills) {
            String rs = resumeSkill.toLowerCase().trim();
            if (required.contains(rs)) {
                exactMatches++;
            } else {
                // Fuzzy matching - check if skill contains or is contained in required skills
                for (String reqSkill : required) {
                    if (rs.contains(reqSkill) || reqSkill.contains(rs)) {
                        partialMatches++;
                        break;
                    }
                }
            }
        }
        
        // Exact matches count fully, partial matches count as 0.5
        double matched = exactMatches + (partialMatches * 0.5);
        return (matched * 100.0) / required.size();
    }
    
    // ✅ Calculate experience match score
    private static double calculateExperienceMatch(String jobExperience, Double resumeYears) {
        if (jobExperience == null || jobExperience.trim().isEmpty()) {
            return 50.0; // Neutral if not specified
        }
        
        if (resumeYears == null) {
            return 0.0;
        }
        
        String jobExp = jobExperience.toLowerCase().trim();
        
        // Parse job experience requirement
        if (jobExp.contains("fresher") || jobExp.contains("0")) {
            return resumeYears <= 1 ? 100.0 : Math.max(0, 100 - (resumeYears * 20));
        } else if (jobExp.contains("0 - 1") || jobExp.contains("0-1")) {
            return resumeYears >= 0 && resumeYears <= 1 ? 100.0 : 
                   resumeYears < 0 ? 50.0 : Math.max(0, 100 - ((resumeYears - 1) * 30));
        } else if (jobExp.contains("1 - 2") || jobExp.contains("1-2")) {
            return resumeYears >= 1 && resumeYears <= 2 ? 100.0 : 
                   resumeYears < 1 ? 70.0 : Math.max(0, 100 - ((resumeYears - 2) * 25));
        } else if (jobExp.contains("2 - 3") || jobExp.contains("2-3")) {
            return resumeYears >= 2 && resumeYears <= 3 ? 100.0 : 
                   resumeYears < 2 ? 60.0 : Math.max(0, 100 - ((resumeYears - 3) * 20));
        } else if (jobExp.contains("3 - 5") || jobExp.contains("3-5")) {
            return resumeYears >= 3 && resumeYears <= 5 ? 100.0 : 
                   resumeYears < 3 ? 50.0 : Math.max(0, 100 - ((resumeYears - 5) * 15));
        } else if (jobExp.contains("5+") || jobExp.contains("5 +")) {
            return resumeYears >= 5 ? 100.0 : Math.max(0, resumeYears * 20);
        }
        
        return 50.0; // Default neutral score
    }
    
    // ✅ Calculate location match score
    private static double calculateLocationMatch(String jobLocation, String resumeLocation) {
        if (jobLocation == null || jobLocation.trim().isEmpty()) {
            return 50.0; // Neutral if not specified
        }
        
        if (resumeLocation == null || resumeLocation.trim().isEmpty()) {
            return 30.0; // Slight penalty for missing location
        }
        
        String jobLoc = jobLocation.toLowerCase().trim();
        String resumeLoc = resumeLocation.toLowerCase().trim();
        
        // Exact match
        if (jobLoc.equals(resumeLoc)) {
            return 100.0;
        }
        
        // Contains match (e.g., "Bangalore" matches "Bangalore, India")
        if (jobLoc.contains(resumeLoc) || resumeLoc.contains(jobLoc)) {
            return 80.0;
        }
        
        // Check for remote
        if (jobLoc.contains("remote") || resumeLoc.contains("remote")) {
            return 100.0; // Remote matches everything
        }
        
        return 20.0; // Low score for different locations
    }
    
    // ✅ Calculate education match score
    private static double calculateEducationMatch(String jobDescription, String resumeEducation) {
        if (jobDescription == null || jobDescription.trim().isEmpty()) {
            return 50.0;
        }
        
        if (resumeEducation == null || resumeEducation.trim().isEmpty()) {
            return 30.0;
        }
        
        String desc = jobDescription.toLowerCase();
        String edu = resumeEducation.toLowerCase();
        
        // Check for common education keywords
        String[] eduKeywords = {"bachelor", "btech", "b.tech", "b.e", "be", "degree", 
                               "master", "mtech", "m.tech", "mba", "phd", "diploma", 
                               "engineering", "computer science", "cs", "it"};
        
        int matches = 0;
        for (String keyword : eduKeywords) {
            if (desc.contains(keyword) && edu.contains(keyword)) {
                matches++;
            }
        }
        
        return Math.min(100.0, matches * 20.0);
    }
    
    // ✅ Calculate description/summary match score
    private static double calculateDescriptionMatch(String jobDescription, String resumeSummary) {
        if (jobDescription == null || jobDescription.trim().isEmpty()) {
            return 50.0;
        }
        
        if (resumeSummary == null || resumeSummary.trim().isEmpty()) {
            return 30.0;
        }
        
        // Extract keywords from job description
        String[] descWords = jobDescription.toLowerCase().split("\\s+");
        Set<String> descKeywords = new HashSet<>();
        for (String word : descWords) {
            if (word.length() > 4) { // Only consider words longer than 4 characters
                descKeywords.add(word);
            }
        }
        
        // Check how many keywords appear in resume summary
        String summary = resumeSummary.toLowerCase();
        int matches = 0;
        for (String keyword : descKeywords) {
            if (summary.contains(keyword)) {
                matches++;
            }
        }
        
        if (descKeywords.isEmpty()) {
            return 50.0;
        }
        
        return Math.min(100.0, (matches * 100.0) / descKeywords.size());
    }
    
    // ✅ Legacy method for backward compatibility
    public static int calculateMatchScore(String jobSkillsCsv, List<String> resumeSkills) {
        return (int) calculateSkillsMatchScore(jobSkillsCsv, resumeSkills);
    }
}
