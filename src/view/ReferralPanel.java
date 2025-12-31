package view;

import model.Referral;
import model.store.DataStore;
import model.store.ReferralManager;

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

        JButton createBtn = new JButton("Create Referral");
        JButton loadBtn = new JButton("Load Selected");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        form.add(createBtn);
        form.add(loadBtn);
        form.add(updateBtn);
        form.add(deleteBtn);

        add(form);

        JPanel extra = new JPanel();
        extra.add(new JLabel("Clinical Summary:"));
        extra.add(clinicalSummaryField);
        extra.add(new JLabel("Investigations:"));
        extra.add(investigationsField);
        add(extra);

        String[] cols = {
                "Referral ID", "Patient ID", "Ref Clinician", "To Clinician",
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

        createBtn.addActionListener(e -> createReferral());
        loadBtn.addActionListener(e -> loadSelected());
        updateBtn.addActionListener(e -> updateSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
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

    private void createReferral() {
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
        String nowDate = LocalDate.now().toString();
        String nowTime = LocalDateTime.now().toString();

        String referralDate = referralDateField.getText().trim().isEmpty()
                ? nowDate
                : referralDateField.getText().trim();

        String status = statusField.getText().trim().isEmpty()
                ? "CREATED"
                : statusField.getText().trim();

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

        // ✅ Correct calls for YOUR ReferralManager
        ReferralManager.getInstance().enqueueReferral(referral);
        ReferralManager.getInstance().processNextReferral(store);

        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Referral created and processed (email + audit log generated).",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();
        Referral r = store.getReferrals().get(id);
        if (r == null) return;

        referralIdField.setText(r.getReferralId());
        patientIdField.setText(r.getPatientId());
        refClinicianIdField.setText(r.getReferringClinicianId());
        toClinicianIdField.setText(r.getReferredToClinicianId());
        refFacilityIdField.setText(r.getReferringFacilityId());
        toFacilityIdField.setText(r.getReferredToFacilityId());
        referralDateField.setText(r.getReferralDate());
        urgencyField.setText(r.getUrgencyLevel());
        reasonField.setText(r.getReferralReason());
        clinicalSummaryField.setText(r.getClinicalSummary());
        investigationsField.setText(r.getRequestedInvestigations());
        statusField.setText(r.getStatus());
        appointmentIdField.setText(r.getAppointmentId());
        notesField.setText(r.getNotes());
    }

    private void updateSelected() {
        String id = referralIdField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Click 'Load Selected' first, then update.",
                    "No Referral Loaded",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nowTime = LocalDateTime.now().toString();
        String created = store.getReferrals().get(id) == null ? nowTime : store.getReferrals().get(id).getCreatedDate();

        Referral updated = new Referral(
                id,
                patientIdField.getText().trim(),
                refClinicianIdField.getText().trim(),
                toClinicianIdField.getText().trim(),
                refFacilityIdField.getText().trim(),
                toFacilityIdField.getText().trim(),
                referralDateField.getText().trim(),
                urgencyField.getText().trim(),
                reasonField.getText().trim(),
                clinicalSummaryField.getText().trim(),
                investigationsField.getText().trim(),
                statusField.getText().trim(),
                appointmentIdField.getText().trim(),
                notesField.getText().trim(),
                created,
                nowTime
        );

        store.getReferrals().put(id, updated);
        refreshTable();
        clearForm();
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete referral " + id + " from the app?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        store.getReferrals().remove(id);
        refreshTable();
        clearForm();
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
