package model.store;

import model.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataStore {

    private final Map<String, Patient> patients = new LinkedHashMap<>();
    private final Map<String, Clinician> clinicians = new LinkedHashMap<>();
    private final Map<String, Appointment> appointments = new LinkedHashMap<>();
    private final Map<String, Prescription> prescriptions = new LinkedHashMap<>();
    private final Map<String, Referral> referrals = new LinkedHashMap<>();
    private final Map<String, Staff> staff = new LinkedHashMap<>();
    private final Map<String, Facility> facilities = new LinkedHashMap<>();

    public Map<String, Patient> getPatients() { return patients; }
    public Map<String, Clinician> getClinicians() { return clinicians; }
    public Map<String, Appointment> getAppointments() { return appointments; }
    public Map<String, Prescription> getPrescriptions() { return prescriptions; }
    public Map<String, Referral> getReferrals() { return referrals; }
    public Map<String, Staff> getStaff() { return staff; }
    public Map<String, Facility> getFacilities() { return facilities; }

    public void addPatient(Patient p) { patients.put(p.getPatientId(), p); }
    public void addClinician(Clinician c) { clinicians.put(c.getClinicianId(), c); }
    public void addAppointment(Appointment a) { appointments.put(a.getAppointmentId(), a); }
    public void addPrescription(Prescription p) { prescriptions.put(p.getPrescriptionId(), p); }
    public void addReferral(Referral r) { referrals.put(r.getReferralId(), r); }
    public void addStaff(Staff s) { staff.put(s.getStaffId(), s); }
    public void addFacility(Facility f) { facilities.put(f.getFacilityId(), f); }

    public void removeClinician(String clinicianId) {
        clinicians.remove(clinicianId);
    }
}
