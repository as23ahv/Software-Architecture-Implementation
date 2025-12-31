package persistence;

import model.Appointment;
import model.Clinician;
import model.Facility;
import model.Patient;
import model.Prescription;
import model.Referral;
import model.Staff;
import model.store.DataStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public static void loadPatients(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",", -1);
                Patient patient = new Patient(
                        p[0], p[1], p[2], p[3], p[4], p[5], p[6],
                        p[7], p[8], p[9], p[10], p[11], p[12], p[13]
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
                        c[0], c[1], c[2], c[3], c[4], c[5],
                        c[6], c[7], c[8], c[9], c[10], c[11]
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
                        a[0], a[1], a[2], a[3], a[4], a[5],
                        a[6], a[7], a[8], a[9], a[10], a[11], a[12]
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
                        p[0], p[1], p[2], p[3], p[4], p[5], p[6], p[7], p[8], p[9],
                        p[10], p[11], p[12], p[13], p[14]
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
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] r = line.split(",", -1);

                // referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,
                // referring_facility_id,referred_to_facility_id,referral_date,urgency_level,
                // referral_reason,clinical_summary,requested_investigations,status,appointment_id,
                // notes,created_date,last_updated
                Referral referral = new Referral(
                        r[0],  // referral_id
                        r[1],  // patient_id
                        r[2],  // referring_clinician_id
                        r[3],  // referred_to_clinician_id
                        r[4],  // referring_facility_id
                        r[5],  // referred_to_facility_id
                        r[6],  // referral_date
                        r[7],  // urgency_level
                        r[8],  // referral_reason
                        r[9],  // clinical_summary
                        r[10], // requested_investigations
                        r[11], // status
                        r[12], // appointment_id
                        r[13], // notes
                        r[14], // created_date
                        r[15]  // last_updated
                );

                store.addReferral(referral);
            }
        } catch (IOException e) {
            System.out.println("Error loading referrals");
            e.printStackTrace();
        }
    }

    public static void loadStaff(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(",", -1);
                Staff staff = new Staff(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7]);
                store.addStaff(staff);
            }
        } catch (IOException e) {
            System.out.println("Error loading staff");
            e.printStackTrace();
        }
    }

    public static void loadFacilities(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] f = line.split(",", -1);
                Facility facility = new Facility(
                        f[0], f[1], f[2], f[3],
                        f[4], f[5], f[6], f[7]
                );
                store.addFacility(facility);
            }
        } catch (IOException e) {
            System.out.println("Error loading facilities");
            e.printStackTrace();
        }
    }
}
