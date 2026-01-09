package in.hiresense.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import in.hiresense.models.JobPojo;
import in.hiresense.models.QuizResultPojo;

/**
 * Utility class for matching jobs based on quiz results and user skills
 */
public class JobMatchingUtil {
    
    /**
     * Calculate match score for a job based on quiz results and required skills
     */
    public static double calculateMatchScore(JobPojo job, QuizResultPojo quizResult, 
                                            Set<String> userSkills) {
        if (job == null) return 0.0;
        
        double score = 0.0;
        double totalWeight = 0.0;
        
        // 1. Quiz Score Weight (40%)
        if (quizResult != null) {
            double quizWeight = 0.4;
            score += quizResult.getPercentage() * quizWeight;
            totalWeight += quizWeight;
        }
        
        // 2. Skills Match Weight (50%)
        if (job.getSkills() != null && !job.getSkills().trim().isEmpty()) {
            double skillsWeight = 0.5;
            String[] requiredSkills = job.getSkills().toLowerCase().split("[,;]");
            int matchedSkills = 0;
            
            for (String skill : requiredSkills) {
                skill = skill.trim();
                if (userSkills != null) {
                    for (String userSkill : userSkills) {
                        if (userSkill.toLowerCase().contains(skill) || 
                            skill.contains(userSkill.toLowerCase())) {
                            matchedSkills++;
                            break;
                        }
                    }
                }
            }
            
            double skillsMatchPercentage = requiredSkills.length > 0 ? 
                (matchedSkills * 100.0 / requiredSkills.length) : 0;
            score += skillsMatchPercentage * skillsWeight;
            totalWeight += skillsWeight;
        }
        
        // 3. Experience Match Weight (10%)
        // This would require additional logic based on user profile
        // For now, we'll skip this or give a default score
        
        // Normalize score
        if (totalWeight > 0) {
            score = score / totalWeight;
        }
        
        return Math.min(100.0, Math.max(0.0, score));
    }
    
    /**
     * Get skill scores from quiz result JSON string
     */
    public static Map<String, Double> parseSkillScores(String skillScoresJson) {
        Map<String, Double> skillScores = new HashMap<>();
        
        if (skillScoresJson == null || skillScoresJson.trim().isEmpty()) {
            return skillScores;
        }
        
        try {
            // Simple JSON parsing (format: "skill1:score1,skill2:score2")
            String[] pairs = skillScoresJson.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String skill = keyValue[0].trim();
                    double score = Double.parseDouble(keyValue[1].trim());
                    skillScores.put(skill, score);
                }
            }
        } catch (Exception e) {
            // If parsing fails, return empty map
        }
        
        return skillScores;
    }
    
    /**
     * Match jobs based on quiz results and return sorted list
     */
    public static List<JobPojo> matchJobs(List<JobPojo> jobs, QuizResultPojo quizResult, 
                                          Set<String> userSkills) {
        List<JobPojo> matchedJobs = new ArrayList<>();
        
        for (JobPojo job : jobs) {
            double matchScore = calculateMatchScore(job, quizResult, userSkills);
            job.setScore(matchScore);
            matchedJobs.add(job);
        }
        
        // Sort by match score descending
        matchedJobs.sort((j1, j2) -> Double.compare(j2.getScore(), j1.getScore()));
        
        return matchedJobs;
    }
}

