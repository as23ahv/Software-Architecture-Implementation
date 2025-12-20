package view;

import model.Staff;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StaffPanel extends JPanel {

    public StaffPanel(DataStore store) {

        String[] columns = {"Staff ID", "Name", "Role", "Department", "Facility ID"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Staff s : store.getStaff().values()) {
            tableModel.addRow(new Object[]{
                    s.getStaffId(),
                    s.getFullName(),
                    s.getRole(),
                    s.getDepartment(),
                    s.getFacilityId()
            });
        }

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}
