package Business.University.Transcript;

import Business.University.CourseCatalog.Course;

public class TranscriptRecord {
    private Course course;
    private String grade;
    private String semester;

    public TranscriptRecord(Course course, String grade, String semester) {
        this.course = course;
        this.grade = grade;
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public String getGrade() {
        return grade;
    }

    public String getSemester() {
        return semester;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    
}
