package persistence;

import model.*;
import model.store.DataStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileLoader {

    // ---- CSV helper: supports quoted fields with commas ----
    private static String[] splitCsvLine(String line) {
        ArrayList<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                parts.add(current.toString().trim().replaceAll("^\"|\"$", ""));
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        parts.add(current.toString().trim().replaceAll("^\"|\"$", ""));
        return parts.toArray(new String[0]);
    }

    public static void loadPatients(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] p = splitCsvLine(line);

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
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] c = splitCsvLine(line);

                // Expected columns:
                // clinician_id,first_name,last_name,title,speciality,gmc_number,phone_number,email,
                // workplace_id,workplace_type,employment_status,start_date

                String clinicianId = c[0];
                String firstName = c[1];
                String lastName = c[2];
                String title = c[3];
                String speciality = c[4];
                String gmcNumber = c[5];
                String phone = c[6];
                String email = c[7];
                String workplaceId = c[8];
                String workplaceType = c[9];
                String employmentStatus = c[10];
                String startDate = c[11];

                String lowerTitle = title == null ? "" : title.toLowerCase();

                Clinician clinician;
                if (lowerTitle.contains("nurse")) {
                    clinician = new Nurse(clinicianId, firstName, lastName, title, speciality, gmcNumber,
                            phone, email, workplaceId, workplaceType, employmentStatus, startDate);
                } else if (lowerTitle.contains("gp") || lowerTitle.contains("general")) {
                    clinician = new GeneralPractitioner(clinicianId, firstName, lastName, title, speciality, gmcNumber,
                            phone, email, workplaceId, workplaceType, employmentStatus, startDate);
                } else {
                    clinician = new SpecialistDoctor(clinicianId, firstName, lastName, title, speciality, gmcNumber,
                            phone, email, workplaceId, workplaceType, employmentStatus, startDate);
                }

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
                String[] a = splitCsvLine(line);

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
                String[] p = splitCsvLine(line);

                Prescription prescription = new Prescription(
                        p[0], p[1], p[2], p[3], p[4], p[5],
                        p[6], p[7], p[8], p[9], p[10],
                        p[11], p[12], p[13], p[14]
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
                String[] r = splitCsvLine(line);

                Referral referral = new Referral(
                        r[0], r[1], r[2], r[3], r[4], r[5],
                        r[6], r[7], r[8], r[9], r[10], r[11],
                        r[12], r[13], r[14], r[15]
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
                String[] s = splitCsvLine(line);

                Staff staff = new Staff(
                        s[0], s[1], s[2], s[3], s[4], s[5],
                        s[6], s[7], s[8], s[9], s[10], s[11]
                );

                store.addStaff(staff);
            }
        } catch (IOException e) {
            System.out.println("Error loading staff");
            e.printStackTrace();
        }
    }

    public static void loadFacilities(String filePath, DataStore store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] f = splitCsvLine(line);

                Facility facility = new Facility(
                        f[0], f[1], f[2], f[3], f[4],
                        f[5], f[6], f[7], f[8], f[9], f[10]
                );

                store.addFacility(facility);
            }
        } catch (IOException e) {
            System.out.println("Error loading facilities");
            e.printStackTrace();
        }
    }
}
