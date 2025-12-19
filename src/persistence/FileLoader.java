package persistence;

import model.Appointment;
import model.Clinician;
import model.Patient;
import model.Prescription;
import model.store.DataStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public static void loadPatients(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // skip header

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

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

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

    public static void loadAppointments(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                Appointment appointment = new Appointment(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5],
                        parts[6],
                        parts[7]
                );

                store.addAppointment(appointment);
            }

        } catch (IOException e) {
            System.out.println("Error loading appointments file");
            e.printStackTrace();
        }
    }

    public static void loadPrescriptions(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                Prescription prescription = new Prescription(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5],
                        parts[6],
                        parts[7],
                        parts[8],
                        parts[9],
                        parts[10],
                        parts[11],
                        parts[12],
                        parts[13],
                        parts[14]
                );

                store.addPrescription(prescription);
            }

        } catch (IOException e) {
            System.out.println("Error loading prescriptions file");
            e.printStackTrace();
        }
    }
}
