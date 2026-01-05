package view;

import model.Patient;
import model.store.DataStore;
import persistence.PatientWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    // Form fields (match CSV schema)
    private final JTextField patientIdField = new JTextField(6); // read-only for edit
    private final JTextField firstNameField = new JTextField(10);
    private final JTextField lastNameField = new JTextField(10);
    private final JTextField dobField = new JTextField(10);
    private final JTextField nhsField = new JTextField(12);
    private final JTextField genderField = new JTextField(6);
    private final JTextField phoneField = new JTextField(12);
    private final JTextField emailField = new JTextField(16);
    private final JTextField addressField = new JTextField(16);
    private final JTextField postcodeField = new JTextField(8);
    private final JTextField emergencyNameField = new JTextField(12);
    private final JTextField emergencyPhoneField = new JTextField(12);
    private final JTextField registrationDateField = new JTextField(10);
    private final JTextField gpField = new JTextField(8);

    public PatientPanel(DataStore store) {
        this.store = store;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ---- Form panel ----
        JPanel form = new JPanel();

        patientIdField.setEditable(false);
        form.add(new JLabel("Patient ID:"));
        form.add(patientIdField);

        form.add(new JLabel("First Name:"));
        form.add(firstNameField);

        form.add(new JLabel("Last Name:"));
        form.add(lastNameField);

        form.add(new JLabel("DOB:"));
        form.add(dobField);

        form.add(new JLabel("NHS No:"));
        form.add(nhsField);

        form.add(new JLabel("Gender:"));
        form.add(genderField);

        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

        form.add(new JLabel("Address:"));
        form.add(addressField);

        form.add(new JLabel("Postcode:"));
        form.add(postcodeField);

        form.add(new JLabel("Emergency Name:"));
        form.add(emergencyNameField);

        form.add(new JLabel("Emergency Phone:"));
        form.add(emergencyPhoneField);

        form.add(new JLabel("Reg Date:"));
        form.add(registrationDateField);

        form.add(new JLabel("GP ID:"));
        form.add(gpField);

        JButton addBtn = new JButton("Add Patient");
        JButton loadBtn = new JButton("Load Selected");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        form.add(addBtn);
        form.add(loadBtn);
        form.add(updateBtn);
        form.add(deleteBtn);

        add(form);

        // ---- Table (all columns) ----
        String[] cols = {
                "Patient ID", "First Name", "Last Name", "DOB", "NHS No", "Gender",
                "Phone", "Email", "Address", "Postcode",
                "Emergency Name", "Emergency Phone", "Reg Date", "GP ID"
        };

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();

        addBtn.addActionListener(e -> addPatient());
        loadBtn.addActionListener(e -> loadSelectedPatient());
        updateBtn.addActionListener(e -> updateSelectedPatient());
        deleteBtn.addActionListener(e -> deleteSelectedPatient());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        for (Patient p : store.getPatients().values()) {
            tableModel.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getDateOfBirth(),
                    p.getNhsNumber(),
                    p.getGender(),
                    p.getPhoneNumber(),
                    p.getEmail(),
                    p.getAddress(),
                    p.getPostcode(),
                    p.getEmergencyContactName(),
                    p.getEmergencyContactPhone(),
                    p.getRegistrationDate(),
                    p.getGpSurgeryId()
            });
        }
    }

    private void addPatient() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dob = dobField.getText().trim();
        String nhs = nhsField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || nhs.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill at least First Name, Last Name, DOB and NHS No.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // auto ID
        String newId = "P" + String.format("%03d", store.getPatients().size() + 1);

        Patient newPatient = new Patient(
                newId,
                firstName,
                lastName,
                dob,
                nhs,
                genderField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                addressField.getText().trim(),
                postcodeField.getText().trim(),
                emergencyNameField.getText().trim(),
                emergencyPhoneField.getText().trim(),
                registrationDateField.getText().trim(),
                gpField.getText().trim()
        );

        store.addPatient(newPatient);

        PatientWriter.appendPatient("data/patients.csv", newPatient);

        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Patient added and saved to patients.csv",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadSelectedPatient() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select a patient row first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patientId = tableModel.getValueAt(row, 0).toString();
        Patient p = store.getPatients().get(patientId);
        if (p == null) return;

        patientIdField.setText(p.getPatientId());
        firstNameField.setText(p.getFirstName());
        lastNameField.setText(p.getLastName());
        dobField.setText(p.getDateOfBirth());
        nhsField.setText(p.getNhsNumber());
        genderField.setText(p.getGender());
        phoneField.setText(p.getPhoneNumber());
        emailField.setText(p.getEmail());
        addressField.setText(p.getAddress());
        postcodeField.setText(p.getPostcode());
        emergencyNameField.setText(p.getEmergencyContactName());
        emergencyPhoneField.setText(p.getEmergencyContactPhone());
        registrationDateField.setText(p.getRegistrationDate());
        gpField.setText(p.getGpSurgeryId());
    }

    private void updateSelectedPatient() {
        String patientId = patientIdField.getText().trim();
        if (patientId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Click 'Load Selected' first, then update.",
                    "No Patient Loaded",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Patient updated = new Patient(
                patientId,
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                dobField.getText().trim(),
                nhsField.getText().trim(),
                genderField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                addressField.getText().trim(),
                postcodeField.getText().trim(),
                emergencyNameField.getText().trim(),
                emergencyPhoneField.getText().trim(),
                registrationDateField.getText().trim(),
                gpField.getText().trim()
        );

        store.getPatients().put(patientId, updated);
        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Patient updated in the app (CSV not rewritten).",
                "Updated",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteSelectedPatient() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select a patient row first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String patientId = tableModel.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete patient " + patientId + " from the app?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        store.getPatients().remove(patientId);
        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Patient deleted from the app (CSV not changed).",
                "Deleted",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        patientIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("");
        nhsField.setText("");
        genderField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        postcodeField.setText("");
        emergencyNameField.setText("");
        emergencyPhoneField.setText("");
        registrationDateField.setText("");
        gpField.setText("");
    }
}
