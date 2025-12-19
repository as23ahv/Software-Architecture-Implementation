package view;

import model.Patient;
import model.store.DataStore;
import persistence.PatientWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;

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
        form.add(addBtn);

        add(form);

        // ---- Table ----
        String[] cols = {"Patient ID", "First Name", "Last Name", "NHS Number"};
        tableModel = new DefaultTableModel(cols, 0);

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();

        addBtn.addActionListener(e -> addPatient());
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

        // Create a simple new ID: P011, P012, ...
        String newId = "P" + String.format("%03d", store.getPatients().size() + 1);

        Patient newPatient = new Patient(
                newId,
                firstName,
                lastName,
                nhs,
                dob,
                phone,
                email,
                gp
        );

        // Add to in-memory store
        store.addPatient(newPatient);

        // Append to CSV
        PatientWriter.appendPatient("data/patients.csv", newPatient);

        // Refresh table
        refreshTable();

        // Clear fields
        firstNameField.setText("");
        lastNameField.setText("");
        nhsField.setText("");
        dobField.setText("");
        phoneField.setText("");
        emailField.setText("");
        gpField.setText("");

        JOptionPane.showMessageDialog(this,
                "Patient added and saved to patients.csv",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
