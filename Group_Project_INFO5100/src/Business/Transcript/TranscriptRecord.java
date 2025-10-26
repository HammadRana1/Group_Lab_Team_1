/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Transcript;

/**
 *
 * @author syrillthevenin
 */
public class TranscriptRecord {
    private final String semester;
    private final Course course;
    private String letterGrade;
    public TranscriptRecord(String semester, Course course, String grade){
        this.semester=semester; this.course=course; this.letterGrade=grade;
    }
    public String getSemester(){ return semester; }
    public Course getCourse(){ return course; }
    public String getLetterGrade(){ return letterGrade; }
    public void setLetterGrade(String g){ this.letterGrade=g; }
}
