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

        // --- Create Persons with enhanced details ---
        PersonDirectory persondirectory = business.getPersonDirectory();
        
        // Admin Person
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
        Person person009 = persondirectory.newPerson("P9", "Fidelity",
                "fidelity@university.edu", "Business", "Student");

        // --- Create Employee/Admin Profile ---
        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
        EmployeeProfile adminProfile = employeedirectory.newEmployeeProfile(adminPerson);
        
        // --- Create Student Profiles (7 students) ---
        StudentDirectory studentdirectory = business.getStudentDirectory();
        StudentProfile studentprofile0 = studentdirectory.newStudentProfile(person003);
        StudentProfile studentprofile1 = studentdirectory.newStudentProfile(person005);
        StudentProfile studentprofile2 = studentdirectory.newStudentProfile(person006);
        StudentProfile studentprofile3 = studentdirectory.newStudentProfile(person007);
        StudentProfile studentprofile4 = studentdirectory.newStudentProfile(person008);
        StudentProfile studentprofile5 = studentdirectory.newStudentProfile(person002);
        StudentProfile studentprofile6 = studentdirectory.newStudentProfile(person009);
        
        // --- Create Faculty Profile ---
        FacultyDirectory facultydirectory = business.getFacultyDirectory();
        FacultyProfile facultyprofile0 = facultydirectory.newFacultyProfile(person004);
        FacultyProfile facultyprofile1 = facultydirectory.newFacultyProfile(person001);
        
        // --- Create Department and Courses ---
        Department department = business.getDepartment();
        
        Course course1 = department.newCourse("INFO 5100", "Application Engineering", 4);
        Course course2 = department.newCourse("INFO 6205", "Data Structures", 4);
        Course course3 = department.newCourse("INFO 6150", "Web Development", 4);
        
        // --- Create Course Schedule for Fall2025 ---
        CourseSchedule fall2025 = department.newCourseSchedule("Fall2025");
        
        // --- Add Course Offers to the Schedule ---
        CourseOffer offer1 = fall2025.newCourseOffer("INFO 5100");
        if (offer1 != null) {
            offer1.generatSeats(40);
            offer1.AssignAsTeacher(facultyprofile0);
        }
        
        CourseOffer offer2 = fall2025.newCourseOffer("INFO 6205");
        if (offer2 != null) {
            offer2.generatSeats(35);
            offer2.AssignAsTeacher(facultyprofile0);
        }
        
        CourseOffer offer3 = fall2025.newCourseOffer("INFO 6150");
        if (offer3 != null) {
            offer3.generatSeats(30);
            offer3.AssignAsTeacher(facultyprofile1);
        }
        
        // --- Create Course Loads for All 7 Students ---
        CourseLoad load0 = studentprofile0.newCourseLoad("Fall2025");
        CourseLoad load1 = studentprofile1.newCourseLoad("Fall2025");
        CourseLoad load2 = studentprofile2.newCourseLoad("Fall2025");
        CourseLoad load3 = studentprofile3.newCourseLoad("Fall2025");
        CourseLoad load4 = studentprofile4.newCourseLoad("Fall2025");
        CourseLoad load5 = studentprofile5.newCourseLoad("Fall2025");
        CourseLoad load6 = studentprofile6.newCourseLoad("Fall2025");
        
        // --- Enroll Students in Courses ---
        // INFO 5100: All 7 students enrolled
        if (offer1 != null) {
            offer1.assignEmptySeat(load0);
            offer1.assignEmptySeat(load1);
            offer1.assignEmptySeat(load2);
            offer1.assignEmptySeat(load3);
            offer1.assignEmptySeat(load4);
            offer1.assignEmptySeat(load5);
            offer1.assignEmptySeat(load6);
        }
        
        // INFO 6205: 6 students enrolled (excluding load4)
        if (offer2 != null) {
            offer2.assignEmptySeat(load0);
            offer2.assignEmptySeat(load1);
            offer2.assignEmptySeat(load2);
            offer2.assignEmptySeat(load3);
            offer2.assignEmptySeat(load5);
            offer2.assignEmptySeat(load6);
        }
        
        // INFO 6150: 5 students enrolled
        if (offer3 != null) {
            offer3.assignEmptySeat(load1);
            offer3.assignEmptySeat(load2);
            offer3.assignEmptySeat(load3);
            offer3.assignEmptySeat(load4);
            offer3.assignEmptySeat(load5);
        }

        // --- Create User Accounts ---
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        
        // Admin login
        UserAccount uaAdmin = uadirectory.newUserAccount(adminProfile, "admin", "****");
        
        // Student login (Adam Rollen)
        UserAccount uaStudent = uadirectory.newUserAccount(studentprofile0, "adam", "****");
        
        // Faculty login
        UserAccount uaFaculty = uadirectory.newUserAccount(facultyprofile0, "faculty", "****");
        
        return business;
    }

}
