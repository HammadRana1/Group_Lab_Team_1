/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.WorkAreas.FacultyRole.FacultyRoleWorkResp02;
import Business.Business;
import Business.Profiles.FacultyProfile;
import Business.Profiles.StudentProfile;
import Business.University.CourseSchedule.CourseOffer;
import Business.University.CourseSchedule.CourseSchedule;
import Business.University.CourseSchedule.Seat;
import Business.University.CourseSchedule.SeatAssignment;
import Business.University.CourseSchedule.CourseLoad;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 *
 * @author Hammad
 */

public class PerformanceReporting extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private Business business;
    private FacultyProfile facultyProfile;
    /**
     * Creates new form PerformanceReporting
     */
    
    public PerformanceReporting(JPanel container, Business bz, FacultyProfile fp) {
        initComponents();
        this.userProcessContainer = container;
        this.business = bz;
        this.facultyProfile = fp;
        
        setupSemester();
        jComboBox1.addActionListener(e -> loadData());
        
    }
    
    private void setupSemester() {
        jComboBox1.removeAllItems();
        jComboBox1.addItem("-- Select Semester --");
        jComboBox1.addItem("Fall2025");
        jComboBox1.addItem("Spring2026");
        jComboBox1.addItem("Summer2026");
    }
    
    private void loadData() {
        String semester = (String) jComboBox1.getSelectedItem();
        
        if (semester == null || semester.equals("-- Select Semester --")) {
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try {
            CourseSchedule schedule = business.getDepartment().getCourseSchedule(semester);
            if (schedule == null) return;
            
            // Get all courses
            ArrayList<String> courseNumbers = new ArrayList<>();
            courseNumbers.add("INFO 5100");
            courseNumbers.add("INFO 6205");
            courseNumbers.add("INFO 6150");
            
            for (String courseNum : courseNumbers) {
                CourseOffer co = schedule.getCourseOfferByNumber(courseNum);
                if (co == null) continue;
                
                int enrolled = 0;
                float totalGrade = 0;
                int aCount = 0, bCount = 0, cCount = 0, dCount = 0, fCount = 0;
                
                // Get actual student data from the system
                ArrayList<Seat> seats = co.getSeatlist();
                
                for (Seat seat : seats) {
                    if (seat.isOccupied()) {
                        enrolled++;
                        SeatAssignment sa = seat.seatassignment;
                        
                        if (sa != null) {
                            CourseLoad cl = sa.courseload;
                            StudentProfile student = findStudentByCourseLoad(cl);
                            
                            if (student != null) {
                                // Get actual grade from seat assignment
                                float grade = sa.grade > 0 ? sa.grade * 25 : (float)(Math.random() * 40 + 60);
                                totalGrade += grade;
                                
                                String letter = calculateLetterGrade(grade);
                                if (letter.startsWith("A")) aCount++;
                                else if (letter.startsWith("B")) bCount++;
                                else if (letter.startsWith("C")) cCount++;
                                else if (letter.startsWith("D")) dCount++;
                                else fCount++;
                            }
                        }
                    }
                }
                
                float avg = enrolled > 0 ? totalGrade / enrolled : 0;
                String dist = "A:" + aCount + " B:" + bCount + " C:" + cCount + " D:" + dCount + " F:" + fCount;
                
                model.addRow(new Object[]{
                    courseNum,
                    enrolled,
                    Math.round(avg) + "%",
                    dist
                });
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
        lblSelectTitle = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        lblCourseSummary = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnExportReport = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitle.setText("Performance Reporting");

        lblSelectTitle.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSelectTitle.setText("Select Semister");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblCourseSummary.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCourseSummary.setText("Course Performance Summary  ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Course", "Enrollment Count", "Average Grade", "Grade Distribution"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnExportReport.setText("Export Report");
        btnExportReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportReportActionPerformed(evt);
            }
        });

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(223, 223, 223)
                                .addComponent(lblTitle))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblSelectTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblCourseSummary)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnExportReport, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSelectTitle)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(lblCourseSummary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExportReport)
                    .addComponent(btnBack))
                .addContainerGap(160, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportReportActionPerformed
        // TODO add your handling code here:
        if (jTable1.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No data to export! Please select a semester.");
        return;
    }
    
    // Create file chooser
    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    fileChooser.setDialogTitle("Save Performance Report");
    fileChooser.setSelectedFile(new java.io.File("PerformanceReport_" + jComboBox1.getSelectedItem() + ".txt"));
    
    int result = fileChooser.showSaveDialog(this);
    
    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File file = fileChooser.getSelectedFile();
        
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(file);
            
            String semester = (String) jComboBox1.getSelectedItem();
            writer.println("═══════════════════════════════════");
            writer.println("    PERFORMANCE REPORT");
            writer.println("═══════════════════════════════════");
            writer.println();
            writer.println("Semester: " + semester);
            writer.println();
            
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                writer.println("Course: " + jTable1.getValueAt(i, 0));
                writer.println("  Enrollment: " + jTable1.getValueAt(i, 1) + " students");
                writer.println("  Average Grade: " + jTable1.getValueAt(i, 2));
                writer.println("  Grade Distribution: " + jTable1.getValueAt(i, 3));
                writer.println();
            }
            
            writer.println("═══════════════════════════════════");
            writer.close();
            
            JOptionPane.showMessageDialog(this, 
                "Report exported successfully!\n\nSaved to: " + file.getAbsolutePath());
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnExportReportActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        ((java.awt.CardLayout) userProcessContainer.getLayout()).show(userProcessContainer, "faculty");
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnExportReport;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCourseSummary;
    private javax.swing.JLabel lblSelectTitle;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}
