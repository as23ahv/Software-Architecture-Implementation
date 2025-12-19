package model.store;

import model.Patient;
import model.Clinician;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataStore {

    private Map<String, Patient> patients = new LinkedHashMap<>();
    private Map<String, Clinician> clinicians = new LinkedHashMap<>();

    public Map<String, Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient p) {
        if (p == null) return;
        patients.put(p.getPatientId(), p);
    }

    public Patient getPatientById(String id) {
        return patients.get(id);
    }

    public void removePatient(String id) {
        patients.remove(id);
    }

    public Map<String, Clinician> getClinicians() {
        return clinicians;
    }

    public void addClinician(Clinician c) {
        if (c == null) return;
        clinicians.put(c.getClinicianId(), c);
    }

    public Clinician getClinicianById(String id) {
        return clinicians.get(id);
    }

    public void removeClinician(String id) {
        clinicians.remove(id);
    }
}
