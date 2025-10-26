package Business.University.CourseSchedule;

import Business.University.CourseCatalog.Course;
import Business.University.CourseCatalog.CourseCatalog;
import java.util.ArrayList;

public class CourseSchedule {
    
    ArrayList<CourseOffer> schedule;
    String semester;
    CourseCatalog coursecatalog; //

    public CourseSchedule(String s, CourseCatalog cc) {
        semester = s;
        coursecatalog = cc;
        schedule = new ArrayList<>();
    }

    public CourseOffer newCourseOffer(String n) {
        Course c = coursecatalog.getCourseByNumber(n);
        if (c == null) return null;
        
        CourseOffer co = new CourseOffer(c);
        co.setSemester(semester);
        schedule.add(co);
        return co;
    }

    public CourseOffer getCourseOfferByNumber(String n) {
        for (CourseOffer co : schedule) {
            if (co.getCourseNumber().equals(n)) {
                return co;
            }
        }
        return null;
    }

    public int calculateTotalRevenues() {
        int sum = 0;
        for (CourseOffer co : schedule) {
            sum += co.getTotalCourseRevenues();
        }
        return sum;
    }
}
