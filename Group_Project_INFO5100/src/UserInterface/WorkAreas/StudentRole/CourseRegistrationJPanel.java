//Thevenin_10-25-_"CourseRegistration_panel_with_search_enroll_drop_and_rules"
package UserInterface.WorkAreas.StudentRole;

import Business.Business;
import Business.Enrollment.EnrollmentService;
import Business.Profiles.StudentProfile;
import Business.University.CourseSchedule.CourseOffer;
import Business.University.CourseSchedule.CourseSchedule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class CourseRegistrationJPanel extends JPanel {

    //Thevenin_10-25-_"References_passed_from_work_area"
    private final Business business;
    private final StudentProfile student;
    private final JPanel cardPanel;

    //Thevenin_10-25-_"Active_semester_schedule_set_from_work_area"
    private CourseSchedule schedule;

    //Thevenin_10-25-_"Enrollment_rules_referee"
    private final EnrollmentService enrollmentService = new EnrollmentService();

    //Thevenin_10-25-_"Search_controls_and_table"
    private final JTextField txtSearch = new JTextField(18);
    private final JComboBox<String> cmbSearchBy =
            new JComboBox<>(new String[]{"Course ID","Course Name","Faculty"});
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Course ID","Course Name","Faculty","Credits","Capacity","Enrolled","Open?"}, 0) {
        public boolean isCellEditable(int r,int c){ return false; }
    };
    private final JTable tbl = new JTable(tableModel);

    //Thevenin_10-25-_"Constructor_matches_your_existing_pattern_(business,student,cardPanel)"
    public CourseRegistrationJPanel(Business business, StudentProfile student, JPanel cardPanel) {
        this.business = business;
        this.student = student;
        this.cardPanel = cardPanel;

        buildUI();
    }

    //Thevenin_10-25-_"Setter_called_by_work_area_to_provide_active_schedule"
    public void setSchedule(CourseSchedule schedule){
        this.schedule = schedule;
        loadOffers(null);
    }

    // ---------- UI ----------
    private void buildUI() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnSearch = new JButton("Search");
        JButton btnClear  = new JButton("Clear");
        top.add(new JLabel("Search by:"));
        top.add(cmbSearchBy);
        top.add(txtSearch);
        top.add(btnSearch);
        top.add(btnClear);
        btnSearch.addActionListener(e -> doSearch());
        btnClear.addActionListener(e -> { txtSearch.setText(""); loadOffers(null); });

        JScrollPane center = new JScrollPane(tbl);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEnroll = new JButton("Enroll");
        JButton btnDrop   = new JButton("Drop");
        bottom.add(btnEnroll);
        bottom.add(btnDrop);
        btnEnroll.addActionListener(e -> enrollSelected());
        btnDrop.addActionListener(e -> dropSelected());

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    // ---------- Data Loader ----------
    private void loadOffers(List<CourseOffer> filtered) {
        tableModel.setRowCount(0);
        if (schedule == null) return;

        List<CourseOffer> list = (filtered==null) ? schedule.getOffers() : filtered;
        for (CourseOffer co : list) {
            var c = co.getCourse();
            tableModel.addRow(new Object[]{
                    c.getCourseNumber(),
                    c.getName(),
                    co.getFacultyName(),
                    c.getCredits(),
                    co.getCapacity(),
                    co.getEnrolled(),
                    co.isEnrollmentOpen() ? "Yes" : "No"
            });
        }
    }

    // ---------- Search ----------
    private void doSearch() {
        if (schedule == null) return;
        String q = txtSearch.getText().trim().toLowerCase();
        if (q.isEmpty()) { loadOffers(null); return; }
        String mode = (String) cmbSearchBy.getSelectedItem();
        List<CourseOffer> base = schedule.getOffers();
        List<CourseOffer> out = base.stream().filter(co -> {
            var course = co.getCourse();
            String id  = course.getCourseNumber().toLowerCase();
            String name= course.getName().toLowerCase();
            String fac = co.getFacultyName()==null ? "" : co.getFacultyName().toLowerCase();
            switch (mode) {
                case "Course ID":   return id.contains(q);
                case "Course Name": return name.contains(q);
                case "Faculty":     return fac.contains(q);
                default: return true;
            }
        }).collect(Collectors.toList());
        loadOffers(out);
    }

    // ---------- Helpers ----------
    private CourseOffer selectedOffer() {
        int r = tbl.getSelectedRow();
        if (r < 0 || schedule == null) return null;
        String courseId = String.valueOf(tableModel.getValueAt(r, 0));
        for (CourseOffer co : schedule.getOffers()) {
            if (co.getCourse().getCourseNumber().equalsIgnoreCase(courseId)) return co;
        }
        return null;
    }

    // ---------- Actions ----------
    private void enrollSelected() {
        CourseOffer co = selectedOffer();
        if (co == null) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }

        if (!enrollmentService.canEnroll(student, co)) {
            JOptionPane.showMessageDialog(this,
                "Cannot enroll:\n• Enrollment may be closed\n• No seats\n• Or credit limit (8) would be exceeded");
            return;
        }
        try {
            enrollmentService.enroll(student, co);
            JOptionPane.showMessageDialog(this, "Enrolled! Tuition charged.");
            doSearch(); // refresh table counts
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enroll failed: " + ex.getMessage());
        }
    }

    private void dropSelected() {
        CourseOffer co = selectedOffer();
        if (co == null) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }
        enrollmentService.drop(student, co);
        JOptionPane.showMessageDialog(this, "Dropped. Refund processed.");
        doSearch(); // refresh table counts
    }
}
