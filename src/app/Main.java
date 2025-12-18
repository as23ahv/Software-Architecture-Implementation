package app;

import model.store.DataStore;
import persistence.FileLoader;
import view.PatientPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        DataStore store = new DataStore();
        FileLoader.loadPatients("data/patients.csv", store);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Healthcare Management System");
            frame.setSize(900, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setContentPane(new PatientPanel(store));

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
