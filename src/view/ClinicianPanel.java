package view;

import model.Clinician;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClinicianPanel extends JPanel {

    public ClinicianPanel(DataStore store) {

        String[] columns = {
                "Clinician ID", "First Name", "Last Name", "Title",
                "Speciality", "GMC Number", "Phone", "Email",
                "Workplace ID", "Workplace Type", "Employment Status", "Start Date"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Clinician c : store.getClinicians().values()) {
            tableModel.addRow(new Object[]{
                    c.getClinicianId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getTitle(),
                    c.getSpeciality(),
                    c.getGmcNumber(),
                    c.getPhoneNumber(),   // ✅ phone under Phone column
                    c.getEmail(),
                    c.getWorkplaceId(),
                    c.getWorkplaceType(),
                    c.getEmploymentStatus(),
                    c.getStartDate()
            });
        }

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}
