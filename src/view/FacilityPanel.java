package view;

import model.Facility;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class FacilityPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    // facility_id,facility_name,facility_type,address,postcode,phone_number,email,opening_hours,manager_name,capacity,specialities_offered
    private final JTextField facilityIdField = new JTextField(8);
    private final JTextField nameField = new JTextField(20);
    private final JTextField typeField = new JTextField(12);
    private final JTextField addressField = new JTextField(22);
    private final JTextField postcodeField = new JTextField(8);
    private final JTextField phoneField = new JTextField(12);
    private final JTextField emailField = new JTextField(20);
    private final JTextField openingHoursField = new JTextField(18);
    private final JTextField managerField = new JTextField(14);
    private final JTextField capacityField = new JTextField(6);
    private final JTextField specialitiesField = new JTextField(22);

    public FacilityPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout());

        // --- Top form (like prescriptions) ---
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        facilityIdField.setEditable(false);

        top.add(new JLabel("Facility ID:"));
        top.add(facilityIdField);

        top.add(new JLabel("Name:"));
        top.add(nameField);

        top.add(new JLabel("Type:"));
        top.add(typeField);

        top.add(new JLabel("Address:"));
        top.add(addressField);

        top.add(new JLabel("Postcode:"));
        top.add(postcodeField);

        top.add(new JLabel("Phone:"));
        top.add(phoneField);

        top.add(new JLabel("Email:"));
        top.add(emailField);

        top.add(new JLabel("Opening Hours:"));
        top.add(openingHoursField);

        top.add(new JLabel("Manager:"));
        top.add(managerField);

        top.add(new JLabel("Capacity:"));
        top.add(capacityField);

        top.add(new JLabel("Specialities:"));
        top.add(specialitiesField);

        JButton addBtn = new JButton("Add Facility");
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
                "Facility ID", "Name", "Type", "Address", "Postcode",
                "Phone", "Email", "Opening Hours", "Manager", "Capacity", "Specialities"
        };

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        configureTable(table, new int[]{
                90, 260, 120, 280, 90,
                140, 240, 220, 160, 90, 260
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshTable();

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
        if (nameField.getText().trim().isEmpty() || typeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Fill Name and Type at minimum.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Keep it simple: auto ID so the ID field stays read-only
        String newId = "F" + String.format("%03d", store.getFacilities().size() + 1);

        Facility facility = new Facility(
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

        store.addFacility(facility);
        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Facility added.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
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
