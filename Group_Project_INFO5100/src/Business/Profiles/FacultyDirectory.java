/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Profiles;
import Business.Business;
import Business.Person.Person;
import java.util.ArrayList;




/**
 *
 * @author Hammad
 */
public class FacultyDirectory {
    
    
  Business business;
 
    ArrayList<FacultyProfile> facultylist;
    public FacultyDirectory(Business d) {
        business = d;
        facultylist = new ArrayList();
    }
    public FacultyProfile newFacultyProfile(Person p) {
        FacultyProfile fp = new FacultyProfile(p);
        facultylist.add(fp);
        return fp;
    }
    public FacultyProfile findFaculty(String id) {
        for (FacultyProfile fp : facultylist) {
            if (fp.isMatch(id)) {
                return fp;
            }
        }
            return null; //not found after going through the whole list
         }
    public ArrayList<FacultyProfile> getFacultyList(){
        return facultylist;
    }  
                  
            
    
}
