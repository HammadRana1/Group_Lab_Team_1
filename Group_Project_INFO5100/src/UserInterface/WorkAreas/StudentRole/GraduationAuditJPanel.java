/*
 * GraduationAuditJPanel.java
 * Graduation audit for student - track credits and graduation requirements
 * Author: Syrill T
 * Date: 10/26/2025
 */
package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import Business.Transcript.TranscriptRecord;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

/**
 * Graduation Audit Panel
 * Displays student progress toward MSIS degree completion
 * Requirements: 32 total credits, INFO 5100 (4 credits core)
 */
public class GraduationAuditJPanel extends javax.swing.JPanel {

    private JPanel cardSequencePanel;
    private Business business;
    private StudentProfile studentProfile;
    
    private static final int REQUIRED_TOTAL_CREDITS = 32;
    private static final String CORE_COURSE = "INFO 5100";
    private static final int CORE_CREDITS = 4;

    /**
     * Creates new Graduation Audit Panel
     */
    public GraduationAuditJPanel(Business b, StudentProfile sp, JPanel clp) {
        // Validate inputs
        if (b == null || sp == null || clp == null) {
            throw new IllegalArgumentException("GraduationAuditJPanel requires non-null parameters");
        }
        
        this.business = b;
        this.studentProfile = sp;
        this.cardSequencePanel = clp;
        
        initComponents();
        loadGraduationData();
    }

    /**
     * Load and display graduation audit data
     */
    private void loadGraduationData() {
        try {
            // Calculate total earned credits from transcript
            int totalCredits = studentProfile.totalEarnedCredits();
            
            // Check if core course completed
            boolean hasCoreCourse = studentProfile.hasCompleted(CORE_COURSE);
            
            // Calculate remaining credits needed
            int remainingCredits = Math.max(0, REQUIRED_TOTAL_CREDITS - totalCredits);
            
            // Update display labels
            lblTotalCreditsValue.setText(String.valueOf(totalCredits));
            lblRequiredCreditsValue.setText(String.valueOf(REQUIRED_TOTAL_CREDITS));
            lblRemainingCreditsValue.setText(String.valueOf(remainingCredits));
            
            // Core course status
            if (hasCoreCourse) {
                lblCoreCourseValue.setText("✓ Completed");
                lblCoreCourseValue.setForeground(new Color(34, 139, 34)); // Green
            } else {
                lblCoreCourseValue.setText("✗ Not Completed");
                lblCoreCourseValue.setForeground(new Color(220, 20, 60)); // Red
            }
            
            // Check graduation eligibility
            boolean isReady = (totalCredits >= REQUIRED_TOTAL_CREDITS) && hasCoreCourse;
            
            if (isReady) {
                lblGraduationStatusValue.setText("✓ READY TO GRADUATE!");
                lblGraduationStatusValue.setForeground(new Color(34, 139, 34)); // Green
                lblGraduationStatusValue.setFont(new java.awt.Font("Segoe UI", 1, 18));
                txtRequirements.setText("Congratulations! You have met all graduation requirements.");
            } else {
                lblGraduationStatusValue.setText("Not Yet Eligible");
                lblGraduationStatusValue.setForeground(new Color(220, 20, 60)); // Red
                
                // Build requirements message
                StringBuilder requirements = new StringBuilder("Requirements still needed:\n\n");
                if (remainingCredits > 0) {
                    requirements.append("• ").append(remainingCredits).append(" more credits\n");
                }
                if (!hasCoreCourse) {
                    requirements.append("• Complete ").append(CORE_COURSE).append(" (Core Course)\n");
                }
                txtRequirements.setText(requirements.toString());
            }
            
            // Display course breakdown
            populateCourseTable();
            
        } catch (Exception e) {
            System.err.println("Error loading graduation data: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error loading graduation audit data.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Populate table with completed courses
     */
    private void populateCourseTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows
        
        if (studentProfile.getTranscript().isEmpty()) {
            // Add empty message row
            Object[] emptyRow = {"No courses completed yet", "", "", "", ""};
            model.addRow(emptyRow);
        } else {
            int coreCredits = 0;
            int electiveCredits = 0;
            
            for (TranscriptRecord record : studentProfile.getTranscript()) {
                if (record != null && record.getCourse() != null) {
                    String courseId = record.getCourse().getCourseNumber();
                    String courseName = record.getCourse().getName();
                    int credits = record.getCourse().getCredits();
                    String grade = record.getGrade() != null ? record.getGrade() : "N/A";
                    String type = CORE_COURSE.equalsIgnoreCase(courseId) ? "Core" : "Elective";
                    
                    Object[] row = {courseId, courseName, credits, grade, type};
                    model.addRow(row);
                    
                    if (CORE_COURSE.equalsIgnoreCase(courseId)) {
                        coreCredits += credits;
                    } else {
                        electiveCredits += credits;
                    }
                }
            }
            
            // Add summary row
            Object[] summaryRow = {"", "TOTAL", (coreCredits + electiveCredits), "", ""};
            model.addRow(summaryRow);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblTotalCredits = new javax.swing.JLabel();
        lblTotalCreditsValue = new javax.swing.JLabel();
        lblRequiredCredits = new javax.swing.JLabel();
        lblRequiredCreditsValue = new javax.swing.JLabel();
        lblRemainingCredits = new javax.swing.JLabel();
        lblRemainingCreditsValue = new javax.swing.JLabel();
        lblCoreCourse = new javax.swing.JLabel();
        lblCoreCourseValue = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblGraduationStatus = new javax.swing.JLabel();
        lblGraduationStatusValue = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRequirements = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblCourseBreakdown = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Graduation Audit - MSIS Program");

        lblTotalCredits.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalCredits.setText("Total Credits Earned:");

        lblTotalCreditsValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalCreditsValue.setForeground(new java.awt.Color(0, 102, 204));
        lblTotalCreditsValue.setText("0");

        lblRequiredCredits.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRequiredCredits.setText("Total Credits Required:");

        lblRequiredCreditsValue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRequiredCreditsValue.setText("32");

        lblRemainingCredits.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRemainingCredits.setText("Credits Remaining:");

        lblRemainingCreditsValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRemainingCreditsValue.setForeground(new java.awt.Color(204, 0, 0));
        lblRemainingCreditsValue.setText("32");

        lblCoreCourse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCoreCourse.setText("Core Course (INFO 5100):");

        lblCoreCourseValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCoreCourseValue.setText("Not Completed");

        lblGraduationStatus.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblGraduationStatus.setText("Graduation Status:");

        lblGraduationStatusValue.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblGraduationStatusValue.setText("Not Yet Eligible");

        txtRequirements.setEditable(false);
        txtRequirements.setColumns(20);
        txtRequirements.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtRequirements.setRows(5);
        txtRequirements.setText("Requirements needed:\n• Complete INFO 5100 (Core Course)\n• Earn 32 total credits");
        txtRequirements.setBackground(new java.awt.Color(245, 245, 245));
        jScrollPane1.setViewportView(txtRequirements);

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course ID", "Course Name", "Credits", "Grade", "Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        lblCourseBreakdown.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCourseBreakdown.setText("Completed Courses:");

        btnRefresh.setBackground(new java.awt.Color(102, 153, 255));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(204, 204, 204));
        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotalCredits)
                                    .addComponent(lblRemainingCredits))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotalCreditsValue, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRemainingCreditsValue, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(80, 80, 80)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCoreCourse)
                                    .addComponent(lblRequiredCredits))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRequiredCreditsValue)
                                    .addComponent(lblCoreCourseValue)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblGraduationStatus)
                                .addGap(18, 18, 18)
                                .addComponent(lblGraduationStatusValue))
                            .addComponent(lblCourseBreakdown)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalCredits)
                    .addComponent(lblTotalCreditsValue)
                    .addComponent(lblRequiredCredits)
                    .addComponent(lblRequiredCreditsValue))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRemainingCredits)
                    .addComponent(lblRemainingCreditsValue)
                    .addComponent(lblCoreCourse)
                    .addComponent(lblCoreCourseValue))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGraduationStatus)
                    .addComponent(lblGraduationStatusValue))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCourseBreakdown)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>                        

    /**
     * Navigate back to student dashboard
     */
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {                                        
        try {
            CardLayout layout = (CardLayout) cardSequencePanel.getLayout();
            layout.show(cardSequencePanel, "StudentWorkArea");
        } catch (Exception e) {
            System.err.println("Error navigating back: " + e.getMessage());
        }
    }                                       

    /**
     * Refresh graduation audit data
     */
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {                                           
        loadGraduationData();
        JOptionPane.showMessageDialog(this,
            "Graduation audit refreshed!",
            "Refreshed", JOptionPane.INFORMATION_MESSAGE);
    }                                          

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCoreCourse;
    private javax.swing.JLabel lblCoreCourseValue;
    private javax.swing.JLabel lblCourseBreakdown;
    private javax.swing.JLabel lblGraduationStatus;
    private javax.swing.JLabel lblGraduationStatusValue;
    private javax.swing.JLabel lblRemainingCredits;
    private javax.swing.JLabel lblRemainingCreditsValue;
    private javax.swing.JLabel lblRequiredCredits;
    private javax.swing.JLabel lblRequiredCreditsValue;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalCredits;
    private javax.swing.JLabel lblTotalCreditsValue;
    private javax.swing.JTextArea txtRequirements;
    // End of variables declaration                   
}