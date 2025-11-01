/*
 * CourseWorkJPanel.java
 * Course work management for student - submit assignments and track progress
 * Author: Syrill T
 * Date: 10/26/2025
 */
package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import Business.University.CourseSchedule.CourseOffer;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CourseWorkJPanel extends javax.swing.JPanel {

    private JPanel cardSequencePanel;
    private Business business;
    private StudentProfile studentProfile;

    public CourseWorkJPanel(Business b, StudentProfile sp, JPanel clp) {
        if (b == null || sp == null || clp == null) {
            throw new IllegalArgumentException("CourseWorkJPanel requires non-null parameters");
        }

        this.business = b;
        this.studentProfile = sp;
        this.cardSequencePanel = clp;

        initComponents();
        loadEnrolledCourses();
    }

    private void loadEnrolledCourses() {
        DefaultTableModel model = (DefaultTableModel) tableCourses.getModel();
        model.setRowCount(0);

        List<CourseOffer> enrolledOffers = studentProfile.getCurrentCourseOffers();

        if (enrolledOffers == null || enrolledOffers.isEmpty()) {
            Object[] emptyRow = {"No enrolled courses", "", "", "", ""};
            model.addRow(emptyRow);
            return;
        }

        for (CourseOffer offer : enrolledOffers) {
            if (offer == null || offer.getCourse() == null) continue;

            String courseId = offer.getCourse().getCourseNumber();
            String courseName = offer.getCourse().getName();
            String faculty = (offer.getFacultyProfile() != null && offer.getFacultyProfile().getPerson() != null)
                    ? offer.getFacultyProfile().getPerson().getName()
                    : "TBA";
            int credits = offer.getCourse().getCredits();
            String semester = offer.getSemester();

            Object[] row = {courseId, courseName, faculty, credits, semester};
            model.addRow(row);
        }
    }

    private void loadAssignments() {
        int selectedRow = tableCourses.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tableAssignments.getModel();
        model.setRowCount(0);

        // Fake data — replace with your logic later
        Object[][] sampleAssignments = {
            {"Assignment 1", "Quiz", "10/01/2025", "Submitted", "95%"},
            {"Assignment 2", "Homework", "10/15/2025", "Submitted", "88%"},
            {"Midterm Project", "Project", "11/01/2025", "Not Submitted", "-"},
            {"Assignment 3", "Homework", "11/15/2025", "Not Submitted", "-"},
            {"Final Project", "Project", "12/15/2025", "Not Submitted", "-"}
        };

        for (Object[] assignment : sampleAssignments) {
            model.addRow(assignment);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        lblMyCourses = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCourses = new javax.swing.JTable();
        lblAssignments = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAssignments = new javax.swing.JTable();
        btnViewAssignments = new javax.swing.JButton();
        btnSubmitWork = new javax.swing.JButton();
        btnViewGrades = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitle.setText("My Course Work");

        lblMyCourses.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblMyCourses.setText("My Enrolled Courses:");

        tableCourses.setFont(new java.awt.Font("Segoe UI", 0, 12));
        tableCourses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Course ID", "Course Name", "Faculty", "Credits", "Semester"
            }
        ) {
            boolean[] canEdit = new boolean [] { false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        tableCourses.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tableCourses);

        lblAssignments.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblAssignments.setText("Course Assignments:");

        tableAssignments.setFont(new java.awt.Font("Segoe UI", 0, 12));
        tableAssignments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Assignment Name", "Type", "Due Date", "Status", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] { false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        tableAssignments.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tableAssignments);

        btnViewAssignments.setBackground(new java.awt.Color(102, 153, 255));
        btnViewAssignments.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnViewAssignments.setForeground(new java.awt.Color(255, 255, 255));
        btnViewAssignments.setText("View Assignments");
        btnViewAssignments.addActionListener(evt -> btnViewAssignmentsActionPerformed(evt));

        btnSubmitWork.setBackground(new java.awt.Color(34, 139, 34));
        btnSubmitWork.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnSubmitWork.setForeground(new java.awt.Color(255, 255, 255));
        btnSubmitWork.setText("Submit Assignment");
        btnSubmitWork.addActionListener(evt -> btnSubmitWorkActionPerformed(evt));

        btnViewGrades.setBackground(new java.awt.Color(102, 153, 255));
        btnViewGrades.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnViewGrades.setForeground(new java.awt.Color(255, 255, 255));
        btnViewGrades.setText("View Grades");
        btnViewGrades.addActionListener(evt -> btnViewGradesActionPerformed(evt));

        btnBack.setBackground(new java.awt.Color(204, 204, 204));
        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 12));
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addComponent(jSeparator1)
                        .addComponent(jScrollPane2)
                        .addComponent(jSeparator2)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTitle)
                                .addComponent(lblMyCourses)
                                .addComponent(lblAssignments)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(btnViewAssignments, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSubmitWork, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnViewGrades, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 224, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblTitle)
                    .addGap(18, 18, 18)
                    .addComponent(lblMyCourses)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lblAssignments)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnViewAssignments, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSubmitWork, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnViewGrades, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(20, Short.MAX_VALUE))
        );
    }

    private void btnViewAssignmentsActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tableCourses.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course first!");
            return;
        }

        loadAssignments();
    }

    private void btnSubmitWorkActionPerformed(java.awt.event.ActionEvent evt) {
        int courseRow = tableCourses.getSelectedRow();
        int assignmentRow = tableAssignments.getSelectedRow();

        if (courseRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course first!");
            return;
        }

        if (assignmentRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an assignment to submit!");
            return;
        }

        String assignmentName = tableAssignments.getValueAt(assignmentRow, 0).toString();
        String status = tableAssignments.getValueAt(assignmentRow, 3).toString();

        if (status.equals("Submitted")) {
            JOptionPane.showMessageDialog(this, "This assignment has already been submitted!", "Already Submitted", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String submission = JOptionPane.showInputDialog(this, "Enter your submission text for " + assignmentName + ":", "Submit Assignment", JOptionPane.PLAIN_MESSAGE);

        if (submission != null && !submission.trim().isEmpty()) {
            tableAssignments.setValueAt("Submitted", assignmentRow, 3);
            JOptionPane.showMessageDialog(this, "Assignment submitted successfully!\nYour work has been recorded.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void btnViewGradesActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tableCourses.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course first!");
            return;
        }

        String courseId = tableCourses.getValueAt(selectedRow, 0).toString();
        String courseName = tableCourses.getValueAt(selectedRow, 1).toString();

        DefaultTableModel model = (DefaultTableModel) tableAssignments.getModel();
        int totalAssignments = model.getRowCount();
        int submitted = 0;
        double totalGrade = 0.0;
        int gradedAssignments = 0;

        for (int i = 0; i < totalAssignments; i++) {
            String status = model.getValueAt(i, 3).toString();
            String grade = model.getValueAt(i, 4).toString();

            if (status.equals("Submitted")) {
                submitted++;
            }

            if (!grade.equals("-")) {
                try {
                    double gradeValue = Double.parseDouble(grade.replace("%", ""));
                    totalGrade += gradeValue;
                    gradedAssignments++;
                } catch (NumberFormatException e) {
                    // Ignore invalid grades
                }
            }
        }

        double averageGrade = gradedAssignments > 0 ? totalGrade / gradedAssignments : 0.0;

        String message = "Grade Summary for " + courseId + " - " + courseName + "\n\n" +
                "Total Assignments: " + totalAssignments + "\n" +
                "Submitted: " + submitted + "\n" +
                "Graded: " + gradedAssignments + "\n" +
                "Average Grade: " + String.format("%.1f%%", averageGrade) + "\n\n" +
                "Status: " + (submitted == totalAssignments ? "All work submitted!" :
                (totalAssignments - submitted) + " assignments pending");

        JOptionPane.showMessageDialog(this, message, "Grade Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            CardLayout layout = (CardLayout) cardSequencePanel.getLayout();
            layout.show(cardSequencePanel, "StudentWorkArea");
        } catch (Exception e) {
            System.err.println("Error navigating back: " + e.getMessage());
        }
    }

    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSubmitWork;
    private javax.swing.JButton btnViewAssignments;
    private javax.swing.JButton btnViewGrades;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAssignments;
    private javax.swing.JLabel lblMyCourses;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tableAssignments;
    private javax.swing.JTable tableCourses;
}
