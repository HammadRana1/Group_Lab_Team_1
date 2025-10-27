/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;

import Business.University.CourseSchedule.CourseOffer;

/**
 *
 * @author Hammad
 */
public class FacultyAssignment {
    
     double tracerating;
    public CourseOffer courseoffer;
    FacultyProfile facultyprofile;
    
    public FacultyAssignment(FacultyProfile fp, CourseOffer co){
        courseoffer = co;
        facultyprofile = fp;
    }

    public double getRating(){
        return tracerating;
    }
    
    public void setProfRating(double r){
        tracerating = r;
    }
    
    public FacultyProfile getFacultyProfile(){
        return facultyprofile;
    }
    
    public CourseOffer getCourseOffer(){
        return courseoffer;
    }
    
    
}
