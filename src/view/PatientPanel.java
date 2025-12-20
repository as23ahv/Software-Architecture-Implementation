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

    private final JTextField patientIdField = new JTextField(8); // used for edit
    private final JTextField firstNameField = new JTextField(10);
    private final JTextField lastNameField = new JTextField(10);
    private final JTextField nhsField = new JTextField(12);
    private final JTextField dobField = new JTextField(10);
    private final JTextField phoneField = new JTextField(10);
    private final JTextField emailField = new JTextField(14);
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

        form.add(new JLabel("NHS No:"));
        form.add(nhsField);

        form.add(new JLabel("DOB (yyyy-mm-dd):"));
        form.add(dobField);

        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

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

        // ---- Table ----
        String[] cols = {"Patient ID", "First Name", "Last Name", "NHS Number"};
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
                    p.getNhsNumber()
            });
        }
    }

    private void addPatient() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String nhs = nhsField.getText().trim();
        String dob = dobField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String gp = gpField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || nhs.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill at least First Name, Last Name and NHS Number.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = "P" + String.format("%03d", store.getPatients().size() + 1);

        Patient newPatient = new Patient(
                newId, firstName, lastName, nhs, dob, phone, email, gp
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
        nhsField.setText(p.getNhsNumber());
        dobField.setText(p.getDateOfBirth());
        phoneField.setText(p.getPhoneNumber());
        emailField.setText(p.getEmail());
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
                nhsField.getText().trim(),
                dobField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
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
        nhsField.setText("");
        dobField.setText("");
        phoneField.setText("");
        emailField.setText("");
        gpField.setText("");
    }
}
