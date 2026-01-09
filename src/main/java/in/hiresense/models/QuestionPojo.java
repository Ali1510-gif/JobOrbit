package in.hiresense.models;

import java.util.List;

public class QuestionPojo {
    private int id;
    private int quizId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer; // 1, 2, 3, or 4
    private String skillTag; // Skill this question tests
    private int points;
    
    public QuestionPojo() {}
    
    public QuestionPojo(int id, int quizId, String questionText, String option1, 
                       String option2, String option3, String option4, 
                       int correctAnswer, String skillTag, int points) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
        this.skillTag = skillTag;
        this.points = points;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }
    
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    
    public String getOption1() { return option1; }
    public void setOption1(String option1) { this.option1 = option1; }
    
    public String getOption2() { return option2; }
    public void setOption2(String option2) { this.option2 = option2; }
    
    public String getOption3() { return option3; }
    public void setOption3(String option3) { this.option3 = option3; }
    
    public String getOption4() { return option4; }
    public void setOption4(String option4) { this.option4 = option4; }
    
    public int getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }
    
    public String getSkillTag() { return skillTag; }
    public void setSkillTag(String skillTag) { this.skillTag = skillTag; }
    
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}

