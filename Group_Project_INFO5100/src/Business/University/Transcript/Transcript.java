package Business.University.Transcript;

import java.util.ArrayList;
import java.util.List;
import Business.University.CourseCatalog.Course;
import Business.Profiles.StudentProfile;

public class Transcript {

    private StudentProfile student;
    private List<TranscriptRecord> records;

    public Transcript(StudentProfile sp) {
        this.student = sp;
        this.records = new ArrayList<>();
    }

    public void addRecord(Course course, String grade, String semester) {
        TranscriptRecord record = new TranscriptRecord(course, grade, semester);
        records.add(record);
    }

    public List<TranscriptRecord> getAllRecords() {
        return records;
    }
    
    public List<TranscriptRecord> getTranscriptRecords() {
    return this.records;
}


    public boolean isEmpty() {
        return records.isEmpty();
    }

    public int totalEarnedCredits() {
        int total = 0;
        for (TranscriptRecord record : records) {
            if (!record.getGrade().equalsIgnoreCase("F")) {
                total += record.getCourse().getCredits();
            }
        }
        return total;
    }

    public double calculateGPA() {
        double totalPoints = 0;
        int totalCredits = 0;

        for (TranscriptRecord record : records) {
            double gradePoint = convertGradeToPoint(record.getGrade());
            int credits = record.getCourse().getCredits();
            totalPoints += gradePoint * credits;
            totalCredits += credits;
        }

        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    private double convertGradeToPoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "C-": return 1.7;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }
} 