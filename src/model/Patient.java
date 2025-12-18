package model;

/*
 * Represents a patient in the healthcare system.
 * Stores basic demographic and contact information.
 */
public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private String nhsNumber;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String gpSurgeryId;

    public Patient(String patientId, String firstName, String lastName,
                   String nhsNumber, String dateOfBirth,
                   String phoneNumber, String email, String gpSurgeryId) {

        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nhsNumber = nhsNumber;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gpSurgeryId = gpSurgeryId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getGpSurgeryId() {
        return gpSurgeryId;
    }

    // Useful for debugging and logging
    @Override
    public String toString() {
        return firstName + " " + lastName + " (NHS: " + nhsNumber + ")";
    }
}
