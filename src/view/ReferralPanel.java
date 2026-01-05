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

    private final JTextField patientIdField = new JTextField(8);
    private final JTextField refClinicianIdField = new JTextField(8);
    private final JTextField toClinicianIdField = new JTextField(8);
    private final JTextField refFacilityIdField = new JTextField(8);
    private final JTextField toFacilityIdField = new JTextField(8);
    private final JTextField urgencyField = new JTextField(10);
    private final JTextField reasonField = new JTextField(14);
    private final JTextField appointmentIdField = new JTextField(8);

    private final JTextField clinicalSummaryField = new JTextField(20);
    private final JTextField investigationsField = new JTextField(20);

    public ReferralPanel(DataStore store) {
        this.store = store;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel form = new JPanel();

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

        JButton createBtn = new JButton("Create Referral");
        form.add(createBtn);

        add(form);

        JPanel extra = new JPanel();
        extra.add(new JLabel("Clinical Summary:"));
        extra.add(clinicalSummaryField);
        extra.add(new JLabel("Investigations:"));
        extra.add(investigationsField);
        add(extra);

        String[] cols = {
                "Referral ID", "Patient ID", "Urgency",
                "Reason", "Status"
        };

        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();

        createBtn.addActionListener(e -> createReferral());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Referral r : store.getReferrals().values()) {
            tableModel.addRow(new Object[]{
                    r.getReferralId(),
                    r.getPatientId(),
                    r.getUrgencyLevel(),
                    r.getReferralReason(),
                    r.getStatus()
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
                    "Fill Patient ID, Clinician IDs, Urgency and Reason",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = "R" + String.format("%03d", store.getReferrals().size() + 1);
        String now = LocalDateTime.now().toString();

        Referral referral = new Referral(
                id,
                patientIdField.getText().trim(),
                refClinicianIdField.getText().trim(),
                toClinicianIdField.getText().trim(),
                refFacilityIdField.getText().trim(),
                toFacilityIdField.getText().trim(),
                LocalDate.now().toString(),
                urgencyField.getText().trim(),
                reasonField.getText().trim(),
                clinicalSummaryField.getText().trim(),
                investigationsField.getText().trim(),
                "CREATED",
                appointmentIdField.getText().trim(),
                "",
                now,
                now
        );

        store.addReferral(referral);

        ReferralManager.getInstance().enqueueReferral(referral);
        ReferralManager.getInstance().processNextReferral(store);

        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Referral created and processed.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        patientIdField.setText("");
        refClinicianIdField.setText("");
        toClinicianIdField.setText("");
        refFacilityIdField.setText("");
        toFacilityIdField.setText("");
        urgencyField.setText("");
        reasonField.setText("");
        appointmentIdField.setText("");
        clinicalSummaryField.setText("");
        investigationsField.setText("");
    }
}
