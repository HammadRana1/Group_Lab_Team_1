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
import Business.University.Department.Department;  
import Business.University.CourseSchedule.CourseSchedule;  
import Business.University.CourseSchedule.CourseOffer;  
import Business.University.CourseSchedule.CourseLoad;
import Business.University.CourseCatalog.Course;  
import Business.University.CourseSchedule.Seat; 

/**
 *
 * @author kal bugrara
 */
class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Information Systems");

        // Create Persons
        PersonDirectory persondirectory = business.getPersonDirectory();
        Person person001 = persondirectory.newPerson("John Smith");
        Person person002 = persondirectory.newPerson("Gina Montana");
        Person person003 = persondirectory.newPerson("Adam Rollen");
        Person person004 = persondirectory.newPerson("Dr.Sarah Johnson");
        Person person005 = persondirectory.newPerson("Jim Dellon");
        Person person006 = persondirectory.newPerson("Anna Shnider");
        Person person007 = persondirectory.newPerson("Laura Brown");
        Person person008 = persondirectory.newPerson("Jack While");
        Person person009 = persondirectory.newPerson("Fidelity");

        // Create Admins to manage the business
        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
        EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(person001);
        
        // Create Students - NOW 7 STUDENTS
        StudentDirectory studentdirectory = business.getStudentDirectory();
        StudentProfile studentprofile0 = studentdirectory.newStudentProfile(person003);
        StudentProfile studentprofile1 = studentdirectory.newStudentProfile(person005);
        StudentProfile studentprofile2 = studentdirectory.newStudentProfile(person006);
        StudentProfile studentprofile3 = studentdirectory.newStudentProfile(person007);  // ✓ New
        StudentProfile studentprofile4 = studentdirectory.newStudentProfile(person008);  // ✓ New
        StudentProfile studentprofile5 = studentdirectory.newStudentProfile(person002);  // ✓ New
        StudentProfile studentprofile6 = studentdirectory.newStudentProfile(person009);  // ✓ New
        
        FacultyDirectory facultydirectory = business.getFacultyDirectory();
        FacultyProfile facutlyprofile0 = facultydirectory.newFacultyProfile(person004);
        
        Department department = business.getDepartment();
        
        Course course1 = department.newCourse("INFO 5100", "Application Engineering", 4);
        Course course2 = department.newCourse("INFO 6205", "Data Structures", 4);
        Course course3 = department.newCourse("INFO 6150", "Web Development", 4);
        
        // Create course schedule for Fall2025
        CourseSchedule fall2025 = department.newCourseSchedule("Fall2025");
        
        // Add course offers to the schedule
        CourseOffer offer1 = fall2025.newCourseOffer("INFO 5100");
        if (offer1 != null) {
            offer1.generatSeats(40);
            offer1.AssignAsTeacher(facutlyprofile0);
        }
        
        CourseOffer offer2 = fall2025.newCourseOffer("INFO 6205");
        if (offer2 != null) {
            offer2.generatSeats(35);
            offer2.AssignAsTeacher(facutlyprofile0);
        }
        
        CourseOffer offer3 = fall2025.newCourseOffer("INFO 6150");
        if (offer3 != null) {
            offer3.generatSeats(30);
            offer3.AssignAsTeacher(facutlyprofile0);
        }
        
        // Create course loads for all 7 students
        CourseLoad load0 = studentprofile0.newCourseLoad("Fall2025");
        CourseLoad load1 = studentprofile1.newCourseLoad("Fall2025");
        CourseLoad load2 = studentprofile2.newCourseLoad("Fall2025");
        CourseLoad load3 = studentprofile3.newCourseLoad("Fall2025");  // ✓ New
        CourseLoad load4 = studentprofile4.newCourseLoad("Fall2025");  // ✓ New
        CourseLoad load5 = studentprofile5.newCourseLoad("Fall2025");  // ✓ New
        CourseLoad load6 = studentprofile6.newCourseLoad("Fall2025");  // ✓ New
        
        // Enroll students in courses - NOW WITH 7 STUDENTS
        if (offer1 != null) {
            offer1.assignEmptySeat(load0);
            offer1.assignEmptySeat(load1);
            offer1.assignEmptySeat(load2);
            offer1.assignEmptySeat(load3);  // ✓ New
            offer1.assignEmptySeat(load4);  // ✓ New
            offer1.assignEmptySeat(load5);  // ✓ New
            offer1.assignEmptySeat(load6);  // ✓ New
        }
        
        if (offer2 != null) {
            offer2.assignEmptySeat(load0);
            offer2.assignEmptySeat(load1);
            offer2.assignEmptySeat(load2);  // ✓ New
            offer2.assignEmptySeat(load3);  // ✓ New
            offer2.assignEmptySeat(load5);  // ✓ New
            offer2.assignEmptySeat(load6);  // ✓ New
        }
        
        if (offer3 != null) {
            offer3.assignEmptySeat(load1);
            offer3.assignEmptySeat(load2);
            offer3.assignEmptySeat(load3);  // ✓ New
            offer3.assignEmptySeat(load4);  // ✓ New
            offer3.assignEmptySeat(load5);  // ✓ New
        }

        // Create User accounts that link to specific profiles
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        UserAccount ua3 = uadirectory.newUserAccount(employeeprofile0, "admin", "****");
        UserAccount ua4 = uadirectory.newUserAccount(studentprofile0, "adam", "****");
        UserAccount ua5 = uadirectory.newUserAccount(facutlyprofile0, "faculty", "****");
        
        return business;
    }

}