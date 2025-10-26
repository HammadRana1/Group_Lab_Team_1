package Business;

import Business.University.CourseCatalog.CourseCatalog;
import Business.University.CourseSchedule.CourseOffer;
import Business.University.CourseSchedule.CourseSchedule;
import Business.Person.Person;
import Business.Person.PersonDirectory;
import Business.Profiles.EmployeeDirectory;
import Business.Profiles.EmployeeProfile;
import Business.Profiles.FacultyDirectory;
import Business.Profiles.FacultyProfile;
import Business.Profiles.StudentDirectory;
import Business.Profiles.StudentProfile;
import Business.UserAccounts.UserAccount;
import Business.UserAccounts.UserAccountDirectory;
import Business.University.Department.Department;


class ConfigureABusiness {

    static Business initialize() {
        Business business = new Business("Information Systems");

        // --- Create Persons ---
        PersonDirectory persondirectory = business.getPersonDirectory();

        Person adminPerson = persondirectory.newPerson("P0", "System Administrator",
                "admin@university.edu", "Administration", "Admin");

        Person person001 = persondirectory.newPerson("P1", "John Smith",
                "john.smith@university.edu", "Computer Science", "Faculty");
        Person person002 = persondirectory.newPerson("P2", "Gina Montana",
                "gina.montana@university.edu", "Finance", "Faculty");
        Person person003 = persondirectory.newPerson("P3", "Adam Rollen",
                "adam.rollen@university.edu", "Engineering", "Faculty");
        Person person004 = persondirectory.newPerson("P4", "Dr. Sarah Johnson",
                "sarah.johnson@university.edu", "Medicine", "Faculty");

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
        EmployeeProfile adminProfile = employeedirectory.newEmployeeProfile(adminPerson);

        StudentDirectory studentdirectory = business.getStudentDirectory();
        StudentProfile studentProfile = studentdirectory.newStudentProfile(person005);

        FacultyDirectory facultydirectory = business.getFacultyDirectory();
        FacultyProfile facultyProfile = facultydirectory.newFacultyProfile(person001);

        // --- Create User Accounts ---
        UserAccountDirectory uadirectory = business.getUserAccountDirectory();
        UserAccount uaAdmin   = uadirectory.newUserAccount(adminProfile,   "admin",   "****");
        UserAccount uaFaculty = uadirectory.newUserAccount(facultyProfile, "faculty", "****");
        UserAccount uaStudent = uadirectory.newUserAccount(studentProfile, "student", "****");

        //Thevenin_10-25-_"Build_and_attach_active_schedule_(Fall_2025)"
        Department csDept = new Department("Computer Science");  
        CourseCatalog cc = new CourseCatalog(csDept);            
        
        cc.newCourse("INFO 5100", "Application Engineering & Development", 4);
        cc.newCourse("INFO 6150", "Web Design & User Experience", 4);

        CourseSchedule fall2025 = new CourseSchedule("Fall 2025", cc);

        CourseOffer o1 = fall2025.newCourseOffer("INFO 5100");
        if (o1 != null) {
            o1.setCapacity(40);
            o1.setFacultyName("TBA");
            o1.setEnrollmentOpen(true);
        }
        CourseOffer o2 = fall2025.newCourseOffer("INFO 6150");
        if (o2 != null) {
            o2.setCapacity(30);
            o2.setFacultyName("TBA");
            o2.setEnrollmentOpen(true);
        }

        business.setActiveStudentSchedule(fall2025);
        business.getDepartmentDirectory().addDepartment(csDept);

        return business;
    }
}
