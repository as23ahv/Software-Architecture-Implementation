package view;

import model.Patient;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public PatientPanel(DataStore store) {
        this.store = store;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] columns = {
                "Patient ID", "First Name", "Last Name",
                "DOB", "NHS No", "Gender",
                "Phone", "Email", "Address", "Postcode",
                "Emergency Contact", "Emergency Phone",
                "Registered", "GP Surgery"
        };

        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        add(new JScrollPane(table));

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        for (Patient p : store.getPatients().values()) {
            tableModel.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getDateOfBirth(),
                    p.getNhsNumber(),
                    p.getGender(),
                    p.getPhoneNumber(),
                    p.getEmail(),
                    p.getAddress(),
                    p.getPostcode(),
                    p.getEmergencyContactName(),
                    p.getEmergencyContactPhone(),
                    p.getRegistrationDate(),
                    p.getGpSurgeryId()
            });
        }
    }
}
