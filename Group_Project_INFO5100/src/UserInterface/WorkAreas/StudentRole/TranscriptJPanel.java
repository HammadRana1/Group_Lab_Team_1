/*
/*
 * TranscriptJPanel.java
 * Transcript viewer for student - view grades and academic history
 * Author: Syrill T
 * Date: 10/26/2025
 */
package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import Business.University.Transcript.TranscriptRecord;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * Transcript Panel
 * Displays academic history with semester filtering and GPA calculations
 */
public class TranscriptJPanel extends javax.swing.JPanel {

    private JPanel cardSequencePanel;
    private Business business;
    private StudentProfile studentProfile;
    
    // Grade to GPA mapping
    private static final Map<String, Double> GRADE_MAP = new HashMap<String, Double>() {{
        put("A", 4.0); put("A-", 3.7);
        put("B+", 3.3); put("B", 3.0); put("B-", 2.7);
        put("C+", 2.3); put("C", 2.0); put("C-", 1.7);
        put("F", 0.0);
    }};

    /**
     * Creates new Transcript Panel
     */
    public TranscriptJPanel(Business b, StudentProfile sp, JPanel clp) {
        // Validate inputs
        if (b == null || sp == null || clp == null) {
            throw new IllegalArgumentException("TranscriptJPanel requires non-null parameters");
        }
        
        this.business = b;
        this.studentProfile = sp;
        this.cardSequencePanel = clp;
        
        initComponents();
        populateSemesterDropdown();
        loadTranscriptData("All Semesters");
    }

    /**
     * Populate semester dropdown with available semesters
     */
    private void populateSemesterDropdown() {
        comboSemester.removeAllItems();
        comboSemester.addItem("All Semesters");
        
        // Get unique semesters from transcript
        Set<String> semesters = new HashSet<>();
        for (TranscriptRecord record : studentProfile.getTranscriptRecords()) {

            if (record != null && record.getSemester() != null) {
                semesters.add(record.getSemester());
            }
        }
        
        // Add to dropdown (sorted)
        List<String> sortedSemesters = new ArrayList<>(semesters);
        Collections.sort(sortedSemesters);
        for (String semester : sortedSemesters) {
            comboSemester.addItem(semester);
        }
    }

    /**
     * Load transcript data for selected semester
     */
    private void loadTranscriptData(String selectedSemester) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows
        
        if (studentProfile.getTranscript().isEmpty()) {
            Object[] emptyRow = {"No courses completed yet", "", "", "", "", "", ""};
            model.addRow(emptyRow);
            lblTermGPAValue.setText("N/A");
            lblOverallGPAValue.setText("N/A");
            lblAcademicStandingValue.setText("N/A");
            return;
        }
        
        double termQualityPoints = 0.0;
        int termCredits = 0;
        double overallQualityPoints = 0.0;
        int overallCredits = 0;
        
        // Group by semester
        Map<String, List<TranscriptRecord>> bySemester = new LinkedHashMap<>();
        
        for (TranscriptRecord record : studentProfile.getTranscriptRecords()) {

            if (record == null || record.getCourse() == null) continue;
            
            String semester = record.getSemester() != null ? record.getSemester() : "Unknown";
            
            // Filter by selected semester
            if (!selectedSemester.equals("All Semesters") && !semester.equals(selectedSemester)) {
                continue;
            }
            
            bySemester.computeIfAbsent(semester, k -> new ArrayList<>()).add(record);
        }
        
        // Display records grouped by semester
        for (Map.Entry<String, List<TranscriptRecord>> entry : bySemester.entrySet()) {
            String semester = entry.getKey();
            List<TranscriptRecord> records = entry.getValue();
            
            double semesterQP = 0.0;
            int semesterCreds = 0;
            
            for (TranscriptRecord record : records) {
                String courseId = record.getCourse().getCourseNumber();
                String courseName = record.getCourse().getName();
                int credits = record.getCourse().getCredits();
                String grade = record.getGrade() != null ? record.getGrade() : "N/A";
                
                double gradePoints = GRADE_MAP.getOrDefault(grade, 0.0);
                double qualityPoints = gradePoints * credits;
                
                semesterQP += qualityPoints;
                semesterCreds += credits;
                
                Object[] row = {semester, courseId, courseName, credits, grade, 
                               String.format("%.2f", gradePoints), ""};
                model.addRow(row);
            }
            
            // Calculate term GPA for this semester
            double termGPA = semesterCreds > 0 ? semesterQP / semesterCreds : 0.0;
            termQualityPoints += semesterQP;
            termCredits += semesterCreds;
            
            // Add semester summary row
            Object[] summaryRow = {semester + " Summary", "", "Term GPA: " + String.format("%.2f", termGPA), 
                                  semesterCreds, "", "", ""};
            model.addRow(summaryRow);
        }
        
        // Calculate overall GPA from ALL transcript records
        for (TranscriptRecord record : studentProfile.getTranscriptRecords()) {

            if (record == null || record.getCourse() == null) continue;
            
            int credits = record.getCourse().getCredits();
            String grade = record.getGrade() != null ? record.getGrade() : "N/A";
            double gradePoints = GRADE_MAP.getOrDefault(grade, 0.0);
            
            overallQualityPoints += gradePoints * credits;
            overallCredits += credits;
        }
        
        // Display GPA values
        double termGPA = termCredits > 0 ? termQualityPoints / termCredits : 0.0;
        double overallGPA = overallCredits > 0 ? overallQualityPoints / overallCredits : 0.0;
        
        lblTermGPAValue.setText(String.format("%.2f", termGPA));
        lblOverallGPAValue.setText(String.format("%.2f", overallGPA));
        
        // Determine academic standing
        String standing = determineAcademicStanding(termGPA, overallGPA);
        lblAcademicStandingValue.setText(standing);
        
        // Color code standing
        if (standing.equals("Good Standing")) {
            lblAcademicStandingValue.setForeground(new java.awt.Color(34, 139, 34)); // Green
        } else if (standing.equals("Academic Warning")) {
            lblAcademicStandingValue.setForeground(new java.awt.Color(255, 140, 0)); // Orange
        } else {
            lblAcademicStandingValue.setForeground(new java.awt.Color(220, 20, 60)); // Red
        }
    }
    
    /**
     * Determine academic standing based on GPA
     */
    private String determineAcademicStanding(double termGPA, double overallGPA) {
        if (termGPA >= 3.0 && overallGPA >= 3.0) {
            return "Good Standing";
        } else if (termGPA < 3.0 && overallGPA >= 3.0) {
            return "Academic Warning";
        } else {
            return "Academic Probation";
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSemester = new javax.swing.JLabel();
        comboSemester = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTermGPA = new javax.swing.JLabel();
        lblTermGPAValue = new javax.swing.JLabel();
        lblOverallGPA = new javax.swing.JLabel();
        lblOverallGPAValue = new javax.swing.JLabel();
        lblAcademicStanding = new javax.swing.JLabel();
        lblAcademicStandingValue = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("My Transcript");

        lblSemester.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSemester.setText("Filter by Semester:");

        comboSemester.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        comboSemester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Semesters" }));
        comboSemester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSemesterActionPerformed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Term", "Course ID", "Course Name", "Credits", "Grade", "Grade Points", "Academic Standing"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        lblTermGPA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTermGPA.setText("Term GPA:");

        lblTermGPAValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTermGPAValue.setForeground(new java.awt.Color(0, 102, 204));
        lblTermGPAValue.setText("0.00");

        lblOverallGPA.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblOverallGPA.setText("Overall GPA:");

        lblOverallGPAValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblOverallGPAValue.setForeground(new java.awt.Color(0, 102, 204));
        lblOverallGPAValue.setText("0.00");

        lblAcademicStanding.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAcademicStanding.setText("Academic Standing:");

        lblAcademicStandingValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAcademicStandingValue.setText("Good Standing");

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSemester)
                                .addGap(18, 18, 18)
                                .addComponent(comboSemester, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTermGPA)
                                .addGap(18, 18, 18)
                                .addComponent(lblTermGPAValue, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(lblOverallGPA)
                                .addGap(18, 18, 18)
                                .addComponent(lblOverallGPAValue, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(lblAcademicStanding)
                                .addGap(18, 18, 18)
                                .addComponent(lblAcademicStandingValue))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSemester)
                    .addComponent(comboSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTermGPA)
                    .addComponent(lblTermGPAValue)
                    .addComponent(lblOverallGPA)
                    .addComponent(lblOverallGPAValue)
                    .addComponent(lblAcademicStanding)
                    .addComponent(lblAcademicStandingValue))
                .addGap(18, 18, 18)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>                        

    /**
     * Handle semester filter change
     */
    private void comboSemesterActionPerformed(java.awt.event.ActionEvent evt) {                                              
        String selectedSemester = (String) comboSemester.getSelectedItem();
        if (selectedSemester != null) {
            loadTranscriptData(selectedSemester);
        }
    }                                             

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

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox<String> comboSemester;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAcademicStanding;
    private javax.swing.JLabel lblAcademicStandingValue;
    private javax.swing.JLabel lblOverallGPA;
    private javax.swing.JLabel lblOverallGPAValue;
    private javax.swing.JLabel lblSemester;
    private javax.swing.JLabel lblTermGPA;
    private javax.swing.JLabel lblTermGPAValue;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration                   
}