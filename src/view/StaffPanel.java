package view;

import model.Staff;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StaffPanel extends JPanel {

    public StaffPanel(DataStore store) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] cols = {
                "Staff ID", "First Name", "Last Name", "Role", "Department",
                "Facility ID", "Phone", "Email", "Employment Status",
                "Start Date", "Line Manager", "Access Level"
        };

        DefaultTableModel tableModel = new DefaultTableModel(cols, 0);

        for (Staff s : store.getStaff().values()) {
            tableModel.addRow(new Object[]{
                    s.getStaffId(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getRole(),
                    s.getDepartment(),
                    s.getFacilityId(),
                    s.getPhoneNumber(),
                    s.getEmail(),
                    s.getEmploymentStatus(),
                    s.getStartDate(),
                    s.getLineManager(),
                    s.getAccessLevel()
            });
        }

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table));
    }
}
