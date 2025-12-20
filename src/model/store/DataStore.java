package model.store;

import model.Appointment;
import model.Clinician;
import model.Facility;
import model.Patient;
import model.Prescription;
import model.Referral;
import model.Staff;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataStore {

    private Map<String, Patient> patients = new LinkedHashMap<>();
    private Map<String, Clinician> clinicians = new LinkedHashMap<>();
    private Map<String, Appointment> appointments = new LinkedHashMap<>();
    private Map<String, Prescription> prescriptions = new LinkedHashMap<>();
    private Map<String, Referral> referrals = new LinkedHashMap<>();
    private Map<String, Staff> staff = new LinkedHashMap<>();
    private Map<String, Facility> facilities = new LinkedHashMap<>();

    // Patients
    public Map<String, Patient> getPatients() { return patients; }
    public void addPatient(Patient p) {
        if (p != null) patients.put(p.getPatientId(), p);
    }

    // Clinicians
    public Map<String, Clinician> getClinicians() { return clinicians; }
    public void addClinician(Clinician c) {
        if (c != null) clinicians.put(c.getClinicianId(), c);
    }

    // Appointments
    public Map<String, Appointment> getAppointments() { return appointments; }
    public void addAppointment(Appointment a) {
        if (a != null) appointments.put(a.getAppointmentId(), a);
    }

    // Prescriptions
    public Map<String, Prescription> getPrescriptions() { return prescriptions; }
    public void addPrescription(Prescription p) {
        if (p != null) prescriptions.put(p.getPrescriptionId(), p);
    }

    // Referrals
    public Map<String, Referral> getReferrals() { return referrals; }
    public void addReferral(Referral r) {
        if (r != null) referrals.put(r.getReferralId(), r);
    }

    // Staff
    public Map<String, Staff> getStaff() { return staff; }
    public void addStaff(Staff s) {
        if (s != null) staff.put(s.getStaffId(), s);
    }

    // Facilities
    public Map<String, Facility> getFacilities() { return facilities; }
    public void addFacility(Facility f) {
        if (f != null) facilities.put(f.getFacilityId(), f);
    }
}
