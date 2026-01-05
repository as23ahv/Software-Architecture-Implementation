package view;

import model.Clinician;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClinicianPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField clinicianIdField = new JTextField(8);
    private final JTextField firstNameField = new JTextField(10);
    private final JTextField lastNameField = new JTextField(10);
    private final JTextField titleField = new JTextField(8);
    private final JTextField specialityField = new JTextField(12);
    private final JTextField gmcNumberField = new JTextField(10);
    private final JTextField phoneField = new JTextField(12);
    private final JTextField emailField = new JTextField(16);
    private final JTextField workplaceIdField = new JTextField(10);
    private final JTextField workplaceTypeField = new JTextField(10);
    private final JTextField employmentStatusField = new JTextField(10);
    private final JTextField startDateField = new JTextField(10);

    public ClinicianPanel(DataStore store) {
        this.store = store;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel form = new JPanel();

        clinicianIdField.setEditable(false);

        form.add(new JLabel("Clinician ID:"));
        form.add(clinicianIdField);

        form.add(new JLabel("First Name:"));
        form.add(firstNameField);

        form.add(new JLabel("Last Name:"));
        form.add(lastNameField);

        form.add(new JLabel("Title:"));
        form.add(titleField);

        form.add(new JLabel("Speciality:"));
        form.add(specialityField);

        form.add(new JLabel("GMC No:"));
        form.add(gmcNumberField);

        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

        form.add(new JLabel("Workplace ID:"));
        form.add(workplaceIdField);

        form.add(new JLabel("Workplace Type:"));
        form.add(workplaceTypeField);

        form.add(new JLabel("Employment Status:"));
        form.add(employmentStatusField);

        form.add(new JLabel("Start Date:"));
        form.add(startDateField);

        JButton addBtn = new JButton("Add Clinician");
        JButton loadBtn = new JButton("Load Selected");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        form.add(addBtn);
        form.add(loadBtn);
        form.add(updateBtn);
        form.add(deleteBtn);

        add(form);

        String[] cols = {
                "Clinician ID", "First Name", "Last Name", "Title", "Speciality",
                "GMC Number", "Phone", "Email",
                "Workplace ID", "Workplace Type", "Employment Status", "Start Date"
        };

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();

        addBtn.addActionListener(e -> addClinician());
        loadBtn.addActionListener(e -> loadSelected());
        updateBtn.addActionListener(e -> updateSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Clinician c : store.getClinicians().values()) {
            tableModel.addRow(new Object[]{
                    c.getClinicianId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getTitle(),
                    c.getSpeciality(),
                    c.getGmcNumber(),
                    c.getPhoneNumber(),
                    c.getEmail(),
                    c.getWorkplaceId(),
                    c.getWorkplaceType(),
                    c.getEmploymentStatus(),
                    c.getStartDate()
            });
        }
    }

    private void addClinician() {
        if (firstNameField.getText().trim().isEmpty()
                || lastNameField.getText().trim().isEmpty()
                || specialityField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill First Name, Last Name and Speciality.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = "C" + String.format("%03d", store.getClinicians().size() + 1);

        Clinician clinician = new Clinician(
                newId,
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                titleField.getText().trim(),
                specialityField.getText().trim(),
                gmcNumberField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                workplaceIdField.getText().trim(),
                workplaceTypeField.getText().trim(),
                employmentStatusField.getText().trim(),
                startDateField.getText().trim()
        );

        store.addClinician(clinician);

        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Clinician added.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();
        Clinician c = store.getClinicians().get(id);
        if (c == null) return;

        clinicianIdField.setText(c.getClinicianId());
        firstNameField.setText(c.getFirstName());
        lastNameField.setText(c.getLastName());
        titleField.setText(c.getTitle());
        specialityField.setText(c.getSpeciality());
        gmcNumberField.setText(c.getGmcNumber());
        phoneField.setText(c.getPhoneNumber());
        emailField.setText(c.getEmail());
        workplaceIdField.setText(c.getWorkplaceId());
        workplaceTypeField.setText(c.getWorkplaceType());
        employmentStatusField.setText(c.getEmploymentStatus());
        startDateField.setText(c.getStartDate());
    }

    private void updateSelected() {
        String id = clinicianIdField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Click 'Load Selected' first, then update.",
                    "No Clinician Loaded",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Clinician updated = new Clinician(
                id,
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                titleField.getText().trim(),
                specialityField.getText().trim(),
                gmcNumberField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                workplaceIdField.getText().trim(),
                workplaceTypeField.getText().trim(),
                employmentStatusField.getText().trim(),
                startDateField.getText().trim()
        );

        store.getClinicians().put(id, updated);
        refreshTable();
        clearForm();
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete clinician " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        store.removeClinician(id);
        refreshTable();
        clearForm();
    }

    private void clearForm() {
        clinicianIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        titleField.setText("");
        specialityField.setText("");
        gmcNumberField.setText("");
        phoneField.setText("");
        emailField.setText("");
        workplaceIdField.setText("");
        workplaceTypeField.setText("");
        employmentStatusField.setText("");
        startDateField.setText("");
    }
}
