package view;

import model.Appointment;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppointmentPanel extends JPanel {

    public AppointmentPanel(DataStore store) {

        String[] columns = {
                "Appointment ID", "Patient ID", "Clinician ID", "Facility ID",
                "Date", "Time", "Duration(min)", "Type", "Status",
                "Reason", "Notes", "Created", "Last Modified"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        for (Appointment a : store.getAppointments().values()) {
            tableModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianId(),
                    a.getFacilityId(),
                    a.getAppointmentDate(),
                    a.getAppointmentTime(),
                    a.getDurationMinutes(),
                    a.getAppointmentType(),
                    a.getStatus(),
                    a.getReasonForVisit(),
                    a.getNotes(),
                    a.getCreatedDate(),
                    a.getLastModified()
            });
        }

        JTable table = new JTable(tableModel);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}
