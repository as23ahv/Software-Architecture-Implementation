package view;

import model.Prescription;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PrescriptionPanel extends JPanel {

    public PrescriptionPanel(DataStore store) {

        String[] columns = {
                "Prescription ID",
                "Patient ID",
                "Clinician ID",
                "Medication",
                "Dosage",
                "Status"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

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

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}