/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.University.Degree;

import Business.Person.Person;
import Business.University.CourseSchedule.CourseLoad;
import Business.University.CourseSchedule.SeatAssignment;  
import Business.University.CourseCatalog.Course;
import Business.Profiles.StudentProfile;
import java.util.ArrayList; 

/**
 *
 * @author Hammad
 */
public class Degree {
    
    String title;
    ArrayList<Course> corelist;
    ArrayList<Course> electives;
    
    public Degree(String name) {
        title = name;
        corelist = new ArrayList<>();  // ✓ Added <>
        electives = new ArrayList<>();  // ✓ Added <>
    }
    
    public void addCoreCourse(Course c) {
        corelist.add(c);
    }
    
    public void addElectiveCourse(Course c) {
        electives.add(c);
    }
    
    public boolean isStudentReadyToGraduate(StudentProfile sp) {
        ArrayList<SeatAssignment> sas = sp.getCourseList(); // ✓ Fixed: added type parameter
        if (validateCoreClasses(sas) == false) {
            return false;
        }
        return true;
    }
    
    public boolean validateCoreClasses(ArrayList<SeatAssignment> sas) {
        for (Course c : corelist) {
            if (!isCoreSatisfied(sas, c)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isCoreSatisfied(ArrayList<SeatAssignment> sas, Course c) {
        for (SeatAssignment sa : sas) {
            if (sa.getAssociatedCourse().equals(c)) {
                return true;
            }
        }
        return false;
    }
    
    public int getTotalElectiveCoursesTaken(ArrayList<SeatAssignment> sas) {
        int electivecount = 0;
        for (SeatAssignment sa : sas) {
            if (isElectiveSatisfied(sa)) {
                electivecount = electivecount + 1;
            }
        }
        return electivecount;
    }
    
    public boolean isElectiveSatisfied(SeatAssignment sa) {
        for (Course c : electives) {
            if (sa.getAssociatedCourse().equals(c)) {
                return true;
            }
        }
        return false;
    }
    
    
}
