package view;

import model.Prescription;
import model.store.DataStore;
import persistence.PrescriptionWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;

public class PrescriptionPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;

    private final JTextField patientIdField = new JTextField(10);
    private final JTextField clinicianIdField = new JTextField(10);
    private final JTextField appointmentIdField = new JTextField(10);
    private final JTextField medicationField = new JTextField(12);
    private final JTextField dosageField = new JTextField(10);
    private final JTextField frequencyField = new JTextField(10);
    private final JTextField durationField = new JTextField(6);
    private final JTextField quantityField = new JTextField(6);
    private final JTextField pharmacyField = new JTextField(12);

    public PrescriptionPanel(DataStore store) {
        this.store = store;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ---- Form panel ----
        JPanel form = new JPanel();

        form.add(new JLabel("Patient ID:"));
        form.add(patientIdField);

        form.add(new JLabel("Clinician ID:"));
        form.add(clinicianIdField);

        form.add(new JLabel("Appointment ID:"));
        form.add(appointmentIdField);

        form.add(new JLabel("Medication:"));
        form.add(medicationField);

        form.add(new JLabel("Dosage:"));
        form.add(dosageField);

        form.add(new JLabel("Frequency:"));
        form.add(frequencyField);

        form.add(new JLabel("Duration(days):"));
        form.add(durationField);

        form.add(new JLabel("Quantity:"));
        form.add(quantityField);

        form.add(new JLabel("Pharmacy:"));
        form.add(pharmacyField);

        JButton addBtn = new JButton("Add Prescription");
        form.add(addBtn);

        add(form);

        // ---- Table ----
        String[] columns = {"Prescription ID", "Patient ID", "Clinician ID", "Medication", "Dosage", "Status"};
        tableModel = new DefaultTableModel(columns, 0);

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table));

        // Load existing data into table
        refreshTable();

        // Button action
        addBtn.addActionListener(e -> addPrescription());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        for (Prescription p : store.getPrescriptions().values()) {
            tableModel.addRow(new Object[]{
                    p.getPrescriptionId(),
                    p.getPatientId(),
                    p.getClinicianId(),
                    p.getMedicationName(),
                    p.getDosage(),
                    p.getStatus()
            });
        }
    }

    private void addPrescription() {
        String patientId = patientIdField.getText().trim();
        String clinicianId = clinicianIdField.getText().trim();
        String appointmentId = appointmentIdField.getText().trim();
        String medication = medicationField.getText().trim();
        String dosage = dosageField.getText().trim();
        String frequency = frequencyField.getText().trim();
        String durationDays = durationField.getText().trim();
        String quantity = quantityField.getText().trim();
        String pharmacy = pharmacyField.getText().trim();

        if (patientId.isEmpty() || clinicianId.isEmpty() || medication.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill at least Patient ID, Clinician ID and Medication.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create a simple new ID
        String newId = "RX" + (store.getPrescriptions().size() + 1);

        String today = LocalDate.now().toString();

        Prescription newPrescription = new Prescription(
                newId,                 // prescriptionId
                patientId,             // patientId
                clinicianId,           // clinicianId
                appointmentId,         // appointmentId
                today,                 // prescriptionDate
                medication,            // medicationName
                dosage,                // dosage
                frequency,             // frequency
                durationDays,          // durationDays
                quantity,              // quantity
                "",                    // instructions (blank for now)
                pharmacy,              // pharmacyName
                "ISSUED",              // status
                today,                 // issueDate
                ""                     // collectionDate
        );

        // Add to memory store
        store.addPrescription(newPrescription);

        // Append to CSV file
        PrescriptionWriter.appendPrescription("data/prescriptions.csv", newPrescription);

        // Update table
        refreshTable();

        // Clear input fields
        patientIdField.setText("");
        clinicianIdField.setText("");
        appointmentIdField.setText("");
        medicationField.setText("");
        dosageField.setText("");
        frequencyField.setText("");
        durationField.setText("");
        quantityField.setText("");
        pharmacyField.setText("");

        JOptionPane.showMessageDialog(this,
                "Prescription added and saved to prescriptions.csv",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
