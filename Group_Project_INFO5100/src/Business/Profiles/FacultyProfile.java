/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;

import Business.Person.Person;
import Business.University.CourseSchedule.CourseOffer;
import Business.Profiles.FacultyAssignment;
import java.util.ArrayList;



/**
 *
 * @author Hammad
 */
public class FacultyProfile extends Profile{
    ArrayList<FacultyAssignment> facultyassignments;

    public FacultyProfile(Person p) {
        super(p);
        facultyassignments = new ArrayList();
    }

    @Override
    public String getRole() {
        return "Faculty";
    }

    // Get all courses assigned to this faculty
    public ArrayList<CourseOffer> getCourseOfferings() {
        ArrayList<CourseOffer> courses = new ArrayList();
        for (FacultyAssignment fa : facultyassignments) {
            courses.add(fa.courseoffer);
        }
        return courses;
    }

    // Assign faculty to a course
    public FacultyAssignment AssignAsTeacher(CourseOffer co) {
        FacultyAssignment fa = new FacultyAssignment(this, co);
        facultyassignments.add(fa);
        return fa;
    }

    public ArrayList<FacultyAssignment> getFacultyAssignments() {
        return facultyassignments;
    }
    
    public double getProfAverageOverallRating(){
        double sum = 0.0;
        for(FacultyAssignment fa: facultyassignments){
            sum = sum + fa.getRating();
        }
        return facultyassignments.size() > 0 ? sum/(facultyassignments.size()*1.0) : 0;
    }
    
    
    
}
