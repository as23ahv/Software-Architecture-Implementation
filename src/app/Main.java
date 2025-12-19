package app;

import model.store.DataStore;
import persistence.FileLoader;
import view.MainFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        DataStore store = new DataStore();

        FileLoader.loadPatients("data/patients.csv", store);
        FileLoader.loadClinicians("data/clinicians.csv", store);
        FileLoader.loadAppointments("data/appointments.csv", store);
        FileLoader.loadPrescriptions("data/prescriptions.csv", store);

        System.out.println("Patients loaded: " + store.getPatients().size());
        System.out.println("Clinicians loaded: " + store.getClinicians().size());
        System.out.println("Appointments loaded: " + store.getAppointments().size());
        System.out.println("Prescriptions loaded: " + store.getPrescriptions().size());

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(store);
            frame.setVisible(true);
        });
    }
}
