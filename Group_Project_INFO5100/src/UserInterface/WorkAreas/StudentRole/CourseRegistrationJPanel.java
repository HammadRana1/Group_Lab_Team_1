/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
/*
 * CourseRegistrationJPanel.java
 * Course registration for student - search, enroll, and drop courses
 * Author: Syrill T
 * Date: 10/26/2025
 */
package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import Business.University.CourseSchedule.CourseOffer;
import Business.University.CourseCatalog.Course;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.table.DefaultTableModel;
import java.util.*;

/**
 * Course Registration Panel
 * Allows students to search for courses, enroll, and drop courses
 * Enforces 8 credit hour limit per semester
 */
public class CourseRegistrationJPanel extends javax.swing.JPanel {

    private JPanel cardSequencePanel;
    private Business business;
    private StudentProfile studentProfile;
    
    private static final int MAX_CREDITS_PER_SEMESTER = 8;

    /**
     * Creates new Course Registration Panel
     */
    public CourseRegistrationJPanel(Business b, StudentProfile sp, JPanel clp) {
        // Validate inputs
        if (b == null || sp == null || clp == null) {
            throw new IllegalArgumentException("CourseRegistrationJPanel requires non-null parameters");
        }
        
        this.business = b;
        this.studentProfile = sp;
        this.cardSequencePanel = clp;
        
        initComponents();
        populateSemesterDropdown();
        loadCourseOffers();
    }

    /**
     * Populate semester dropdown
     */
    private void populateSemesterDropdown() {
        comboSemester.removeAllItems();
        
        // Add common semesters (you can get this from business object if available)
        comboSemester.addItem("Fall 2025");
        comboSemester.addItem("Spring 2026");
        comboSemester.addItem("Summer 2026");
        comboSemester.addItem("Fall 2026");
    }

    /**
     * Load available course offers for selected semester
     */
    private void loadCourseOffers() {
        String selectedSemester = (String) comboSemester.getSelectedItem();
        if (selectedSemester == null) return;
        
        DefaultTableModel model = (DefaultTableModel) tableCourses.getModel();
        model.setRowCount(0);
        
        // Get course offers from business (you'll need to implement this getter)
        // For now, showing example structure
        if (business.getCourseSchedule() != null) {
            for (CourseOffer offer : business.getCourseSchedule().getCourseOffers()) {
                if (offer != null && selectedSemester.equals(offer.getSemester())) {
                    Course course = offer.getCourse();
                    String faculty = offer.getFacultyProfile() != null ? 
                        offer.getFacultyProfile().getPerson().getFirstName() + " " +
                        offer.getFacultyProfile().getPerson().getLastName() : "TBA";
                    
                    int enrolled = offer.getSeatAssignments() != null ? offer.getSeatAssignments().size() : 0;
                    int capacity = 30; // You can get this from CourseOffer if available
                    
                    Object[] row = {
                        course.getCourseNumber(),
                        course.getName(),
                        faculty,
                        course.getCredits(),
                        enrolled + "/" + capacity,
                        offer.getSemester()
                    };
                    model.addRow(row);
                }
            }
        }
        
        if (model.getRowCount() == 0) {
            Object[] emptyRow = {"No courses available", "", "", "", "", ""};
            model.addRow(emptyRow);
        }
    }
    
    /**
     * Search courses by different criteria
     */
    private void searchCourses() {
        String searchText = txtSearch.getText().trim().toLowerCase();
        String searchType = (String) comboSearchType.getSelectedItem();
        String selectedSemester = (String) comboSemester.getSelectedItem();
        
        if (searchText.isEmpty()) {
            loadCourseOffers();
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) tableCourses.getModel();
        model.setRowCount(0);
        
        if (business.getCourseSchedule() != null) {
            for (CourseOffer offer : business.getCourseSchedule().getCourseOffers()) {
                if (offer == null || !selectedSemester.equals(offer.getSemester())) continue;
                
                Course course = offer.getCourse();
                String faculty = offer.getFacultyProfile() != null ? 
                    offer.getFacultyProfile().getPerson().getName() : "";
                
                boolean matches = false;
                
                switch (searchType) {
                    case "Course ID":
                        matches = course.getCourseNumber().toLowerCase().contains(searchText);
                        break;
                    case "Course Name":
                        matches = course.getName().toLowerCase().contains(searchText);
                        break;
                    case "Faculty":
                        matches = faculty.toLowerCase().contains(searchText);
                        break;
                }
                
                if (matches) {
                    int enrolled = offer.getSeatAssignments() != null ? offer.getSeatAssignments().size() : 0;
                    int capacity = 30;
                    
                    Object[] row = {
                        course.getCourseNumber(),
                        course.getName(),
                        faculty,
                        course.getCredits(),
                        enrolled + "/" + capacity,
                        offer.getSemester()
                    };
                    model.addRow(row);
                }
            }
        }
        
        if (model.getRowCount() == 0) {
            Object[] emptyRow = {"No matching courses found", "", "", "", "", ""};
            model.addRow(emptyRow);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblSemester = new javax.swing.JLabel();
        comboSemester = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCourses = new javax.swing.JTable();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        comboSearchType = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        btnEnroll = new javax.swing.JButton();
        btnDrop = new javax.swing.JButton();
        btnViewMyEnrollments = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblCredits = new javax.swing.JLabel();
        lblCreditsValue = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("Course Registration");

        lblSemester.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSemester.setText("Semester:");

        comboSemester.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        comboSemester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSemesterActionPerformed(evt);
            }
        });

        tableCourses.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tableCourses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course ID", "Course Name", "Faculty", "Credits", "Enrolled/Capacity", "Semester"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableCourses);

        lblSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSearch.setText("Search:");

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        comboSearchType.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        comboSearchType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Course ID", "Course Name", "Faculty" }));

        btnSearch.setBackground(new java.awt.Color(102, 153, 255));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnEnroll.setBackground(new java.awt.Color(34, 139, 34));
        btnEnroll.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEnroll.setForeground(new java.awt.Color(255, 255, 255));
        btnEnroll.setText("Enroll in Course");
        btnEnroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnrollActionPerformed(evt);
            }
        });

        btnDrop.setBackground(new java.awt.Color(220, 20, 60));
        btnDrop.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDrop.setForeground(new java.awt.Color(255, 255, 255));
        btnDrop.setText("Drop Course");
        btnDrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDropActionPerformed(evt);
            }
        });

        btnViewMyEnrollments.setBackground(new java.awt.Color(102, 153, 255));
        btnViewMyEnrollments.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnViewMyEnrollments.setForeground(new java.awt.Color(255, 255, 255));
        btnViewMyEnrollments.setText("View My Enrollments");
        btnViewMyEnrollments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewMyEnrollmentsActionPerformed(evt);
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

        lblCredits.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCredits.setText("Current Semester Credits:");

        lblCreditsValue.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCreditsValue.setForeground(new java.awt.Color(0, 102, 204));
        lblCreditsValue.setText("0 / 8");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSemester)
                                .addGap(18, 18, 18)
                                .addComponent(comboSemester, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(lblCredits)
                                .addGap(18, 18, 18)
                                .addComponent(lblCreditsValue))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSearch)
                                .addGap(18, 18, 18)
                                .addComponent(comboSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnEnroll, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnViewMyEnrollments, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 6, Short.MAX_VALUE)))
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
                    .addComponent(comboSemester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCredits)
                    .addComponent(lblCreditsValue))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(comboSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnroll, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnViewMyEnrollments, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>                        

    /**
     * Handle semester change
     */
    private void comboSemesterActionPerformed(java.awt.event.ActionEvent evt) {                                              
        loadCourseOffers();
        updateCreditsDisplay();
    }                                             

    /**
     * Search button handler
     */
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {                                          
        searchCourses();
    }                                         

    /**
     * Enroll in selected course
     */
    private void btnEnrollActionPerformed(java.awt.event.ActionEvent evt) {                                          
        int selectedRow = tableCourses.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course to enroll in!");
            return;
        }
        
        String courseId = tableCourses.getValueAt(selectedRow, 0).toString();
        String semester = (String) comboSemester.getSelectedItem();
        
        // Find the course offer
        CourseOffer selectedOffer = null;
        if (business.getCourseSchedule() != null) {
            for (CourseOffer offer : business.getCourseSchedule().getCourseOffers()) {
                if (offer.getCourse().getCourseNumber().equals(courseId) && 
                    offer.getSemester().equals(semester)) {
                    selectedOffer = offer;
                    break;
                }
            }
        }
        
        if (selectedOffer == null) {
            JOptionPane.showMessageDialog(this, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if already enrolled
        if (studentProfile.getCurrentEnrollments().contains(selectedOffer)) {
            JOptionPane.showMessageDialog(this, "You are already enrolled in this course!");
            return;
        }
        
        // Check credit limit
        int currentCredits = studentProfile.termCredits(semester);
        int courseCredits = selectedOffer.getCourse().getCredits();
        
        if (currentCredits + courseCredits > MAX_CREDITS_PER_SEMESTER) {
            JOptionPane.showMessageDialog(this, 
                "Cannot enroll! You have " + currentCredits + " credits.\n" +
                "This course is " + courseCredits + " credits.\n" +
                "Maximum allowed: " + MAX_CREDITS_PER_SEMESTER + " credits per semester.",
                "Credit Limit Exceeded", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Enroll
        studentProfile.enrollIn(selectedOffer);
        selectedOffer.assignSeat(studentProfile);
        
        JOptionPane.showMessageDialog(this, 
            "Successfully enrolled in " + courseId + "!",
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        loadCourseOffers();
        updateCreditsDisplay();
    }                                         

    /**
     * Drop selected course
     */
    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {                                        
        int selectedRow = tableCourses.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a course to drop!");
            return;
        }
        
        String courseId = tableCourses.getValueAt(selectedRow, 0).toString();
        String semester = (String) comboSemester.getSelectedItem();
        
        // Find the course offer
        CourseOffer selectedOffer = null;
        if (business.getCourseSchedule() != null) {
            for (CourseOffer offer : business.getCourseSchedule().getCourseOffers()) {
                if (offer.getCourse().getCourseNumber().equals(courseId) && 
                    offer.getSemester().equals(semester)) {
                    selectedOffer = offer;
                    break;
                }
            }
        }
        
        if (selectedOffer == null) {
            JOptionPane.showMessageDialog(this, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if enrolled
        if (!studentProfile.getCurrentEnrollments().contains(selectedOffer)) {
            JOptionPane.showMessageDialog(this, "You are not enrolled in this course!");
            return;
        }
        
        // Confirm drop
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to drop " + courseId + "?",
            "Confirm Drop", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            studentProfile.dropFrom(selectedOffer);
            selectedOffer.dropSeat(studentProfile);
            
            JOptionPane.showMessageDialog(this, 
                "Successfully dropped " + courseId + "!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            loadCourseOffers();
            updateCreditsDisplay();
        }
    }                                       

    /**
     * View current enrollments
     */
    private void btnViewMyEnrollmentsActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        String semester = (String) comboSemester.getSelectedItem();
        List<CourseOffer> enrollments = studentProfile.getCurrentEnrollments();
        
        StringBuilder message = new StringBuilder("Your enrollments for " + semester + ":\n\n");
        int totalCredits = 0;
        
        boolean hasEnrollments = false;
        for (CourseOffer offer : enrollments) {
            if (offer.getSemester().equals(semester)) {
                hasEnrollments = true;
                message.append(offer.getCourse().getCourseNumber())
                       .append(" - ")
                       .append(offer.getCourse().getName())
                       .append(" (")
                       .append(offer.getCourse().getCredits())
                       .append(" credits)\n");
                totalCredits += offer.getCourse().getCredits();
            }
        }
        
        if (!hasEnrollments) {
            message.append("No enrollments for this semester.");
        } else {
            message.append("\nTotal Credits: ").append(totalCredits).append(" / ").append(MAX_CREDITS_PER_SEMESTER);
        }
        
        JOptionPane.showMessageDialog(this, message.toString(), 
            "My Enrollments", JOptionPane.INFORMATION_MESSAGE);
    }                                                    

    /**
     * Update credits display
     */
    private void updateCreditsDisplay() {
        String semester = (String) comboSemester.getSelectedItem();
        int credits = studentProfile.termCredits(semester);
        lblCreditsValue.setText(credits + " / " + MAX_CREDITS_PER_SEMESTER);
        
        if (credits >= MAX_CREDITS_PER_SEMESTER) {
            lblCreditsValue.setForeground(new java.awt.Color(220, 20, 60)); // Red
        } else {
            lblCreditsValue.setForeground(new java.awt.Color(0, 102, 204)); // Blue
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
    private javax.swing.JButton btnDrop;
    private javax.swing.JButton btnEnroll;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewMyEnrollments;
    private javax.swing.JComboBox<String> comboSearchType;
    private javax.swing.JComboBox<String> comboSemester;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCredits;
    private javax.swing.JLabel lblCreditsValue;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblSemester;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tableCourses;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration                   
}