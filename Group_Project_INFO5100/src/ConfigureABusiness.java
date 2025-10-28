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

        // --- Create Persons ---
        PersonDirectory persondirectory = business.getPersonDirectory();

        // ✅ Admin Person
        Person adminPerson = persondirectory.newPerson("P0", "System Administrator",
                "admin@university.edu", "Administration", "Admin");

        // Faculty
        Person person001 = persondirectory.newPerson("P1", "John Smith",
                "john.smith@university.edu", "Computer Science", "Faculty");
        Person person002 = persondirectory.newPerson("P2", "Gina Montana",
                "gina.montana@university.edu", "Finance", "Faculty");
        Person person003 = persondirectory.newPerson("P3", "Adam Rollen",
                "adam.rollen@university.edu", "Engineering", "Faculty");
        Person person004 = persondirectory.newPerson("P4", "Dr. Sarah Johnson",
                "sarah.johnson@university.edu", "Medicine", "Faculty");

        // Students
        Person person005 = persondirectory.newPerson("P5", "Jim Dellon",
                "jim.dellon@university.edu", "Computer Science", "Student");
        Person person006 = persondirectory.newPerson("P6", "Anna Shnider",
                "anna.shnider@university.edu", "Mathematics", "Student");
        Person person007 = persondirectory.newPerson("P7", "Laura Brown",
                "laura.brown@university.edu", "Biology", "Student");
        Person person008 = persondirectory.newPerson("P8", "Jack While",
                "jack.while@university.edu", "Chemistry", "Student");

        // --- Create Profiles ---
        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
        EmployeeProfile adminProfile = employeedirectory.newEmployeeProfile(adminPerson);  // ✅ Admin linked here

        StudentDirectory studentdirectory = business.getStudentDirectory();
        StudentProfile studentProfile = studentdirectory.newStudentProfile(person005);  // example student

        FacultyDirectory facultydirectory = business.getFacultyDirectory();
        FacultyProfile facultyProfile = facultydirectory.newFacultyProfile(person001); // example faculty

        // --- Create User Accounts ---
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();

        // ✅ Admin login
        UserAccount uaAdmin = uadirectory.newUserAccount(adminProfile, "admin", "****");

        // Faculty login
        UserAccount uaFaculty = uadirectory.newUserAccount(facultyProfile, "faculty", "****");

        // Student login
        UserAccount uaStudent = uadirectory.newUserAccount(studentProfile, "student", "****");

        return business;
    }

}
