/*
 * ManageProfileJPanel.java
 * Profile management for student - view and update personal information
 * Author: Syrill T
 * Date: 10/26/2025
 */
package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import Business.Person.Person;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.table.DefaultTableModel;

/**
 * Student Profile Management Panel
 * Allows students to view and update their personal information
 */
public class ManageProfileJPanel extends javax.swing.JPanel {

    private JPanel cardSequencePanel;
    private Business business;
    private StudentProfile studentProfile;
    private Person person;

    /**
     * Creates new Manage Profile Panel
     */
    public ManageProfileJPanel(Business b, StudentProfile sp, JPanel clp) {
        // Validate inputs
        if (b == null || sp == null || clp == null) {
            throw new IllegalArgumentException("ManageProfileJPanel requires non-null parameters");
        }
        
        this.business = b;
        this.studentProfile = sp;
        this.cardSequencePanel = clp;
        this.person = sp.getPerson();
        
        initComponents();
        populateTable();
        loadStudentData();
    }

    /**
     * Populate table with student profile data
     */
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows
        
        // Add current student's data
        Object[] row = new Object[6];
        row[0] = person.getFirstName() + " " + person.getLastName();
        row[1] = person.getPersonId();
        row[2] = person.getEmail() != null ? person.getEmail() : "N/A";
        row[3] = person.getPhone() != null ? person.getPhone() : "N/A";
        row[4] = "MSIS"; // Program
        row[5] = studentProfile.getTotalEarnedCredits() + " credits";
        model.addRow(row);
    }

    /**
     * Load student profile data into form fields
     */
    private void loadStudentData() {
        if (person == null) {
            JOptionPane.showMessageDialog(this, 
                "Error: Unable to load profile data", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Populate fields with current data
        fieldFirstName.setText(person.getFirstName() != null ? person.getFirstName() : "");
        fieldLastName.setText(person.getLastName() != null ? person.getLastName() : "");
        fieldStudentID.setText(person.getPersonId() != null ? person.getPersonId() : "");
        fieldEmail.setText(person.getEmail() != null ? person.getEmail() : "");
        fieldPhone.setText(person.getPhone() != null ? person.getPhone() : "");
        fieldProgram.setText("MSIS");
        
        // Make Student ID and Program read-only
        fieldStudentID.setEditable(false);
        fieldProgram.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblFirstName = new javax.swing.JLabel();
        fieldFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        fieldLastName = new javax.swing.JTextField();
        lblStudentID = new javax.swing.JLabel();
        fieldStudentID = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        fieldEmail = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        fieldPhone = new javax.swing.JTextField();
        lblProgram = new javax.swing.JLabel();
        fieldProgram = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setText("My Profile");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Full Name", "Student ID", "Email", "Phone", "Program", "Credits Earned"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        lblFirstName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFirstName.setText("First Name");

        fieldFirstName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblLastName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLastName.setText("Last Name");

        fieldLastName.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblStudentID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblStudentID.setText("Student ID");

        fieldStudentID.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEmail.setText("Email");

        fieldEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblPhone.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPhone.setText("Phone Number");

        fieldPhone.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblProgram.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblProgram.setText("Program");

        fieldProgram.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        btnUpdate.setBackground(new java.awt.Color(102, 153, 255));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update Profile");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(204, 204, 204));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setText("Clear Fields");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFirstName)
                                    .addComponent(lblEmail)
                                    .addComponent(lblProgram))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fieldFirstName)
                                    .addComponent(fieldEmail)
                                    .addComponent(fieldProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLastName)
                                    .addComponent(lblPhone)
                                    .addComponent(lblStudentID))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fieldLastName)
                                    .addComponent(fieldPhone)
                                    .addComponent(fieldStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(fieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLastName)
                    .addComponent(fieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(fieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPhone)
                    .addComponent(fieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProgram)
                    .addComponent(fieldProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStudentID)
                    .addComponent(fieldStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(150, Short.MAX_VALUE))
        );
    }// </editor-fold>                        

    /**
     * Update profile information
     */
    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {                                          
        try {
            // Validate inputs
            String firstName = fieldFirstName.getText();
            String lastName = fieldLastName.getText();
            String email = fieldEmail.getText();
            String phone = fieldPhone.getText();
            
            // Check for empty fields
            if (firstName == null || firstName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "First name cannot be empty!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (lastName == null || lastName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Last name cannot be empty!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (email == null || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Email cannot be empty!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Basic email validation
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid email address!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Update person object
            person.setFirstName(firstName.trim());
            person.setLastName(lastName.trim());
            person.setEmail(email.trim());
            person.setPhone(phone != null ? phone.trim() : "");
            
            // Refresh the table
            populateTable();
            
            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Profile updated successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            System.err.println("Error updating profile: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error updating profile. Please try again.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                         

    /**
     * Clear all input fields
     */
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {                                         
        fieldFirstName.setText("");
        fieldLastName.setText("");
        fieldEmail.setText("");
        fieldPhone.setText("");
        // Don't clear Student ID or Program (they're read-only)
        loadStudentData(); // Reload original data
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
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTextField fieldEmail;
    private javax.swing.JTextField fieldFirstName;
    private javax.swing.JTextField fieldLastName;
    private javax.swing.JTextField fieldPhone;
    private javax.swing.JTextField fieldProgram;
    private javax.swing.JTextField fieldStudentID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblProgram;
    private javax.swing.JLabel lblStudentID;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration                   
}