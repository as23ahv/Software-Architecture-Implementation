package persistence;

import model.Appointment;
import model.Clinician;
import model.Patient;
import model.Prescription;
import model.Referral;
import model.store.DataStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public static void loadPatients(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1);

                Patient patient = new Patient(
                        p[0], p[1], p[2], p[3],
                        p[4], p[5], p[6], p[7]
                );

                store.addPatient(patient);
            }

        } catch (IOException e) {
            System.out.println("Error loading patients");
            e.printStackTrace();
        }
    }

    public static void loadClinicians(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] c = line.split(",", -1);

                Clinician clinician = new Clinician(
                        c[0], c[1], c[2], c[3],
                        c[4], c[5], c[6]
                );

                store.addClinician(clinician);
            }

        } catch (IOException e) {
            System.out.println("Error loading clinicians");
            e.printStackTrace();
        }
    }

    public static void loadAppointments(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] a = line.split(",", -1);

                Appointment appointment = new Appointment(
                        a[0], a[1], a[2], a[3],
                        a[4], a[5], a[6], a[7]
                );

                store.addAppointment(appointment);
            }

        } catch (IOException e) {
            System.out.println("Error loading appointments");
            e.printStackTrace();
        }
    }

    public static void loadPrescriptions(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1);

                Prescription prescription = new Prescription(
                        p[0], p[1], p[2], p[3],
                        p[4], p[5], p[6], p[7],
                        p[8], p[9], p[10], p[11],
                        p[12], p[13], p[14]
                );

                store.addPrescription(prescription);
            }

        } catch (IOException e) {
            System.out.println("Error loading prescriptions");
            e.printStackTrace();
        }
    }

    public static void loadReferrals(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] r = line.split(",", -1);

                Referral referral = new Referral(
                        r[0], r[1], r[2], r[3],
                        r[4], r[5], r[6], r[7],
                        r[8], r[9], r[10], r[11],
                        r[12], r[13], r[14], r[15]
                );

                store.addReferral(referral);
            }

        } catch (IOException e) {
            System.out.println("Error loading referrals");
            e.printStackTrace();
        }
    }
}
