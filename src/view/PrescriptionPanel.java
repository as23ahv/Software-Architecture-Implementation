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

    private final JTextField rxIdField = new JTextField(8);
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

        form.add(new JLabel("Prescription ID:"));
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

        form.add(new JLabel("Duration (days):"));
        form.add(durationField);

        form.add(new JLabel("Quantity:"));
        form.add(quantityField);

        form.add(new JLabel("Instructions:"));
        form.add(instructionsField);

        form.add(new JLabel("Pharmacy:"));
        form.add(pharmacyField);

        form.add(new JLabel("Status:"));
        form.add(statusField);

        // ✅ RENAMED LABEL
        form.add(new JLabel("Prescription Date:"));
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
        if (patientIdField.getText().trim().isEmpty()
                || clinicianIdField.getText().trim().isEmpty()
                || medicationField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill Patient ID, Clinician ID and Medication.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = "RX" + String.format("%03d", store.getPrescriptions().size() + 1);
        String today = LocalDate.now().toString();

        Prescription p = new Prescription(
                newId,
                patientIdField.getText().trim(),
                clinicianIdField.getText().trim(),
                appointmentIdField.getText().trim(),
                prescriptionDateField.getText().trim().isEmpty() ? today : prescriptionDateField.getText().trim(),
                medicationField.getText().trim(),
                dosageField.getText().trim(),
                frequencyField.getText().trim(),
                durationField.getText().trim(),
                quantityField.getText().trim(),
                instructionsField.getText().trim(),
                pharmacyField.getText().trim(),
                statusField.getText().trim().isEmpty() ? "Issued" : statusField.getText().trim(),
                issueDateField.getText().trim().isEmpty() ? today : issueDateField.getText().trim(),
                collectionDateField.getText().trim()
        );

        store.addPrescription(p);
        PrescriptionWriter.appendPrescription("data/prescriptions.csv", p);

        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Prescription added successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();
        Prescription p = store.getPrescriptions().get(id);

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
        if (rxIdField.getText().trim().isEmpty()) return;

        Prescription updated = new Prescription(
                rxIdField.getText().trim(),
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

        store.getPrescriptions().put(updated.getPrescriptionId(), updated);
        refreshTable();
        clearForm();
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();
        store.getPrescriptions().remove(id);
        refreshTable();
        clearForm();
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
