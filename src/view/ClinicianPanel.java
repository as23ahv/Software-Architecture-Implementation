package view;

import model.Clinician;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClinicianPanel extends JPanel {

    public ClinicianPanel(DataStore store) {

        String[] columns = {"Clinician ID", "Name", "Role", "Specialty", "Facility ID"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Clinician c : store.getClinicians().values()) {
            tableModel.addRow(new Object[]{
                    c.getClinicianId(),
                    c.getFullName(),
                    c.getRole(),
                    c.getSpecialty(),
                    c.getFacilityId()
            });
        }

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}
