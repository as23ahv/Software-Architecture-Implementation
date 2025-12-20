package view;

import model.store.DataStore;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

    public MainFrame(DataStore store) {
        setTitle("Healthcare Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Patients", new PatientPanel(store));
        tabs.addTab("Clinicians", new ClinicianPanel(store));
        tabs.addTab("Appointments", new AppointmentPanel(store));
        tabs.addTab("Prescriptions", new PrescriptionPanel(store));
        tabs.addTab("Referrals", new ReferralPanel(store));
        tabs.addTab("Staff", new StaffPanel(store));

        setContentPane(tabs);
        setLocationRelativeTo(null);
    }
}
