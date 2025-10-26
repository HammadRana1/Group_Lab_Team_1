package Business.University.CourseSchedule;

import Business.University.CourseCatalog.Course;

public class CourseOffer {
    private boolean enrollmentOpen = true;
    private int enrolled = 0;
    private int capacity = 30;
    private String facultyName = "TBA";
    private String semester;              // <-- added
    private final Course course;

    public CourseOffer(Course course){ this.course = course; }

    // open/close + seat++/-- you already had:
    public boolean isEnrollmentOpen(){ return enrollmentOpen; }
    public void setEnrollmentOpen(boolean open){ enrollmentOpen = open; }
    public void increment(){ enrolled++; }
    public void decrement(){ if(enrolled>0) enrolled--; }

    // NEW: tie to schedule’s semester
    public void setSemester(String s){ semester = s; }
    public String getSemester(){ return semester; }

    // For table/search/rules:
    public Course getCourse(){ return course; }
    public String getCourseNumber(){ return course.getCourseNumber(); } // adjust if name differs
    public String getCourseName(){ return course.getName(); }           // adjust if name differs
    public int getCapacity(){ return capacity; }
    public void setCapacity(int c){ capacity = Math.max(0,c); }
    public int getEnrolled(){ return enrolled; }
    public String getFacultyName(){ return facultyName; }
    public void setFacultyName(String n){ facultyName = (n==null||n.isBlank()) ? "TBA" : n; }
    public int getTotalCourseRevenues(){ return enrolled * 1000; }
}
