package view;

import model.Facility;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FacilityPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField facilityIdField = new JTextField(8);
    private final JTextField nameField = new JTextField(16);
    private final JTextField typeField = new JTextField(10);
    private final JTextField addressField = new JTextField(18);
    private final JTextField postcodeField = new JTextField(8);
    private final JTextField phoneField = new JTextField(12);
    private final JTextField emailField = new JTextField(18);
    private final JTextField openingHoursField = new JTextField(14);
    private final JTextField managerField = new JTextField(14);
    private final JTextField capacityField = new JTextField(8);
    private final JTextField specialitiesField = new JTextField(18);

    public FacilityPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout(8, 8));

        // ================= FORM (TOP) =================
        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        facilityIdField.setEditable(false);

        // Row 0
        gbc.gridy = 0;
        gbc.gridx = 0; top.add(new JLabel("Facility ID:"), gbc);
        gbc.gridx = 1; top.add(facilityIdField, gbc);

        gbc.gridx = 2; top.add(new JLabel("Name:"), gbc);
        gbc.gridx = 3; top.add(nameField, gbc);

        gbc.gridx = 4; top.add(new JLabel("Type:"), gbc);
        gbc.gridx = 5; top.add(typeField, gbc);

        // Row 1
        gbc.gridy = 1;
        gbc.gridx = 0; top.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; top.add(addressField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 3; top.add(new JLabel("Postcode:"), gbc);
        gbc.gridx = 4; top.add(postcodeField, gbc);

        gbc.gridx = 5; top.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 6; top.add(phoneField, gbc);

        // Row 2
        gbc.gridy = 2;
        gbc.gridx = 0; top.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; top.add(emailField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 3; top.add(new JLabel("Opening Hours:"), gbc);
        gbc.gridx = 4; top.add(openingHoursField, gbc);

        gbc.gridx = 5; top.add(new JLabel("Manager:"), gbc);
        gbc.gridx = 6; top.add(managerField, gbc);

        // Row 3
        gbc.gridy = 3;
        gbc.gridx = 0; top.add(new JLabel("Capacity:"), gbc);
        gbc.gridx = 1; top.add(capacityField, gbc);

        gbc.gridx = 2; top.add(new JLabel("Specialities:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 4; top.add(specialitiesField, gbc);
        gbc.gridwidth = 1;

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add Facility");
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
                "Facility ID", "Name", "Type", "Address", "Postcode",
                "Phone", "Email", "Opening Hours", "Manager", "Capacity", "Specialities"
        };

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        table.setRowHeight(24);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] widths = {90, 220, 100, 240, 90, 130, 220, 140, 140, 90, 220};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);

        refreshTable();

        // Events
        addBtn.addActionListener(e -> addFacility());
        loadBtn.addActionListener(e -> loadSelected());
        updateBtn.addActionListener(e -> updateSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Facility f : store.getFacilities().values()) {
            tableModel.addRow(new Object[]{
                    f.getFacilityId(),
                    f.getFacilityName(),
                    f.getFacilityType(),
                    f.getAddress(),
                    f.getPostcode(),
                    f.getPhoneNumber(),
                    f.getEmail(),
                    f.getOpeningHours(),
                    f.getManagerName(),
                    f.getCapacity(),
                    f.getSpecialitiesOffered()
            });
        }
    }

    private void addFacility() {
        if (nameField.getText().trim().isEmpty()
                || typeField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill Facility Name and Facility Type.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // auto ID: if type is GP Surgery -> S### else Hospital -> H###
        String prefix = typeField.getText().trim().toLowerCase().contains("gp") ? "S" : "H";
        String newId = prefix + String.format("%03d", store.getFacilities().size() + 1);

        Facility f = new Facility(
                newId,
                nameField.getText().trim(),
                typeField.getText().trim(),
                addressField.getText().trim(),
                postcodeField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                openingHoursField.getText().trim(),
                managerField.getText().trim(),
                capacityField.getText().trim(),
                specialitiesField.getText().trim()
        );

        store.addFacility(f);
        refreshTable();
        clearForm();
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();
        Facility f = store.getFacilities().get(id);
        if (f == null) return;

        facilityIdField.setText(f.getFacilityId());
        nameField.setText(f.getFacilityName());
        typeField.setText(f.getFacilityType());
        addressField.setText(f.getAddress());
        postcodeField.setText(f.getPostcode());
        phoneField.setText(f.getPhoneNumber());
        emailField.setText(f.getEmail());
        openingHoursField.setText(f.getOpeningHours());
        managerField.setText(f.getManagerName());
        capacityField.setText(f.getCapacity());
        specialitiesField.setText(f.getSpecialitiesOffered());
    }

    private void updateSelected() {
        String id = facilityIdField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Click 'Load Selected' first, then update.",
                    "No Facility Loaded",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Facility updated = new Facility(
                id,
                nameField.getText().trim(),
                typeField.getText().trim(),
                addressField.getText().trim(),
                postcodeField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                openingHoursField.getText().trim(),
                managerField.getText().trim(),
                capacityField.getText().trim(),
                specialitiesField.getText().trim()
        );

        store.getFacilities().put(id, updated);
        refreshTable();
        clearForm();
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete facility " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        store.getFacilities().remove(id);
        refreshTable();
        clearForm();
    }

    private void clearForm() {
        facilityIdField.setText("");
        nameField.setText("");
        typeField.setText("");
        addressField.setText("");
        postcodeField.setText("");
        phoneField.setText("");
        emailField.setText("");
        openingHoursField.setText("");
        managerField.setText("");
        capacityField.setText("");
        specialitiesField.setText("");
    }
}
