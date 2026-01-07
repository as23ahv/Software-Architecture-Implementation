package view;

import model.Staff;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StaffPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField staffIdField = new JTextField(8);
    private final JTextField firstNameField = new JTextField(10);
    private final JTextField lastNameField = new JTextField(10);
    private final JTextField roleField = new JTextField(12);
    private final JTextField departmentField = new JTextField(12);
    private final JTextField facilityIdField = new JTextField(8);
    private final JTextField phoneField = new JTextField(12);
    private final JTextField emailField = new JTextField(16);
    private final JTextField employmentStatusField = new JTextField(10);
    private final JTextField startDateField = new JTextField(10);
    private final JTextField lineManagerField = new JTextField(12);
    private final JTextField accessLevelField = new JTextField(10);

    public StaffPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout(8, 8));

        // ================= FORM (TOP) =================
        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        staffIdField.setEditable(false);

        // Row 0
        gbc.gridy = 0;
        gbc.gridx = 0; top.add(new JLabel("Staff ID:"), gbc);
        gbc.gridx = 1; top.add(staffIdField, gbc);

        gbc.gridx = 2; top.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 3; top.add(firstNameField, gbc);

        gbc.gridx = 4; top.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 5; top.add(lastNameField, gbc);

        gbc.gridx = 6; top.add(new JLabel("Role:"), gbc);
        gbc.gridx = 7; top.add(roleField, gbc);

        // Row 1
        gbc.gridy = 1;
        gbc.gridx = 0; top.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1; top.add(departmentField, gbc);

        gbc.gridx = 2; top.add(new JLabel("Facility ID:"), gbc);
        gbc.gridx = 3; top.add(facilityIdField, gbc);

        gbc.gridx = 4; top.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 5; top.add(phoneField, gbc);

        gbc.gridx = 6; top.add(new JLabel("Email:"), gbc);
        gbc.gridx = 7; top.add(emailField, gbc);

        // Row 2
        gbc.gridy = 2;
        gbc.gridx = 0; top.add(new JLabel("Employment Status:"), gbc);
        gbc.gridx = 1; top.add(employmentStatusField, gbc);

        gbc.gridx = 2; top.add(new JLabel("Start Date:"), gbc);
        gbc.gridx = 3; top.add(startDateField, gbc);

        gbc.gridx = 4; top.add(new JLabel("Line Manager:"), gbc);
        gbc.gridx = 5; top.add(lineManagerField, gbc);

        gbc.gridx = 6; top.add(new JLabel("Access Level:"), gbc);
        gbc.gridx = 7; top.add(accessLevelField, gbc);

        // Buttons Row
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add Staff");
        JButton loadBtn = new JButton("Load Selected");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        btnRow.add(addBtn);
        btnRow.add(loadBtn);
        btnRow.add(updateBtn);
        btnRow.add(deleteBtn);

        JPanel north = new JPanel(new BorderLayout());
        north.add(top, BorderLayout.CENTER);
        north.add(btnRow, BorderLayout.SOUTH);

        add(north, BorderLayout.NORTH);

        // ================= TABLE (CENTER) =================
        String[] cols = {
                "Staff ID", "First Name", "Last Name", "Role", "Department", "Facility ID",
                "Phone", "Email", "Employment Status", "Start Date", "Line Manager", "Access Level"
        };

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        table.setRowHeight(24);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] widths = {80, 110, 110, 160, 140, 90, 130, 220, 140, 110, 140, 110};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);

        refreshTable();

        // Events
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
                    "Please fill First Name, Last Name and Role.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = "ST" + String.format("%03d", store.getStaff().size() + 1);

        Staff s = new Staff(
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

        store.addStaff(s);
        refreshTable();
        clearForm();
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
}
