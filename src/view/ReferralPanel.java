package view;

import model.Referral;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReferralPanel extends JPanel {

    public ReferralPanel(DataStore store) {

        String[] columns = {
                "Referral ID",
                "Patient ID",
                "Urgency",
                "Reason",
                "Status"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Referral r : store.getReferrals().values()) {
            tableModel.addRow(new Object[]{
                    r.getReferralId(),
                    r.getPatientId(),
                    r.getUrgencyLevel(),
                    r.getReferralReason(),
                    r.getStatus()
            });
        }

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}
