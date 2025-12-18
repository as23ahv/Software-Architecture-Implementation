package model.store;

import model.Patient;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * Holds application data in memory (no database in this assignment).
 */
public class DataStore {

    private Map<String, Patient> patients = new LinkedHashMap<>();

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
}
