/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
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
                lblGraduationStatusValue.setFont(new java.awt.Font("Arial", 1, 18));
            } else {
                lblGraduationStatusValue.setText("Not Yet Eligible");
                lblGraduationStatusValue.setForeground(new Color(220, 20, 60)); // Red
                
                // Build requirements message
                StringBuilder requirements = new StringBuilder("Requirements needed:\n");
                if (remainingCredits > 0) {
                    requirements.append("• ").append(remainingCredits).append(" more credits\n");
                }
                if (!hasCoreCourse) {
                    requirements.append("• Complete ").append(CORE_COURSE).append(" (Core Course)\n");
                }
                txtRequirements.setText(requirements.toString());
            }
            
            // Display course breakdown
            displayCourseBreakdown();
            
        } catch (Exception e) {
            System.err.println("Error loading graduation data: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error loading graduation audit data.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Display detailed course breakdown
     */
    private void displayCourseBreakdown() {
        StringBuilder breakdown = new StringBuilder();
        breakdown.append("COMPLETED COURSES:\n");
        breakdown.append("=====================================\n\n");
        
        if (studentProfile.getTranscript().isEmpty()) {
            breakdown.append("No courses completed yet.\n");
        } else {
            int coreCredits = 0;
            int electiveCredits = 0;
            
            for (TranscriptRecord record : studentProfile.getTranscript()) {
                if (record != null && record.getCourse() != null) {
                    String courseId = record.getCourse().getCourseNumber();
                    String courseName = record.getCourse().getName();
                    int credits = record.getCourse().getCredits();
                    String grade = record.getGrade() != null ? record.getGrade() : "N/A";
                    
                    breakdown.append(String.format("%-12s %-30s %d credits (Grade: %s)\n", 
                        courseId, courseName, credits, grade));
                    
                    if (CORE_COURSE.equalsIgnoreCase(courseId)) {
                        coreCredits += credits;
                    } else {
                        electiveCredits += credits;
                    }
                }
            }
            
            breakdown.append("\n=====================================\n");
            breakdown.append(String.format("Core Credits: %d / %d\n", coreCredits, CORE_CREDITS));
            breakdown.append(String.format("Elective Credits: %d / %d\n", 
                electiveCredits, REQUIRED_TOTAL_CREDITS - CORE_CREDITS));
        }
        
        txtCourseBreakdown.setText(breakdown.toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTotalCreditsValue = new javax.swing.JLabel();
        lblRequiredCreditsValue = new javax.swing.JLabel();
        lblRemainingCreditsValue = new javax.swing.JLabel();
        lblCoreCourseValue = new javax.swing.JLabel();
        lblGraduationStatusValue = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRequirements = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCourseBreakdown = new javax.swing.JTextArea();
        btnBack = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Graduation Audit - MSIS Program");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Total Credits Earned:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Total Credits Required:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Credits Remaining:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Core Course (INFO 5100):");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel6.setText("Graduation Status:");

        lblTotalCreditsValue.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTotalCreditsValue.setText("0");

        lblRequiredCreditsValue.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblRequiredCreditsValue.setText("32");

        lblRemainingCreditsValue.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblRemainingCreditsValue.setText("32");

        lblCoreCourseValue.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCoreCourseValue.setText("Not Completed");

        lblGraduationStatusValue.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblGraduationStatusValue.setText("Not Yet Eligible");

        txtRequirements.setEditable(false);
        txtRequirements.setColumns(20);
        txtRequirements.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtRequirements.setRows(5);
        txtRequirements.setText("Requirements needed:\n• Complete INFO 5100 (Core Course)\n• Earn 32 total credits");
        jScrollPane1.setViewportView(txtRequirements);

        txtCourseBreakdown.setEditable(false);
        txtCourseBreakdown.setColumns(20);
        txtCourseBreakdown.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtCourseBreakdown.setRows(5);
        jScrollPane2.setViewportView(txtCourseBreakdown);

        btnBack.setBackground(new java.awt.Color(204, 204, 204));
        btnBack.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnBack.setText("Back to Dashboard");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(102, 153, 255));
        btnRefresh.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Course Breakdown:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalCreditsValue, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRequiredCreditsValue, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRemainingCreditsValue, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCoreCourseValue, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(20, 20, 20)
                        .addComponent(lblGraduationStatusValue, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblTotalCreditsValue))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblRequiredCreditsValue))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblRemainingCreditsValue))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblCoreCourseValue))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblGraduationStatusValue))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblCoreCourseValue;
    private javax.swing.JLabel lblGraduationStatusValue;
    private javax.swing.JLabel lblRemainingCreditsValue;
    private javax.swing.JLabel lblRequiredCreditsValue;
    private javax.swing.JLabel lblTotalCreditsValue;
    private javax.swing.JTextArea txtCourseBreakdown;
    private javax.swing.JTextArea txtRequirements;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration                   
}