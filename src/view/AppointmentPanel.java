package view;

import model.Appointment;
import model.store.DataStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppointmentPanel extends JPanel {

    private final DataStore store;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField appointmentIdField = new JTextField(8);
    private final JTextField patientIdField = new JTextField(8);
    private final JTextField clinicianIdField = new JTextField(8);
    private final JTextField facilityIdField = new JTextField(8);
    private final JTextField dateField = new JTextField(10);
    private final JTextField timeField = new JTextField(8);
    private final JTextField durationField = new JTextField(6);
    private final JTextField typeField = new JTextField(10);
    private final JTextField statusField = new JTextField(10);
    private final JTextField reasonField = new JTextField(14);

    public AppointmentPanel(DataStore store) {
        this.store = store;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel form = new JPanel();

        appointmentIdField.setEditable(false);

        form.add(new JLabel("Appointment ID:"));
        form.add(appointmentIdField);

        form.add(new JLabel("Patient ID:"));
        form.add(patientIdField);

        form.add(new JLabel("Clinician ID:"));
        form.add(clinicianIdField);

        form.add(new JLabel("Facility ID:"));
        form.add(facilityIdField);

        form.add(new JLabel("Date:"));
        form.add(dateField);

        form.add(new JLabel("Time:"));
        form.add(timeField);

        form.add(new JLabel("Duration (mins):"));
        form.add(durationField);

        form.add(new JLabel("Type:"));
        form.add(typeField);

        form.add(new JLabel("Status:"));
        form.add(statusField);

        form.add(new JLabel("Reason:"));
        form.add(reasonField);

        JButton addBtn = new JButton("Add");
        JButton loadBtn = new JButton("Load Selected");
        JButton updateBtn = new JButton("Update Selected");
        JButton deleteBtn = new JButton("Delete Selected");

        form.add(addBtn);
        form.add(loadBtn);
        form.add(updateBtn);
        form.add(deleteBtn);

        add(form);

        String[] cols = {
                "Appointment ID", "Patient ID", "Clinician ID",
                "Facility ID", "Date", "Time",
                "Duration", "Type", "Status", "Reason"
        };

        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table));

        refreshTable();

        addBtn.addActionListener(e -> addAppointment());
        loadBtn.addActionListener(e -> loadSelected());
        updateBtn.addActionListener(e -> updateSelected());
        deleteBtn.addActionListener(e -> deleteSelected());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
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
                    a.getReasonForVisit()
            });
        }
    }

    private void addAppointment() {
        String id = "A" + String.format("%03d", store.getAppointments().size() + 1);

        Appointment a = new Appointment(
                id,
                patientIdField.getText().trim(),
                clinicianIdField.getText().trim(),
                facilityIdField.getText().trim(),
                dateField.getText().trim(),
                timeField.getText().trim(),
                durationField.getText().trim(),
                typeField.getText().trim(),
                statusField.getText().trim(),
                reasonField.getText().trim(),
                "",
                "",
                ""
        );

        store.addAppointment(a);
        refreshTable();
        clearForm();
    }

    private void loadSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();
        Appointment a = store.getAppointments().get(id);
        if (a == null) return;

        appointmentIdField.setText(a.getAppointmentId());
        patientIdField.setText(a.getPatientId());
        clinicianIdField.setText(a.getClinicianId());
        facilityIdField.setText(a.getFacilityId());
        dateField.setText(a.getAppointmentDate());
        timeField.setText(a.getAppointmentTime());
        durationField.setText(a.getDurationMinutes());
        typeField.setText(a.getAppointmentType());
        statusField.setText(a.getStatus());
        reasonField.setText(a.getReasonForVisit());
    }

    private void updateSelected() {
        String id = appointmentIdField.getText().trim();
        if (id.isEmpty()) return;

        Appointment a = new Appointment(
                id,
                patientIdField.getText().trim(),
                clinicianIdField.getText().trim(),
                facilityIdField.getText().trim(),
                dateField.getText().trim(),
                timeField.getText().trim(),
                durationField.getText().trim(),
                typeField.getText().trim(),
                statusField.getText().trim(),
                reasonField.getText().trim(),
                "",
                "",
                ""
        );

        store.getAppointments().put(id, a);
        refreshTable();
        clearForm();
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String id = tableModel.getValueAt(row, 0).toString();
        store.getAppointments().remove(id);

        refreshTable();
        clearForm();
    }

    private void clearForm() {
        appointmentIdField.setText("");
        patientIdField.setText("");
        clinicianIdField.setText("");
        facilityIdField.setText("");
        dateField.setText("");
        timeField.setText("");
        durationField.setText("");
        typeField.setText("");
        statusField.setText("");
        reasonField.setText("");
    }
}
