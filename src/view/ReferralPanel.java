package view;

import model.Referral;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReferralPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    // ---- Form fields ----
    private final JTextField patientIdField = new JTextField(8);
    private final JTextField refClinicianIdField = new JTextField(8);
    private final JTextField toClinicianIdField = new JTextField(8);
    private final JTextField refFacilityIdField = new JTextField(8);
    private final JTextField toFacilityIdField = new JTextField(8);
    private final JTextField referralDateField = new JTextField(10);
    private final JTextField urgencyField = new JTextField(10);
    private final JTextField reasonField = new JTextField(16);

    private final JTextField clinicalSummaryField = new JTextField(22);
    private final JTextField investigationsField = new JTextField(22);

    private final JTextField appointmentIdField = new JTextField(8);
    private final JTextField statusField = new JTextField(10);
    private final JTextField notesField = new JTextField(18);

    public ReferralPanel(DataStore store) {
        this.store = store;
        setLayout(new BorderLayout(8, 8));

        // ================= FORM (TOP) =================
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;

        // Row 1
        gbc.gridx = 0; topPanel.add(new JLabel("Patient ID:"), gbc);
        gbc.gridx = 1; topPanel.add(patientIdField, gbc);

        gbc.gridx = 2; topPanel.add(new JLabel("Ref Clinician ID:"), gbc);
        gbc.gridx = 3; topPanel.add(refClinicianIdField, gbc);

        gbc.gridx = 4; topPanel.add(new JLabel("To Clinician ID:"), gbc);
        gbc.gridx = 5; topPanel.add(toClinicianIdField, gbc);

        gbc.gridx = 6; topPanel.add(new JLabel("Ref Facility ID:"), gbc);
        gbc.gridx = 7; topPanel.add(refFacilityIdField, gbc);

        gbc.gridx = 8; topPanel.add(new JLabel("To Facility ID:"), gbc);
        gbc.gridx = 9; topPanel.add(toFacilityIdField, gbc);

        // Row 2
        gbc.gridy = 1;

        gbc.gridx = 0; topPanel.add(new JLabel("Referral Date:"), gbc);
        gbc.gridx = 1; topPanel.add(referralDateField, gbc);

        gbc.gridx = 2; topPanel.add(new JLabel("Urgency:"), gbc);
        gbc.gridx = 3; topPanel.add(urgencyField, gbc);

        gbc.gridx = 4; topPanel.add(new JLabel("Reason:"), gbc);
        gbc.gridx = 5; gbc.gridwidth = 2; topPanel.add(reasonField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 7; topPanel.add(new JLabel("Clinical Summary:"), gbc);
        gbc.gridx = 8; topPanel.add(clinicalSummaryField, gbc);

        gbc.gridx = 9; topPanel.add(new JLabel("Investigations:"), gbc);

        // Row 3
        gbc.gridy = 2;

        gbc.gridx = 0; topPanel.add(new JLabel("Investigations:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; topPanel.add(investigationsField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 3; topPanel.add(new JLabel("Appointment ID:"), gbc);
        gbc.gridx = 4; topPanel.add(appointmentIdField, gbc);

        gbc.gridx = 5; topPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 6; topPanel.add(statusField, gbc);

        gbc.gridx = 7; topPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 8; topPanel.add(notesField, gbc);

        JButton addBtn = new JButton("Add Referral");
        gbc.gridx = 9; topPanel.add(addBtn, gbc);

        add(topPanel, BorderLayout.NORTH);

        // ================= TABLE (CENTER) =================
        String[] cols = {
                "Referral ID", "Patient ID", "Ref Clinician", "To Clinician",
                "Ref Facility", "To Facility", "Referral Date", "Urgency",
                "Reason", "Clinical Summary", "Investigations",
                "Status", "Appointment ID", "Notes",
                "Created", "Last Updated"
        };

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        // Make table readable like other tabs
        table.setRowHeight(24);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] widths = {
                80, 80, 90, 90, 90, 90,
                110, 90, 200, 220, 220,
                100, 110, 220, 120, 120
        };
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        refreshTable();

        // Only Add Referral (as required)
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
                    "Fill Patient ID, Clinician IDs, Urgency and Reason.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newId = "R" + String.format("%03d", store.getReferrals().size() + 1);
        String today = LocalDate.now().toString();
        String now = LocalDateTime.now().toString();

        Referral referral = new Referral(
                newId,
                patientIdField.getText().trim(),
                refClinicianIdField.getText().trim(),
                toClinicianIdField.getText().trim(),
                refFacilityIdField.getText().trim(),
                toFacilityIdField.getText().trim(),
                referralDateField.getText().trim().isEmpty() ? today : referralDateField.getText().trim(),
                urgencyField.getText().trim(),
                reasonField.getText().trim(),
                clinicalSummaryField.getText().trim(),
                investigationsField.getText().trim(),
                statusField.getText().trim().isEmpty() ? "CREATED" : statusField.getText().trim(),
                appointmentIdField.getText().trim(),
                notesField.getText().trim(),
                now,
                now
        );

        store.addReferral(referral);
        refreshTable();
        clearForm();

        JOptionPane.showMessageDialog(this,
                "Referral added successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        patientIdField.setText("");
        refClinicianIdField.setText("");
        toClinicianIdField.setText("");
        refFacilityIdField.setText("");
        toFacilityIdField.setText("");
        referralDateField.setText("");
        urgencyField.setText("");
        reasonField.setText("");
        clinicalSummaryField.setText("");
        investigationsField.setText("");
        appointmentIdField.setText("");
        statusField.setText("");
        notesField.setText("");
    }
}
