package view;

import model.Facility;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FacilityPanel extends JPanel {

    public FacilityPanel(DataStore store) {

        String[] columns = {"Facility ID", "Name", "Type", "Phone", "Capacity"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Facility f : store.getFacilities().values()) {
            tableModel.addRow(new Object[]{
                    f.getFacilityId(),
                    f.getName(),
                    f.getType(),
                    f.getPhone(),
                    f.getCapacity()
            });
        }

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}
