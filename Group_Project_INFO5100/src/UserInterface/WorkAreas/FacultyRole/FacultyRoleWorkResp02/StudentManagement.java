/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.WorkAreas.FacultyRole.FacultyRoleWorkResp02;
import Business.Business;
import Business.Profiles.FacultyProfile;
import Business.Profiles.StudentProfile;
import Business.Profiles.StudentDirectory;
import Business.University.Department.Department;
import Business.University.Department.Department;
import Business.University.CourseSchedule.CourseOffer;
import Business.University.CourseSchedule.CourseLoad;
import Business.University.CourseSchedule.CourseSchedule;
import Business.University.CourseSchedule.Seat;
import Business.University.CourseSchedule.SeatAssignment;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Hammad
 */
public class StudentManagement extends javax.swing.JPanel {

    /**
     * Creates new form StudentManagement
     */
    
private JPanel userProcessContainer;
private FacultyProfile facultyProfile;
private Business business;
    public StudentManagement(JPanel userProcessContainer, FacultyProfile fp, Business bz) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.facultyProfile = fp;
        this.business = bz;
        
        loadFacultyCourses();
        setupFilterOptions();
        jComboCourseSelection.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        populateTable();
    }
});

jComboFilterResults.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        applyFilter();
    }
});
    }

    
    private void loadFacultyCourses() {
  jComboCourseSelection.removeAllItems();
    jComboCourseSelection.addItem("-- Select Course --");
    
    // Get courses assigned to this faculty member
    ArrayList<CourseOffer> courses = facultyProfile.getCourseOfferings();
    
    // Add faculty courses
    for (CourseOffer co : courses) {
        String courseInfo = co.getCourseNumber();
        jComboCourseSelection.addItem(courseInfo);
    }
    
    // FOR TESTING: Add some manual courses if no courses found
    if (courses.isEmpty()) {
        jComboCourseSelection.addItem("INFO 5100");
        jComboCourseSelection.addItem("INFO 6205");
        jComboCourseSelection.addItem("INFO 6150");
    }
}
    
   private void setupFilterOptions() {
    jComboFilterResults.removeAllItems();  // Changed from jComboBoxFilterResults
    jComboFilterResults.addItem("-- No Filter --");
    jComboFilterResults.addItem("Sort by Total Grade (High to Low)");
    jComboFilterResults.addItem("Sort by Total Grade (Low to High)");
    jComboFilterResults.addItem("Sort by Letter Grade (A to F)");
    jComboFilterResults.addItem("Sort by Rank (1 to Last)");
    jComboFilterResults.addItem("Sort by GPA (High to Low)");
} 
    
    
    
   
private void populateTable() {
     DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);
    
    String selectedCourse = (String) jComboCourseSelection.getSelectedItem();
    
    if (selectedCourse == null || selectedCourse.equals("-- Select Course --")) {
        return;
    }
    
    try {
        Department dept = business.getDepartment();
        CourseSchedule schedule = dept.getCourseSchedule("Fall2025");
        
        if (schedule == null) {
            JOptionPane.showMessageDialog(this, 
                "No course schedule found for Fall2025!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        CourseOffer courseOffer = schedule.getCourseOfferByNumber(selectedCourse);
        
        if (courseOffer == null) {
            JOptionPane.showMessageDialog(this, 
                "Course " + selectedCourse + " not found in schedule!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ArrayList<Seat> seats = courseOffer.seatlist;
        
        if (seats == null || seats.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No seats available for " + selectedCourse, 
                "No Seats", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        ArrayList<StudentGradeData> studentGrades = new ArrayList<>();
        
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                SeatAssignment sa = seat.seatassignment;
                
                if (sa != null) {
                    CourseLoad courseLoad = sa.courseload;
                    StudentProfile student = findStudentByCourseLoad(courseLoad);
                    
                    if (student != null) {
                        String studentId = "STU-" + String.format("%03d", Math.abs(student.getPerson().getPersonId().hashCode()) % 1000);
                        String studentName = student.getPerson().getPersonId();
                        
                        float gradeScore = sa.grade > 0 ? sa.grade * 25 : (float)(Math.random() * 40 + 60);
                        String letterGrade = calculateLetterGrade(gradeScore);
                        double gpa = calculateGPA(letterGrade);
                        
                        studentGrades.add(new StudentGradeData(
                            studentId, studentName, selectedCourse, gradeScore, letterGrade, gpa
                        ));
                    }
                }
            }
        }
        
        if (studentGrades.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No students are currently enrolled in " + selectedCourse + ".\n\n" +
                "To see students, add enrollments in ConfigureABusiness.java", 
                "No Enrollments", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        studentGrades.sort((a, b) -> Float.compare(b.totalGrade, a.totalGrade));
        
        int rank = 1;
        for (StudentGradeData sgd : studentGrades) {
            Object[] row = new Object[7];
            row[0] = sgd.studentId;
            row[1] = sgd.studentName;
            row[2] = sgd.course;
            row[3] = Math.round(sgd.totalGrade);
            row[4] = sgd.letterGrade;
            row[5] = rank++;
            row[6] = String.format("%.2f", sgd.gpa);
            
            model.addRow(row);
        }
        
        double classGPA = studentGrades.stream()
            .mapToDouble(s -> s.gpa)
            .average()
            .orElse(0.0);
        
        System.out.println("Loaded " + studentGrades.size() + " students for " + selectedCourse);
        System.out.println("Class GPA: " + String.format("%.2f", classGPA));
        
        jComboFilterResults.setSelectedIndex(0);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading students: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    
 
  
  
  private void refreshCourses() {
    loadFacultyCourses();
}
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblStudentManagement = new javax.swing.JLabel();
        lblSelectCourse = new javax.swing.JLabel();
        jComboCourseSelection = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnViewStudents = new javax.swing.JButton();
        btnViewTranscript = new javax.swing.JButton();
        btnGradeAssignment = new javax.swing.JButton();
        btnCalculateGrade = new javax.swing.JButton();
        btnViewProgress = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblFilterResults = new javax.swing.JLabel();
        jComboFilterResults = new javax.swing.JComboBox<>();
        btnAddStudent = new javax.swing.JButton();
        btnDeleteStudent = new javax.swing.JButton();

        lblStudentManagement.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblStudentManagement.setText("Student Management ");

        lblSelectCourse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSelectCourse.setText("Select Course");

        jComboCourseSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Course", "Total Grade %", "Letter Grade", "Rank", "GPA"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnViewStudents.setText("View Students");
        btnViewStudents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewStudentsActionPerformed(evt);
            }
        });

        btnViewTranscript.setText("View Transcript");

        btnGradeAssignment.setText("Grade Assignment");

        btnCalculateGrade.setText("Calculate Grade");

        btnViewProgress.setText("View Progress Report");

        btnLogout.setText("Logout");

        btnBack.setText("<<Back");

        lblFilterResults.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFilterResults.setText("Filter Results");

        jComboFilterResults.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddStudent.setText("Add Student");

        btnDeleteStudent.setText("Delete Student");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblSelectCourse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboCourseSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125)
                        .addComponent(lblFilterResults)
                        .addGap(18, 18, 18)
                        .addComponent(jComboFilterResults, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStudentManagement)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnAddStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnViewStudents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnViewTranscript, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(btnDeleteStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGradeAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCalculateGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnViewProgress)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStudentManagement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectCourse)
                    .addComponent(jComboCourseSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFilterResults)
                    .addComponent(jComboFilterResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewStudents)
                    .addComponent(btnViewTranscript)
                    .addComponent(btnGradeAssignment)
                    .addComponent(btnCalculateGrade)
                    .addComponent(btnViewProgress))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddStudent)
                    .addComponent(btnDeleteStudent))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogout)
                    .addComponent(btnBack))
                .addContainerGap(76, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewStudentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewStudentsActionPerformed
        
// TODO add your handling code here:
       String selectedCourse = (String) jComboCourseSelection.getSelectedItem();
    
    if (selectedCourse == null || selectedCourse.equals("-- Select Course --")) {
        JOptionPane.showMessageDialog(this, "Please select a course first!");
        return;
    }
    
    // Load students into the table (stays on same screen)
    populateTable();
    
    JOptionPane.showMessageDialog(this, "Students loaded for " + selectedCourse);
    }//GEN-LAST:event_btnViewStudentsActionPerformed

    
     private class StudentGradeData {
        String studentId, studentName, course, letterGrade;
        float totalGrade;
        double gpa;
        
        StudentGradeData(String id, String name, String course, float grade, String letter, double gpa) {
            this.studentId = id;
            this.studentName = name;
            this.course = course;
            this.totalGrade = grade;
            this.letterGrade = letter;
            this.gpa = gpa;
        }
    }

    private Business.Profiles.StudentProfile findStudentByCourseLoad(Business.University.CourseSchedule.CourseLoad cl) {
        for (Business.Profiles.StudentProfile sp : business.getStudentDirectory().getStudentList()) {
            if (sp.courseLoads != null) {
                for (Business.University.CourseSchedule.CourseLoad courseLoad : sp.courseLoads) {
                    if (courseLoad == cl) return sp;
                }
            }
        }
        return null;
    }

    private String calculateLetterGrade(float p) {
        if (p >= 93) return "A";
        if (p >= 90) return "A-";
        if (p >= 87) return "B+";
        if (p >= 83) return "B";
        if (p >= 80) return "B-";
        if (p >= 77) return "C+";
        if (p >= 73) return "C";
        if (p >= 70) return "C-";
        if (p >= 60) return "D";
        return "F";
    }

    private double calculateGPA(String grade) {
        switch (grade) {
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

    private void applyFilter() {
        // Empty for now
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddStudent;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCalculateGrade;
    private javax.swing.JButton btnDeleteStudent;
    private javax.swing.JButton btnGradeAssignment;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnViewProgress;
    private javax.swing.JButton btnViewStudents;
    private javax.swing.JButton btnViewTranscript;
    private javax.swing.JComboBox<String> jComboCourseSelection;
    private javax.swing.JComboBox<String> jComboFilterResults;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblFilterResults;
    private javax.swing.JLabel lblSelectCourse;
    private javax.swing.JLabel lblStudentManagement;
    // End of variables declaration//GEN-END:variables
}
