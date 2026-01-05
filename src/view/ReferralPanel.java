package view;

import model.Referral;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReferralPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;

    public ReferralPanel(DataStore store) {
        this.store = store;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ✅ Full referrals.csv columns (16)
        String[] cols = {
                "Referral ID",
                "Patient ID",
                "Referring Clinician ID",
                "Referred To Clinician ID",
                "Referring Facility ID",
                "Referred To Facility ID",
                "Referral Date",
                "Urgency Level",
                "Referral Reason",
                "Clinical Summary",
                "Requested Investigations",
                "Status",
                "Appointment ID",
                "Notes",
                "Created Date",
                "Last Updated"
        };

        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();
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
}
