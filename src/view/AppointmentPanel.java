package view;

import model.Appointment;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppointmentPanel extends JPanel {

    public AppointmentPanel(DataStore store) {

        String[] columns = {
                "Appointment ID",
                "Patient ID",
                "Clinician ID",
                "Date",
                "Time",
                "Status"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Appointment a : store.getAppointments().values()) {
            tableModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianId(),
                    a.getAppointmentDate(),
                    a.getAppointmentTime(),
                    a.getStatus()
            });
        }

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}
