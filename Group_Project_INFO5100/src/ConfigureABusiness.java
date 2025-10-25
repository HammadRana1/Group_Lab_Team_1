/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package Business;

import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;

import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;

import Business.Profiles.FacultyDirectory;
import Business.Profiles.FacultyProfile;

/**
 *
 * @author kal bugrara
 */
class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Information Systems");

// Create Persons
        PersonDirectory persondirectory = business.getPersonDirectory();
// person representing sales organization        
        Person person001 = persondirectory.newPerson("P1", "John Smith", "john.smith@university.edu", "Computer Science", "Faculty");
        Person person002 = persondirectory.newPerson("P2", "Gina Montana", "gina.montana@university.edu", "Finance", "Faculty");
        Person person003 = persondirectory.newPerson("P3", "Adam Rollen", "adam.rollen@university.edu", "Engineering", "Faculty");
        Person person004 = persondirectory.newPerson("P4", "Dr. Sarah Johnson", "sarah.johnson@university.edu", "Medicine", "Faculty");

        Person person005 = persondirectory.newPerson("P5", "Jim Dellon", "jim.dellon@university.edu", "Computer Science", "Student");
        Person person006 = persondirectory.newPerson("P6", "Anna Shnider", "anna.shnider@university.edu", "Mathematics", "Student");
        Person person007 = persondirectory.newPerson("P7", "Laura Brown", "laura.brown@university.edu", "Biology", "Student");
        Person person008 = persondirectory.newPerson("P8", "Jack While", "jack.while@university.edu", "Chemistry", "Student");
        Person person009 = persondirectory.newPerson("P9", "Fidelity", "fidelity@corp.com", "Finance", "Customer");

// Create Admins to manage the business
        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
        EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(person001);

        StudentDirectory studentdirectory = business.getStudentDirectory();
        StudentProfile studentprofile0 = studentdirectory.newStudentProfile(person003);

        FacultyDirectory facultydirectory = business.getFacultyDirectory();
        FacultyProfile facutlyprofile0 = facultydirectory.newFacultyProfile(person004);

// Create User accounts that link to specific profiles
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        UserAccount ua3 = uadirectory.newUserAccount(employeeprofile0, "admin", "****"); /// order products for one of the customers and performed by a sales person
        UserAccount ua4 = uadirectory.newUserAccount(studentprofile0, "adam", "****"); /// order products for one of the customers and performed by a sales person
        UserAccount ua5 = uadirectory.newUserAccount(facutlyprofile0, "faculty", "****"); // Faculty login
        return business;

    }

}
