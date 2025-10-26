<<<<<<< Updated upstream
//Thevenin_10-25-_"Referee_enforces_open_full_8_credit_cap_and_updates_billing"
package Business.Enrollment;

=======
package Business.Enrollment;
>>>>>>> Stashed changes
import Business.University.CourseSchedule.CourseOffer;
import Business.Profiles.StudentProfile;

public class EnrollmentService {
<<<<<<< Updated upstream

    //Thevenin_10-25-_"Check_if_enrollment_is_open_seat_available_and_<=8_credits"
    public boolean canEnroll(StudentProfile s, CourseOffer o) {
        if (!o.isEnrollmentOpen()) return false;
        if (o.getEnrolled() >= o.getCapacity()) return false;
        int newCredits = s.termCredits(/* term */ o.getTerm()) + o.getCourse().getCredits();
        return newCredits <= 8;
    }

    //Thevenin_10-25-_"Enroll_add_to_student_increment_seat_and_charge_tuition"
    public void enroll(StudentProfile s, CourseOffer o) {
        if (!canEnroll(s, o)) throw new IllegalStateException("Cannot enroll");
        s.enrollIn(o);
        o.increment();
        s.getAccount().charge(o.getCourse().getCredits() * 1000.0,
                "Tuition " + o.getCourse().getCourseNumber());
    }

    //Thevenin_10-25-_"Drop_remove_from_student_decrement_seat_and_refund"
    public void drop(StudentProfile s, CourseOffer o) {
        s.dropFrom(o);
        o.decrement();
=======
    //Thevenin_10-25-_"Open? Seat? <=8 credits?"
    public boolean canEnroll(StudentProfile s, CourseOffer o) {
        if (!o.isEnrollmentOpen()) return false;
        if (o.getEnrolled() >= o.getCapacity()) return false;
        int newCredits = s.termCredits(o.getSemester()) + o.getCourse().getCredits();
        return newCredits <= 8;
    }
    //Thevenin_10-25-_"Enroll + charge"
    public void enroll(StudentProfile s, CourseOffer o) {
        if (!canEnroll(s, o)) throw new IllegalStateException("Cannot enroll");
        s.enrollIn(o); o.increment();
        s.getAccount().charge(o.getCourse().getCredits() * 1000.0,
                "Tuition " + o.getCourse().getCourseNumber());
    }
    //Thevenin_10-25-_"Drop + refund"
    public void drop(StudentProfile s, CourseOffer o) {
        s.dropFrom(o); o.decrement();
>>>>>>> Stashed changes
        s.getAccount().refund(o.getCourse().getCredits() * 1000.0,
                "Refund " + o.getCourse().getCourseNumber());
    }
}
