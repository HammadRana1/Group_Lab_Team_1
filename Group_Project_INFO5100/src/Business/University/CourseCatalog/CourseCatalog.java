/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.University.CourseCatalog;

import java.util.ArrayList;
import Business.University.Department.Department;
import java.util.ArrayList;

/**
 *
 * @author Hammad
 */
public class CourseCatalog {
    
    Department department;
    String lastupdated;
    ArrayList<Course> courselist;
    
    public CourseCatalog(Department d){
        courselist = new ArrayList();
        department = d;
    }
    
    public ArrayList<Course> getCourseList(){
        return courselist;
    }
    
    public Course newCourse(String n, String nm, int cr){
        Course c = new Course(n, nm, cr);
        courselist.add(c);
        return c;
    }
    
    public Course getCourseByNumber(String n){
        for(Course c: courselist){
            if(c.getCOurseNumber().equals(n)) return c;
        }
        return null;
    }
    
}
