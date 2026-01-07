package view;

import model.Staff;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class StaffPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    // staff_id,first_name,last_name,role,department,facility_id,phone_number,email,employment_status,start_date,line_manager,access_level
    private final JTextField staffIdField = new JTextField(8);
    private final JTextField firstNameField = new JTextField(10);
    private final JTextField lastNameField = new JTextField(10);
    private final JTextField roleField = new JTextField(14);
    private final JTextField departmentField = new JTextField(14);
    private final JTextField facilityIdField = new JTextField(8);
    private final JTextField phoneField = new JTextField(12);
    private final JTextField emailField = new JTextField(16);
    private final JTextField employmentStatusField = new JTextField(10);
    private final JTextField startDateField = new JTextField(10);
    private final JTextField lineManagerField = new JTextField(14);
    private final JTextField accessLevelField = new JTextField(10);

    public StaffPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        // --- Top form (like prescriptions) ---
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        staffIdField.setEditable(false);

        top.add(new JLabel("Staff ID:"));
        top.add(staffIdField);

        top.add(new JLabel("First Name:"));
        top.add(firstNameField);

        top.add(new JLabel("Last Name:"));
        top.add(lastNameField);

        top.add(new JLabel("Role:"));
        top.add(roleField);

        top.add(new JLabel("Department:"));
        top.add(departmentField);

        top.add(new JLabel("Facility ID:"));
        top.add(facilityIdField);

        top.add(new JLabel("Phone:"));
        top.add(phoneField);

        top.add(new JLabel("Email:"));
        top.add(emailField);

        top.add(new JLabel("Employment Status:"));
        top.add(employmentStatusField);

        top.add(new JLabel("Start Date:"));
        top.add(startDateField);

        top.add(new JLabel("Line Manager:"));
        top.add(lineManagerField);

        top.add(new JLabel("Access Level:"));
        top.add(accessLevelField);

        JButton addBtn = new JButton("Add Staff");
        JButton loadBtn = new JButton("Load Selected");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        top.add(addBtn);
        top.add(loadBtn);
        top.add(updateBtn);
        top.add(deleteBtn);

        add(top, BorderLayout.NORTH);

        // --- Table ---
        String[] cols = {
                "Staff ID", "First Name", "Last Name", "Role", "Department", "Facility ID",
                "Phone", "Email", "Employment Status", "Start Date", "Line Manager", "Access Level"
        };
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        // Match the “spaced out” look like prescriptions
        configureTable(table, new int[]{
                90, 110, 110, 180, 150, 90,
                120, 220, 140, 110, 180, 110
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();

        // actions
        addBtn.addActionListener(e -> addStaff());
        loadBtn.addActionListener(e -> loadSelected());
        updateBtn.addActionListener(e -> updateSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
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
    }

    private void addStaff() {
        if (firstNameField.getText().trim().isEmpty()
                || lastNameField.getText().trim().isEmpty()
                || roleField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Fill First Name, Last Name and Role at minimum.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = "ST" + String.format("%03d", store.getStaff().size() + 1);

        Staff staff = new Staff(
                newId,
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                roleField.getText().trim(),
                departmentField.getText().trim(),
                facilityIdField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                employmentStatusField.getText().trim(),
                startDateField.getText().trim(),
                lineManagerField.getText().trim(),
                accessLevelField.getText().trim()
        );

        store.addStaff(staff);
        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Staff added.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();
        Staff s = store.getStaff().get(id);
        if (s == null) return;

        staffIdField.setText(s.getStaffId());
        firstNameField.setText(s.getFirstName());
        lastNameField.setText(s.getLastName());
        roleField.setText(s.getRole());
        departmentField.setText(s.getDepartment());
        facilityIdField.setText(s.getFacilityId());
        phoneField.setText(s.getPhoneNumber());
        emailField.setText(s.getEmail());
        employmentStatusField.setText(s.getEmploymentStatus());
        startDateField.setText(s.getStartDate());
        lineManagerField.setText(s.getLineManager());
        accessLevelField.setText(s.getAccessLevel());
    }

    private void updateSelected() {
        String id = staffIdField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Click 'Load Selected' first, then update.",
                    "No Staff Loaded",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Staff updated = new Staff(
                id,
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                roleField.getText().trim(),
                departmentField.getText().trim(),
                facilityIdField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                employmentStatusField.getText().trim(),
                startDateField.getText().trim(),
                lineManagerField.getText().trim(),
                accessLevelField.getText().trim()
        );

        store.getStaff().put(id, updated);
        refreshTable();
        clearForm();
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete staff " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        store.getStaff().remove(id);
        refreshTable();
        clearForm();
    }

    private void clearForm() {
        staffIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        roleField.setText("");
        departmentField.setText("");
        facilityIdField.setText("");
        phoneField.setText("");
        emailField.setText("");
        employmentStatusField.setText("");
        startDateField.setText("");
        lineManagerField.setText("");
        accessLevelField.setText("");
    }

    private void configureTable(JTable t, int[] widths) {
        t.setRowHeight(22);
        t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        t.setIntercellSpacing(new Dimension(8, 2));

        for (int i = 0; i < widths.length && i < t.getColumnCount(); i++) {
            TableColumn col = t.getColumnModel().getColumn(i);
            col.setPreferredWidth(widths[i]);
        }
    }
}
