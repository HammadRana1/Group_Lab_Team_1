//Thevenin_10-25-_"Simple_manage_profile_panel_safely_compiles_and_edits_student_person"
package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Profiles.StudentProfile;
import Business.Person.Person;

import javax.swing.*;
import java.awt.*;

public class ManageProfileJPanel extends JPanel {

    private final Business business;
    private final StudentProfile student;
    private final JPanel cardPanel;

    // UI fields
    private JButton btnUpdate;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6;
    private JTextField txtPersonId, txtFirstName, txtLastName, txtEmail, txtPhone, txtDept;

    //Thevenin_10-25-_"Constructor_matches_calls_from_StudentWorkAreaJPanel"
    public ManageProfileJPanel(Business business, StudentProfile student, JPanel cardPanel) {
        if (business == null || student == null || cardPanel == null) {
            throw new IllegalArgumentException("ManageProfileJPanel requires non-null args");
        }
        this.business = business;
        this.student = student;
        this.cardPanel = cardPanel;

        initComponents();
        populateFromStudent();
    }

    //Thevenin_10-25-_"Initialize_all_components_and_layout_(GroupLayout)_so_it_compiles_cleanly"
    private void initComponents() {
        jLabel1 = new JLabel("Manage Profile");
        jLabel1.setFont(new Font("Dialog", Font.BOLD, 20));

        jLabel2 = new JLabel("Person ID:");
        jLabel3 = new JLabel("First Name:");
        jLabel4 = new JLabel("Last Name:");
        jLabel5 = new JLabel("Email:");
        jLabel6 = new JLabel("Phone:");
        JLabel jLabelDept = new JLabel("Department:");

        txtPersonId  = new JTextField(20);
        txtFirstName = new JTextField(20);
        txtLastName  = new JTextField(20);
        txtEmail     = new JTextField(20);
        txtPhone     = new JTextField(20);
        txtDept      = new JTextField(20);

        txtPersonId.setEditable(false); // id is not editable

        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> doUpdate());

        // Layout
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabelDept))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(txtPersonId)
                        .addComponent(txtFirstName)
                        .addComponent(txtLastName)
                        .addComponent(txtEmail)
                        .addComponent(txtPhone)
                        .addComponent(txtDept)))
                .addComponent(btnUpdate, GroupLayout.Alignment.TRAILING)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(12)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2).addComponent(txtPersonId))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3).addComponent(txtFirstName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4).addComponent(txtLastName))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5).addComponent(txtEmail))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6).addComponent(txtPhone))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDept).addComponent(txtDept))
                .addGap(12)
                .addComponent(btnUpdate)
        );
    }

    //Thevenin_10-25-_"Load_student_data_into_fields"
    private void populateFromStudent() {
        Person p = student.getPerson();
        if (p == null) return;
        txtPersonId.setText(p.getPersonId());
        txtFirstName.setText(p.getFirstName());
        txtLastName.setText(p.getLastName());
        txtEmail.setText(p.getEmail());
        txtPhone.setText(p.getPhone());
        txtDept.setText(p.getDepartment());
    }

    //Thevenin_10-25-_"Save_edits_back_to_student_person"
    private void doUpdate() {
        try {
            Person p = student.getPerson();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "No person found for student.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            p.setFirstName(txtFirstName.getText().trim());
            p.setLastName(txtLastName.getText().trim());
            p.setEmail(txtEmail.getText().trim());
            p.setPhone(txtPhone.getText().trim());
            p.setDepartment(txtDept.getText().trim());
            JOptionPane.showMessageDialog(this, "Profile updated.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
