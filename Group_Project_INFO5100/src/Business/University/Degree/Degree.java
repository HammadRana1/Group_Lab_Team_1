/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.University.Degree;

import Business.University.CourseCatalog.Course;  
import Business.University.CourseSchedule.SeatAssignment;  
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
        corelist = new ArrayList();
        electives = new ArrayList();
    }
    public void addCoreCourse(Course c) {
        corelist.add(c);
    }
    public void addElectiveCourse(Course c) {
        electives.add(c);
    }
    public boolean isStudentReadyToGraduate(StudentProfile sp) {
        //Extract transcript from studentprofile
        //Extract the list of courses taken so far from the student transcript
        //For each core course in the core list of the degree do the following:
        //Check if the core class at hand is in the transcrip
        //Repeat this check for the electives as well
        ArrayList<SeatAssignment> sas = sp.getCourseList(); // CHANGE 1: Add <SeatAssignment> here
        if (validateCoreClasses(sas) == false) {
            return false;
        }//core classes satisfied
        //Do the electives check in case some taken classes are not part of the electives
      
        //Check for the total number of credit hours that it is above 32
        return true; // CHANGE 2: Remove "else" from this line
    }
    public boolean validateCoreClasses(ArrayList<SeatAssignment> sas) {
        //For each core course in the core list of the degree do the following:
        //Check if the core class at hand is in the transcrip
        //Repeat this check for the electives as well
        for (Course c : corelist) {
            if (!isCoreSatisfied(sas, c)) { //if selected core class not on the trascript return false
                return false;
            }
        }
        return true; //all core classes are on the transcript satifying core reqs
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
