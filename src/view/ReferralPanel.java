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
    private final JTextField referringClinicianField = new JTextField(8);
    private final JTextField referredToClinicianField = new JTextField(8);
    private final JTextField urgencyField = new JTextField(8);
    private final JTextField reasonField = new JTextField(15);
    private final JTextField appointmentIdField = new JTextField(8);

    private final JTextArea summaryArea = new JTextArea(3, 20);
    private final JTextArea investigationsArea = new JTextArea(2, 20);

    public ReferralPanel(DataStore store) {
        this.store = store;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ---- Form ----
        JPanel form = new JPanel();

        form.add(new JLabel("Patient ID:"));
        form.add(patientIdField);

        form.add(new JLabel("Ref Clinician ID:"));
        form.add(referringClinicianField);

        form.add(new JLabel("To Clinician ID:"));
        form.add(referredToClinicianField);

        form.add(new JLabel("Urgency:"));
        form.add(urgencyField);

        form.add(new JLabel("Reason:"));
        form.add(reasonField);

        form.add(new JLabel("Appointment ID:"));
        form.add(appointmentIdField);

        JButton addBtn = new JButton("Create Referral");
        form.add(addBtn);

        add(form);

        // Text areas
        JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Clinical Summary:"));
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        textPanel.add(new JScrollPane(summaryArea));

        textPanel.add(new JLabel("Investigations:"));
        investigationsArea.setLineWrap(true);
        investigationsArea.setWrapStyleWord(true);
        textPanel.add(new JScrollPane(investigationsArea));

        add(textPanel);

        // ---- Table ----
        String[] columns = {"Referral ID", "Patient ID", "Urgency", "Reason", "Status"};
        tableModel = new DefaultTableModel(columns, 0);

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();

        addBtn.addActionListener(e -> createReferral());
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
        String patientId = patientIdField.getText().trim();
        String refClinician = referringClinicianField.getText().trim();
        String toClinician = referredToClinicianField.getText().trim();
        String urgency = urgencyField.getText().trim();
        String reason = reasonField.getText().trim();
        String appointmentId = appointmentIdField.getText().trim();
        String summary = summaryArea.getText().trim();
        String investigations = investigationsArea.getText().trim();

        if (patientId.isEmpty() || refClinician.isEmpty() || toClinician.isEmpty() || urgency.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Fill Patient ID, Ref Clinician ID, To Clinician ID, Urgency and Reason.",
                    "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Simple new ID
        String newId = "R" + String.format("%03d", store.getReferrals().size() + 1);

        String today = LocalDate.now().toString();
        String now = LocalDateTime.now().toString();

        Referral newReferral = new Referral(
                newId,                 // referralId
                patientId,             // patientId
                refClinician,          // referringClinicianId
                toClinician,           // referredToClinicianId
                "",                    // referringFacilityId (blank for now)
                "",                    // referredToFacilityId (blank for now)
                today,                 // referralDate
                urgency,               // urgencyLevel
                reason,                // referralReason
                summary,               // clinicalSummary
                investigations,        // requestedInvestigations
                "CREATED",             // status
                appointmentId,         // appointmentId
                "",                    // notes
                today,                 // createdDate
                now                    // lastUpdated
        );

        // Save in datastore
        store.addReferral(newReferral);

        // Singleton: queue and process immediately (creates output files)
        ReferralManager manager = ReferralManager.getInstance();
        manager.enqueueReferral(newReferral);
        manager.processNextReferral(store);

        refreshTable();

        // Clear inputs
        patientIdField.setText("");
        referringClinicianField.setText("");
        referredToClinicianField.setText("");
        urgencyField.setText("");
        reasonField.setText("");
        appointmentIdField.setText("");
        summaryArea.setText("");
        investigationsArea.setText("");

        JOptionPane.showMessageDialog(this,
                "Referral created and email text written to output/referrals_emails.txt",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
