package view;

import model.Referral;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReferralPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField referralIdField = new JTextField(8);
    private final JTextField patientIdField = new JTextField(8);
    private final JTextField refClinicianIdField = new JTextField(8);
    private final JTextField toClinicianIdField = new JTextField(8);
    private final JTextField refFacilityIdField = new JTextField(8);
    private final JTextField toFacilityIdField = new JTextField(8);
    private final JTextField referralDateField = new JTextField(10);
    private final JTextField urgencyField = new JTextField(10);
    private final JTextField reasonField = new JTextField(14);
    private final JTextField appointmentIdField = new JTextField(8);
    private final JTextField statusField = new JTextField(10);
    private final JTextField notesField = new JTextField(14);
    private final JTextField clinicalSummaryField = new JTextField(20);
    private final JTextField investigationsField = new JTextField(20);

    public ReferralPanel(DataStore store) {
        this.store = store;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ---- Form ----
        JPanel form = new JPanel();

        referralIdField.setEditable(false);

        form.add(new JLabel("Referral ID:"));
        form.add(referralIdField);

        form.add(new JLabel("Patient ID:"));
        form.add(patientIdField);

        form.add(new JLabel("Ref Clinician ID:"));
        form.add(refClinicianIdField);

        form.add(new JLabel("To Clinician ID:"));
        form.add(toClinicianIdField);

        form.add(new JLabel("Ref Facility ID:"));
        form.add(refFacilityIdField);

        form.add(new JLabel("To Facility ID:"));
        form.add(toFacilityIdField);

        form.add(new JLabel("Referral Date:"));
        form.add(referralDateField);

        form.add(new JLabel("Urgency:"));
        form.add(urgencyField);

        form.add(new JLabel("Reason:"));
        form.add(reasonField);

        form.add(new JLabel("Appointment ID:"));
        form.add(appointmentIdField);

        form.add(new JLabel("Status:"));
        form.add(statusField);

        form.add(new JLabel("Notes:"));
        form.add(notesField);

        JButton addBtn = new JButton("Add Referral"); // ✅ ONLY BUTTON
        form.add(addBtn);

        add(form);

        JPanel extra = new JPanel();
        extra.add(new JLabel("Clinical Summary:"));
        extra.add(clinicalSummaryField);
        extra.add(new JLabel("Investigations:"));
        extra.add(investigationsField);
        add(extra);

        // ---- Table ----
        String[] cols = {
                "Referral ID", "Patient ID",
                "Ref Clinician", "To Clinician",
                "Ref Facility", "To Facility",
                "Referral Date", "Urgency", "Reason",
                "Clinical Summary", "Investigations",
                "Status", "Appointment ID", "Notes",
                "Created", "Last Updated"
        };

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();

        addBtn.addActionListener(e -> addReferral());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Referral r : store.getReferrals().values()) {
            tableModel.addRow(new Object[]{
                    r.getReferralId(),
                    r.getPatientId(),
                    r.getReferringClinicianId(),
                    r.getReferredToClinicianId(),
                    r.getReferringFacilityId(),
                    r.getReferredToFacilityId(),
                    r.getReferralDate(),
                    r.getUrgencyLevel(),
                    r.getReferralReason(),
                    r.getClinicalSummary(),
                    r.getRequestedInvestigations(),
                    r.getStatus(),
                    r.getAppointmentId(),
                    r.getNotes(),
                    r.getCreatedDate(),
                    r.getLastUpdated()
            });
        }
    }

    private void addReferral() {
        if (patientIdField.getText().trim().isEmpty()
                || refClinicianIdField.getText().trim().isEmpty()
                || toClinicianIdField.getText().trim().isEmpty()
                || urgencyField.getText().trim().isEmpty()
                || reasonField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Fill Patient ID, Ref Clinician ID, To Clinician ID, Urgency, and Reason.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = "R" + String.format("%03d", store.getReferrals().size() + 1);
        String today = LocalDate.now().toString();
        String nowTime = LocalDateTime.now().toString();

        String referralDate = referralDateField.getText().trim().isEmpty() ? today : referralDateField.getText().trim();
        String status = statusField.getText().trim().isEmpty() ? "CREATED" : statusField.getText().trim();

        Referral referral = new Referral(
                newId,
                patientIdField.getText().trim(),
                refClinicianIdField.getText().trim(),
                toClinicianIdField.getText().trim(),
                refFacilityIdField.getText().trim(),
                toFacilityIdField.getText().trim(),
                referralDate,
                urgencyField.getText().trim(),
                reasonField.getText().trim(),
                clinicalSummaryField.getText().trim(),
                investigationsField.getText().trim(),
                status,
                appointmentIdField.getText().trim(),
                notesField.getText().trim(),
                nowTime,
                nowTime
        );

        store.addReferral(referral);
        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Referral added.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        referralIdField.setText("");
        patientIdField.setText("");
        refClinicianIdField.setText("");
        toClinicianIdField.setText("");
        refFacilityIdField.setText("");
        toFacilityIdField.setText("");
        referralDateField.setText("");
        urgencyField.setText("");
        reasonField.setText("");
        appointmentIdField.setText("");
        statusField.setText("");
        notesField.setText("");
        clinicalSummaryField.setText("");
        investigationsField.setText("");
    }
}
