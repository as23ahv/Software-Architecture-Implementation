package view;

import model.Patient;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientPanel extends JPanel {

    public PatientPanel(DataStore store) {

        String[] columns = {"Patient ID", "First Name", "Last Name", "NHS Number"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Patient p : store.getPatients().values()) {
            tableModel.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getNhsNumber()
            });
        }

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}
