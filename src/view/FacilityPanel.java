package view;

import model.Facility;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FacilityPanel extends JPanel {

    public FacilityPanel(DataStore store) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] cols = {
                "Facility ID", "Name", "Type", "Address", "Postcode",
                "Phone", "Email", "Opening Hours", "Manager",
                "Capacity", "Specialities"
        };

        DefaultTableModel tableModel = new DefaultTableModel(cols, 0);

        for (Facility f : store.getFacilities().values()) {
            tableModel.addRow(new Object[]{
                    f.getFacilityId(),
                    f.getFacilityName(),
                    f.getFacilityType(),
                    f.getAddress(),
                    f.getPostcode(),
                    f.getPhoneNumber(),
                    f.getEmail(),
                    f.getOpeningHours(),
                    f.getManagerName(),
                    f.getCapacity(),
                    f.getSpecialitiesOffered()
            });
        }

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table));
    }
}
