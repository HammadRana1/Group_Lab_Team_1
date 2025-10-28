/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.WorkAreas.FacultyRole.FacultyRoleWorkResp02;
import Business.Business;
import Business.Profiles.FacultyProfile;
import Business.Profiles.StudentProfile;
import Business.University.CourseSchedule.CourseOffer;
import Business.University.CourseSchedule.CourseLoad;
import Business.University.CourseSchedule.CourseSchedule;
import Business.University.CourseSchedule.Seat;
import Business.University.CourseSchedule.SeatAssignment;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.ArrayList;


/**
 *
 * @author Hammad
 */
public class AssignmentGradingJPanel extends javax.swing.JPanel {

    /**
     * Creates new form AssignmentGradingJPanel
     */
    private JPanel userProcessContainer;
    private Business business;
    private FacultyProfile facultyProfile;
    private DefaultTableModel studentManagementTableModel;  
    private String selectedCourseName;  
    public AssignmentGradingJPanel(JPanel container, Business bz, FacultyProfile fp, 
                               DefaultTableModel smTableModel, String course) {
        initComponents();
        this.userProcessContainer = container;
        this.business = bz;
        this.facultyProfile = fp;
       this.studentManagementTableModel = smTableModel;  
        this.selectedCourseName = course;  
        
        loadCourses();
        setupNumericColumns();
        setupTableListener();
        
         jComboCourseSelection.addActionListener(e -> loadStudentsForCourse());
    }
    
     private void loadCourses() {
        jComboCourseSelection.removeAllItems();
        jComboCourseSelection.addItem("-- Select Course --");
        
        ArrayList<CourseOffer> courses = facultyProfile.getCourseOfferings();
        
        for (CourseOffer co : courses) {
            jComboCourseSelection.addItem(co.getCourseNumber());
        }
        
        if (courses.isEmpty()) {
            jComboCourseSelection.addItem("INFO 5100");
            jComboCourseSelection.addItem("INFO 6205");
            jComboCourseSelection.addItem("INFO 6150");
        }
    }
     
     private void setupNumericColumns() {
        // Make assignment columns only accept numbers
        for (int col = 2; col <= 6; col++) {
            jTableGradeAssignment.getColumnModel().getColumn(col).setCellEditor(
                new javax.swing.DefaultCellEditor(new JTextField()) {
                    @Override
                    public boolean stopCellEditing() {
                        String value = ((JTextField)getComponent()).getText();
                        try {
                            if (!value.isEmpty()) {
                                float num = Float.parseFloat(value);
                                if (num < 0 || num > 100) {
                                    JOptionPane.showMessageDialog(null, "Enter 0-100 only!");
                                    return false;
                                }
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Numbers only!");
                            return false;
                        }
                        return super.stopCellEditing();
                    }
                }
            );
        }
    }
    
     
     private void setupTableListener() {
        // Auto-calculate when user edits assignments
        jTableGradeAssignment.getModel().addTableModelListener(e -> {
            if (e.getColumn() >= 2 && e.getColumn() <= 6) {
                calculateRowGrade(e.getFirstRow());
            }
        });
    }
     
     private void calculateRowGrade(int row) {
        DefaultTableModel model = (DefaultTableModel) jTableGradeAssignment.getModel();
        
        try {
            float total = 0;
            
            // Each of 5 assignments = 20%
            for (int col = 2; col <= 6; col++) {
                String scoreStr = model.getValueAt(row, col).toString();
                float score = scoreStr.isEmpty() ? 0 : Float.parseFloat(scoreStr);
                total += score * 0.20f;
            }
            
            String letterGrade = calculateLetterGrade(total);
            model.setValueAt(Math.round(total) + "% (" + letterGrade + ")", row, 7);
            
        } catch (Exception e) {
            // Ignore errors during editing
        }
    }
     
     private void loadStudentsForCourse() {
        String selectedCourse = (String) jComboCourseSelection.getSelectedItem();
    
    if (selectedCourse == null || selectedCourse.equals("-- Select Course --")) {
        return;
    }
    
    DefaultTableModel model = (DefaultTableModel) jTableGradeAssignment.getModel();
    model.setRowCount(0);
    
    try {
        CourseSchedule schedule = business.getDepartment().getCourseSchedule("Fall2025");
        if (schedule == null) return;
        
        CourseOffer courseOffer = schedule.getCourseOfferByNumber(selectedCourse);
        if (courseOffer == null) return;
        
        ArrayList<Seat> seats = courseOffer.getSeatlist();
        
        for (Seat seat : seats) {
            if (seat.isOccupied()) {
                SeatAssignment sa = seat.seatassignment;
                
                if (sa != null) {
                    CourseLoad courseLoad = sa.courseload;
                    StudentProfile student = findStudentByCourseLoad(courseLoad);
                    
                    if (student != null) {
                        String studentId = "STU-" + String.format("%03d", 
                            Math.abs(student.getPerson().getPersonId().hashCode()) % 1000);
                        String studentName = student.getPerson().getPersonId();
                        
                        // ✓ Get their current grade from the system
                        float currentGrade = sa.grade > 0 ? sa.grade * 25 : (float)(Math.random() * 40 + 60);
                        
                        // ✓ Distribute grade across 5 assignments with variation
                        float base = currentGrade;
                        float a1 = base + (float)(Math.random() * 10 - 5); // ±5 variation
                        float a2 = base + (float)(Math.random() * 10 - 5);
                        float a3 = base + (float)(Math.random() * 10 - 5);
                        float a4 = base + (float)(Math.random() * 10 - 5);
                        float a5 = base + (float)(Math.random() * 10 - 5);
                        
                        // Ensure values stay in 0-100 range
                        a1 = Math.max(0, Math.min(100, a1));
                        a2 = Math.max(0, Math.min(100, a2));
                        a3 = Math.max(0, Math.min(100, a3));
                        a4 = Math.max(0, Math.min(100, a4));
                        a5 = Math.max(0, Math.min(100, a5));
                        
                        String letterGrade = calculateLetterGrade(currentGrade);
                        
                        model.addRow(new Object[]{
                            studentId, 
                            studentName, 
                            Math.round(a1), 
                            Math.round(a2), 
                            Math.round(a3), 
                            Math.round(a4), 
                            Math.round(a5), 
                            Math.round(currentGrade) + "% (" + letterGrade + ")"
                        });
                    }
                }
            }
        }
        
    } catch (Exception e) {
        e.printStackTrace();
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
     
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSelectCourse = new javax.swing.JLabel();
        jComboCourseSelection = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableGradeAssignment = new javax.swing.JTable();
        btnGrade = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        lblTitle.setText("Assignment Grading");

        lblSelectCourse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSelectCourse.setText("Select Course");

        jComboCourseSelection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTableGradeAssignment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Assignment 1", "Assignment 2", "Assignment 3", "Assignment 4", "Assignment 5", "Grade"
            }
        ));
        jScrollPane1.setViewportView(jTableGradeAssignment);

        btnGrade.setText("Calculate Grade");
        btnGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGradeActionPerformed(evt);
            }
        });

        btnExport.setText("Export Grade and Exit");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnBack.setText("<<Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Each Assignment has weightage of 20%");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(lblTitle)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnGrade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelectCourse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboCourseSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectCourse)
                    .addComponent(jComboCourseSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGrade)
                    .addComponent(btnExport)
                    .addComponent(btnBack))
                .addContainerGap(134, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGradeActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTableGradeAssignment.getModel();
    
    if (model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Please select a course first!");
        return;
    }
    
    // Calculate grades for all students
    for (int row = 0; row < model.getRowCount(); row++) {
        try {
            float total = 0;
            
            // Each of 5 assignments = 20%
            for (int col = 2; col <= 6; col++) {
                String scoreStr = model.getValueAt(row, col).toString();
                float score = scoreStr.isEmpty() ? 0 : Float.parseFloat(scoreStr);
                total += score * 0.20f;
            }
            
            String letterGrade = calculateLetterGrade(total);
            model.setValueAt(Math.round(total) + "% (" + letterGrade + ")", row, 7);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error in row " + (row+1) + ": Invalid number!");
            return;
        }
    }
    
    JOptionPane.showMessageDialog(this, "All grades calculated successfully!");
    }//GEN-LAST:event_btnGradeActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        
    DefaultTableModel model = (DefaultTableModel) jTableGradeAssignment.getModel();
    
    if (model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No data to export!");
        return;
    }
    
    int updatedCount = 0;
    
    // Loop through assignment table
    for (int assignRow = 0; assignRow < model.getRowCount(); assignRow++) {
        String studentId = model.getValueAt(assignRow, 0).toString();
        String gradeStr = model.getValueAt(assignRow, 7).toString();
        
        // Extract percentage from "85% (B)" format
        String[] parts = gradeStr.split("%");
        int newGrade = Integer.parseInt(parts[0].trim());
        
        // Find and update in Student Management table
        for (int smRow = 0; smRow < studentManagementTableModel.getRowCount(); smRow++) {
            String smStudentId = studentManagementTableModel.getValueAt(smRow, 0).toString();
            
            if (smStudentId.equals(studentId)) {
                int oldGrade = Integer.parseInt(studentManagementTableModel.getValueAt(smRow, 3).toString());
                
                // Only update if changed
                if (newGrade != oldGrade) {
                    String letterGrade = calculateLetterGrade(newGrade);
                    double gpa = calculateGPA(letterGrade);
                    
                    studentManagementTableModel.setValueAt(newGrade, smRow, 3);
                    studentManagementTableModel.setValueAt(letterGrade, smRow, 4);
                    studentManagementTableModel.setValueAt(String.format("%.2f", gpa), smRow, 6);
                    updatedCount++;
                }
                break;
            }
        }
    }
    
    JOptionPane.showMessageDialog(this, 
        "Grades exported!\n" + updatedCount + " student(s) updated.");
    
    // Go back
    ((java.awt.CardLayout) userProcessContainer.getLayout()).show(userProcessContainer, "StudentManagement");
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        ((java.awt.CardLayout) userProcessContainer.getLayout()).show(userProcessContainer, "StudentManagement");
    }//GEN-LAST:event_btnBackActionPerformed
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnGrade;
    private javax.swing.JComboBox<String> jComboCourseSelection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableGradeAssignment;
    private javax.swing.JLabel lblSelectCourse;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
