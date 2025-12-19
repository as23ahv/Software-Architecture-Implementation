package persistence;

import model.Clinician;
import model.Patient;
import model.store.DataStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public static void loadPatients(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                Patient patient = new Patient(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5],
                        parts[6],
                        parts[7]
                );

                store.addPatient(patient);
            }

        } catch (IOException e) {
            System.out.println("Error loading patients file");
            e.printStackTrace();
        }
    }

    public static void loadClinicians(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                // assumes first 7 columns match this order:
                // clinicianId, firstName, lastName, role, qualification, specialty, facilityId
                Clinician clinician = new Clinician(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5],
                        parts[6]
                );

                store.addClinician(clinician);
            }

        } catch (IOException e) {
            System.out.println("Error loading clinicians file");
            e.printStackTrace();
        }
    }
}
