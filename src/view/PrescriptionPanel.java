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
    private final JTable table;

    private final JTextField rxIdField = new JTextField(8); // read-only when loaded
    private final JTextField patientIdField = new JTextField(10);
    private final JTextField clinicianIdField = new JTextField(10);
    private final JTextField appointmentIdField = new JTextField(10);
    private final JTextField medicationField = new JTextField(12);
    private final JTextField dosageField = new JTextField(10);
    private final JTextField frequencyField = new JTextField(10);
    private final JTextField durationField = new JTextField(6);
    private final JTextField quantityField = new JTextField(6);

    private final JTextField instructionsField = new JTextField(14);
    private final JTextField pharmacyField = new JTextField(12);
    private final JTextField statusField = new JTextField(10);

    private final JTextField prescriptionDateField = new JTextField(10);
    private final JTextField issueDateField = new JTextField(10);
    private final JTextField collectionDateField = new JTextField(10);

    public PrescriptionPanel(DataStore store) {
        this.store = store;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel form = new JPanel();

        rxIdField.setEditable(false);
        form.add(new JLabel("Rx ID:"));
        form.add(rxIdField);

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

        form.add(new JLabel("Instructions:"));
        form.add(instructionsField);

        form.add(new JLabel("Pharmacy:"));
        form.add(pharmacyField);

        form.add(new JLabel("Status:"));
        form.add(statusField);

        form.add(new JLabel("Rx Date:"));
        form.add(prescriptionDateField);

        form.add(new JLabel("Issue Date:"));
        form.add(issueDateField);

        form.add(new JLabel("Collection Date:"));
        form.add(collectionDateField);

        JButton addBtn = new JButton("Add");
        JButton loadBtn = new JButton("Load Selected");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        form.add(addBtn);
        form.add(loadBtn);
        form.add(updateBtn);
        form.add(deleteBtn);

        add(form);

        String[] columns = {
                "Prescription ID", "Patient ID", "Clinician ID", "Appointment ID",
                "Prescription Date", "Medication", "Dosage", "Frequency",
                "Duration(days)", "Quantity", "Instructions", "Pharmacy",
                "Status", "Issue Date", "Collection Date"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();

        addBtn.addActionListener(e -> addPrescription());
        loadBtn.addActionListener(e -> loadSelected());
        updateBtn.addActionListener(e -> updateSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Prescription p : store.getPrescriptions().values()) {
            tableModel.addRow(new Object[]{
                    p.getPrescriptionId(),
                    p.getPatientId(),
                    p.getClinicianId(),
                    p.getAppointmentId(),
                    p.getPrescriptionDate(),
                    p.getMedicationName(),
                    p.getDosage(),
                    p.getFrequency(),
                    p.getDurationDays(),
                    p.getQuantity(),
                    p.getInstructions(),
                    p.getPharmacyName(),
                    p.getStatus(),
                    p.getIssueDate(),
                    p.getCollectionDate()
            });
        }
    }

    private void addPrescription() {
        String patientId = patientIdField.getText().trim();
        String clinicianId = clinicianIdField.getText().trim();
        String medication = medicationField.getText().trim();

        if (patientId.isEmpty() || clinicianId.isEmpty() || medication.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Fill at least Patient ID, Clinician ID and Medication.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = "RX" + String.format("%03d", store.getPrescriptions().size() + 1);

        String today = LocalDate.now().toString();
        String rxDate = prescriptionDateField.getText().trim().isEmpty() ? today : prescriptionDateField.getText().trim();
        String issueDate = issueDateField.getText().trim().isEmpty() ? today : issueDateField.getText().trim();

        Prescription newRx = new Prescription(
                newId,
                patientId,
                clinicianId,
                appointmentIdField.getText().trim(),
                rxDate,
                medication,
                dosageField.getText().trim(),
                frequencyField.getText().trim(),
                durationField.getText().trim(),
                quantityField.getText().trim(),
                instructionsField.getText().trim(),
                pharmacyField.getText().trim(),
                statusField.getText().trim().isEmpty() ? "Issued" : statusField.getText().trim(),
                issueDate,
                collectionDateField.getText().trim()
        );

        store.addPrescription(newRx);
        PrescriptionWriter.appendPrescription("data/prescriptions.csv", newRx);

        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Prescription added and saved to prescriptions.csv",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select a prescription row first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String rxId = tableModel.getValueAt(row, 0).toString();
        Prescription p = store.getPrescriptions().get(rxId);
        if (p == null) return;

        rxIdField.setText(p.getPrescriptionId());
        patientIdField.setText(p.getPatientId());
        clinicianIdField.setText(p.getClinicianId());
        appointmentIdField.setText(p.getAppointmentId());
        prescriptionDateField.setText(p.getPrescriptionDate());
        medicationField.setText(p.getMedicationName());
        dosageField.setText(p.getDosage());
        frequencyField.setText(p.getFrequency());
        durationField.setText(p.getDurationDays());
        quantityField.setText(p.getQuantity());
        instructionsField.setText(p.getInstructions());
        pharmacyField.setText(p.getPharmacyName());
        statusField.setText(p.getStatus());
        issueDateField.setText(p.getIssueDate());
        collectionDateField.setText(p.getCollectionDate());
    }

    private void updateSelected() {
        String rxId = rxIdField.getText().trim();
        if (rxId.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Click 'Load Selected' first, then update.",
                    "No Prescription Loaded",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Prescription updated = new Prescription(
                rxId,
                patientIdField.getText().trim(),
                clinicianIdField.getText().trim(),
                appointmentIdField.getText().trim(),
                prescriptionDateField.getText().trim(),
                medicationField.getText().trim(),
                dosageField.getText().trim(),
                frequencyField.getText().trim(),
                durationField.getText().trim(),
                quantityField.getText().trim(),
                instructionsField.getText().trim(),
                pharmacyField.getText().trim(),
                statusField.getText().trim(),
                issueDateField.getText().trim(),
                collectionDateField.getText().trim()
        );

        store.getPrescriptions().put(rxId, updated);
        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Prescription updated in the app (CSV not rewritten).",
                "Updated",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Select a prescription row first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String rxId = tableModel.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete prescription " + rxId + " from the app?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        store.getPrescriptions().remove(rxId);
        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Prescription deleted from the app (CSV not changed).",
                "Deleted",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        rxIdField.setText("");
        patientIdField.setText("");
        clinicianIdField.setText("");
        appointmentIdField.setText("");
        medicationField.setText("");
        dosageField.setText("");
        frequencyField.setText("");
        durationField.setText("");
        quantityField.setText("");
        instructionsField.setText("");
        pharmacyField.setText("");
        statusField.setText("");
        prescriptionDateField.setText("");
        issueDateField.setText("");
        collectionDateField.setText("");
    }
}
