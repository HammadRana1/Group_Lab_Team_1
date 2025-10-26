/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.University.CourseCatalog;

/**
 *
 * @author Hammad
 */
public class Course {
    private String courseNumber;
    private String courseName;
    private int credits;

    // Constructor
    public Course(String courseNumber, String courseName, int credits) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.credits = credits;
    }

    // Getters and Setters
    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return courseNumber + " - " + courseName + " (" + credits + " credits)";
    }
    
}
