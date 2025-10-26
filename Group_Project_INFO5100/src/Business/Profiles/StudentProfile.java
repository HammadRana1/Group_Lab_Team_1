package Business.Profiles;

import Business.Person.Person;
import Business.University.CourseSchedule.CourseOffer;
import Business.University.CourseCatalog.Course;
import Business.Billing.Account;
import Business.Transcript.TranscriptRecord;

import java.util.ArrayList;
import java.util.List;

//Thevenin_10-25-_"StudentProfile_holds_enrollments_transcript_and_account"
public class StudentProfile extends Profile {

    //Thevenin_10-25-_"Current_enrollments_(offers)_for_all_semesters"
    private final List<CourseOffer> currentEnrollments = new ArrayList<>();

    //Thevenin_10-25-_"Final_grades_records_used_for_GPA_and_audit"
    private final List<TranscriptRecord> transcript = new ArrayList<>();

    //Thevenin_10-25-_"Tuition_balance_and_history_for_transcript_lock"
    private final Account account = new Account();

    public StudentProfile(Person p) { super(p); }

    @Override
    public String getRole() { return "Student"; }

    public boolean isMatch(String id) { return getPerson().getPersonId().equals(id); }

    public List<CourseOffer> getCurrentEnrollments(){ return currentEnrollments; }
    public List<TranscriptRecord> getTranscript(){ return transcript; }
    public Account getAccount(){ return account; }

    //Thevenin_10-25-_"Helpers_for_registration"
    public void enrollIn(CourseOffer offer){ if(!currentEnrollments.contains(offer)) currentEnrollments.add(offer); }
    public void dropFrom(CourseOffer offer){ currentEnrollments.remove(offer); }

    //Thevenin_10-25-_"Sum_credits_for_a_given_semester_string"
    public int termCredits(String semester){
        return currentEnrollments.stream()
                .filter(o -> semester.equalsIgnoreCase(o.getSemester()))
                .mapToInt(o -> o.getCourse().getCredits())
                .sum();
    }

    //Thevenin_10-25-_"Credits_earned_from_transcript_for_graduation_audit"
    public int totalEarnedCredits(){
        return transcript.stream().mapToInt(r -> r.getCourse().getCredits()).sum();
    }
    public boolean hasCompleted(String courseId){
        return transcript.stream().map(TranscriptRecord::getCourse)
                .map(Course::getCourseNumber)
                .anyMatch(id -> id.equalsIgnoreCase(courseId));
    }
}
 