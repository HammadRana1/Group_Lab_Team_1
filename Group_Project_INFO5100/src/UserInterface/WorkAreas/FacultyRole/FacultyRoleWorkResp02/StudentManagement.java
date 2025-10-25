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
        setupGradeColors();
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
        
        ArrayList<Seat> seats = courseOffer.getSeatlist();
        
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
        btnLogout = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblFilterResults = new javax.swing.JLabel();
        jComboFilterResults = new javax.swing.JComboBox<>();

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
        btnViewTranscript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewTranscriptActionPerformed(evt);
            }
        });

        btnGradeAssignment.setText("Grade Assignment");
        btnGradeAssignment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGradeAssignmentActionPerformed(evt);
            }
        });

        btnCalculateGrade.setText("Calculate Grade & Progress Report");
        btnCalculateGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculateGradeActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnBack.setText("<<Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblFilterResults.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFilterResults.setText("Filter Results");

        jComboFilterResults.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                        .addComponent(btnViewStudents)
                        .addGap(18, 18, 18)
                        .addComponent(btnViewTranscript, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGradeAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCalculateGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStudentManagement)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                    .addComponent(btnCalculateGrade))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogout)
                    .addComponent(btnBack))
                .addContainerGap(103, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewStudentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewStudentsActionPerformed
        
// TODO add your handling code here:
      int rowCount = jTable1.getRowCount();
    
    if (rowCount == 0) {
        JOptionPane.showMessageDialog(this, "No students enrolled!");
        return;
    }
    
    String message = "Total Students: " + rowCount + "\n\n";
    
    for (int i = 0; i < rowCount; i++) {
        String name = jTable1.getValueAt(i, 1).toString();
        String grade = jTable1.getValueAt(i, 3).toString();
        String letter = jTable1.getValueAt(i, 4).toString();
        
        message += (i+1) + ". " + name + " - " + grade + "% (" + letter + ")\n";
    }
    
    JOptionPane.showMessageDialog(this, message, "Students Enrolled", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnViewStudentsActionPerformed

    private void btnViewTranscriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewTranscriptActionPerformed
        // TODO add your handling code here:
        
    int selectedRow = jTable1.getSelectedRow();
    
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }
    
    String studentId = jTable1.getValueAt(selectedRow, 0).toString();
    String studentName = jTable1.getValueAt(selectedRow, 1).toString();
    
    // Find the student
    StudentProfile student = null;
    for (StudentProfile sp : business.getStudentDirectory().getStudentList()) {
        String sid = "STU-" + String.format("%03d", Math.abs(sp.getPerson().getPersonId().hashCode()) % 1000);
        if (sid.equals(studentId)) {
            student = sp;
            break;
        }
    }
    
    if (student == null) {
        JOptionPane.showMessageDialog(this, "Student not found!");
        return;
    }
    
    // Build transcript
    String transcript = "═══════════════════════════════════\n";
    transcript += "      OFFICIAL TRANSCRIPT\n";
    transcript += "═══════════════════════════════════\n\n";
    transcript += "Student: " + studentName + "\n";
    transcript += "ID: " + studentId + "\n";
    transcript += "Semester: Fall 2025\n\n";
    transcript += "COURSES:\n";
    transcript += "───────────────────────────────────\n";
    
    float totalGradePoints = 0;
    int totalCredits = 0;
    
    for (CourseLoad cl : student.courseLoads) {
        for (SeatAssignment sa : cl.getSeatAssignments()) {
            String courseNum = sa.getCourseOffer().getCourseNumber();
            int credits = sa.getCreditHours();
            float gradeScore = sa.grade > 0 ? sa.grade * 25 : (float)(Math.random() * 40 + 60);
            String letterGrade = calculateLetterGrade(gradeScore);
            double gpa = calculateGPA(letterGrade);
            
            transcript += courseNum + " - " + Math.round(gradeScore) + "% (" + letterGrade + ")\n";
            transcript += "  Credits: " + credits + " | GPA: " + String.format("%.2f", gpa) + "\n\n";
            
            totalGradePoints += gpa * credits;
            totalCredits += credits;
        }
    }
    
    double cumulativeGPA = totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    
    transcript += "───────────────────────────────────\n";
    transcript += "Total Credits: " + totalCredits + "\n";
    transcript += "Cumulative GPA: " + String.format("%.2f", cumulativeGPA) + "\n";
    transcript += "═══════════════════════════════════\n";
    
    javax.swing.JTextArea textArea = new javax.swing.JTextArea(transcript);
    textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
    textArea.setEditable(false);
    textArea.setRows(20);
    textArea.setColumns(40);
    
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
    
    JOptionPane.showMessageDialog(this, scrollPane, 
        "Transcript - " + studentName, 
        JOptionPane.INFORMATION_MESSAGE);     
    
    }//GEN-LAST:event_btnViewTranscriptActionPerformed

    private void btnGradeAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGradeAssignmentActionPerformed
        // TODO add your handling code here:
        
        int selectedRow = jTable1.getSelectedRow();
    
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select a student first!");
        return;
    }
    
    String studentName = jTable1.getValueAt(selectedRow, 1).toString();
    String course = jTable1.getValueAt(selectedRow, 2).toString();
    String currentGrade = jTable1.getValueAt(selectedRow, 3).toString();
    
    // Ask for new grade
    String newGrade = JOptionPane.showInputDialog(this, 
        "Enter grade for " + studentName + "\n" +
        "Course: " + course + "\n" +
        "Current Grade: " + currentGrade + "%\n\n" +
        "New Grade (0-100):");
    
    if (newGrade == null || newGrade.trim().isEmpty()) {
        return;
    }
    
    try {
        float gradeValue = Float.parseFloat(newGrade);
        
        if (gradeValue < 0 || gradeValue > 100) {
            JOptionPane.showMessageDialog(this, "Grade must be between 0 and 100!");
            return;
        }
        
        // Calculate letter grade and GPA
        String letterGrade = calculateLetterGrade(gradeValue);
        double gpa = calculateGPA(letterGrade);
        
        // Update the table
        jTable1.setValueAt(Math.round(gradeValue), selectedRow, 3);
        jTable1.setValueAt(letterGrade, selectedRow, 4);
        jTable1.setValueAt(String.format("%.2f", gpa), selectedRow, 6);
        
        // Re-sort and update ranks
        recalculateRanks();
        
        JOptionPane.showMessageDialog(this, 
            "Grade updated!\n\n" +
            "New Grade: " + Math.round(gradeValue) + "%\n" +
            "Letter: " + letterGrade + "\n" +
            "GPA: " + String.format("%.2f", gpa));
            
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid grade! Enter a number between 0-100.");
    }
    }//GEN-LAST:event_btnGradeAssignmentActionPerformed

    private void btnCalculateGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculateGradeActionPerformed
        // TODO add your handling code here:
       String selectedCourse = (String) jComboCourseSelection.getSelectedItem();
    
    AssignmentGradingJPanel agp = new AssignmentGradingJPanel(
        userProcessContainer, 
        business, 
        facultyProfile,
        (DefaultTableModel) jTable1.getModel(),  // ✓ Pass table model
        selectedCourse  // ✓ Pass selected course
    );
    
    userProcessContainer.add("AssignmentGrading", agp);
    ((java.awt.CardLayout) userProcessContainer.getLayout()).show(userProcessContainer, "AssignmentGrading");
        
    }//GEN-LAST:event_btnCalculateGradeActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to logout?", 
        "Confirm Logout", 
        JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        userProcessContainer.removeAll();
        ((java.awt.CardLayout) userProcessContainer.getLayout()).first(userProcessContainer);
        JOptionPane.showMessageDialog(this, "Logged out successfully!");
    }
        
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        ((java.awt.CardLayout) userProcessContainer.getLayout()).show(userProcessContainer, "faculty");
    }//GEN-LAST:event_btnBackActionPerformed

    
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

    private StudentProfile findStudentByCourseLoad(CourseLoad cl) {
    for (StudentProfile sp : business.getStudentDirectory().getStudentList()) {
        if (sp.courseLoads != null) {
            for (CourseLoad courseLoad : sp.courseLoads) {
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
        String filter = (String) jComboFilterResults.getSelectedItem();
    
    if (filter == null || filter.equals("-- No Filter --")) {
        return;
    }
    
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    java.util.List<Object[]> rows = new java.util.ArrayList<>();
    
    // Get all rows
    for (int i = 0; i < model.getRowCount(); i++) {
        Object[] row = {
            model.getValueAt(i, 0), model.getValueAt(i, 1), model.getValueAt(i, 2),
            model.getValueAt(i, 3), model.getValueAt(i, 4), model.getValueAt(i, 5),
            model.getValueAt(i, 6)
        };
        rows.add(row);
    }
    
    // Sort
    if (filter.contains("High to Low")) {
        rows.sort((a, b) -> Integer.compare(Integer.parseInt(b[3].toString()), Integer.parseInt(a[3].toString())));
    } else if (filter.contains("Low to High")) {
        rows.sort((a, b) -> Integer.compare(Integer.parseInt(a[3].toString()), Integer.parseInt(b[3].toString())));
    } else if (filter.contains("Letter Grade")) {
        rows.sort((a, b) -> a[4].toString().compareTo(b[4].toString()));
    } else if (filter.contains("GPA")) {
        rows.sort((a, b) -> Double.compare(Double.parseDouble(b[6].toString()), Double.parseDouble(a[6].toString())));
    }
    
    // Update table
    model.setRowCount(0);
    for (Object[] row : rows) {
        model.addRow(row);
    }
    
    recalculateRanks();
    }
    
    private void recalculateRanks() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    int rowCount = model.getRowCount();
    
    // Create list of row indices
    java.util.List<Integer> indices = new java.util.ArrayList<>();
    for (int i = 0; i < rowCount; i++) {
        indices.add(i);
    }
    
    // Sort by grade (highest to lowest)
    indices.sort((i1, i2) -> {
        int grade1 = Integer.parseInt(model.getValueAt(i1, 3).toString());
        int grade2 = Integer.parseInt(model.getValueAt(i2, 3).toString());
        return Integer.compare(grade2, grade1);
    });
    
    // Update ranks
    int rank = 1;
    for (int i : indices) {
        model.setValueAt(rank++, i, 5);
    }
}
    
    private void setupGradeColors() {
    jTable1.getColumnModel().getColumn(4).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table, Object value, boolean isSelected, 
            boolean hasFocus, int row, int column) {
            
            java.awt.Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
            
            if (value != null && value.equals("F")) {
                c.setForeground(java.awt.Color.RED);
            } else {
                c.setForeground(java.awt.Color.BLACK);
            }
            
            return c;
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCalculateGrade;
    private javax.swing.JButton btnGradeAssignment;
    private javax.swing.JButton btnLogout;
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
