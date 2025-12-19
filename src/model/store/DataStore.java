package model.store;

import model.Patient;
import model.Clinician;
import model.Appointment;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataStore {

    private Map<String, Patient> patients = new LinkedHashMap<>();
    private Map<String, Clinician> clinicians = new LinkedHashMap<>();
    private Map<String, Appointment> appointments = new LinkedHashMap<>();

    // Patients
    public Map<String, Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient p) {
        if (p != null) {
            patients.put(p.getPatientId(), p);
        }
    }

    // Clinicians
    public Map<String, Clinician> getClinicians() {
        return clinicians;
    }

    public void addClinician(Clinician c) {
        if (c != null) {
            clinicians.put(c.getClinicianId(), c);
        }
    }

    // Appointments
    public Map<String, Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment a) {
        if (a != null) {
            appointments.put(a.getAppointmentId(), a);
        }
    }
}
