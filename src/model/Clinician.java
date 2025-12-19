package model;

/*
 * Represents a clinician (doctor, nurse, specialist, etc.).
 * Kept simple because data comes from clinicians.csv.
 */
public class Clinician {

    private String clinicianId;
    private String firstName;
    private String lastName;
    private String role;
    private String qualification;
    private String specialty;
    private String facilityId;

    public Clinician(String clinicianId, String firstName, String lastName,
                     String role, String qualification, String specialty, String facilityId) {
        this.clinicianId = clinicianId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.qualification = qualification;
        this.specialty = specialty;
        this.facilityId = facilityId;
    }

    public String getClinicianId() { return clinicianId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getRole() { return role; }
    public String getQualification() { return qualification; }
    public String getSpecialty() { return specialty; }
    public String getFacilityId() { return facilityId; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName() + " (" + role + ")";
    }
}
