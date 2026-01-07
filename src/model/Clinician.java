package model;

public abstract class Clinician extends Person {

    private String clinicianId;
    private String title;
    private String speciality;
    private String gmcNumber;
    private String workplaceId;
    private String workplaceType;
    private String employmentStatus;
    private String startDate;

    public Clinician(String clinicianId,
                     String firstName,
                     String lastName,
                     String title,
                     String speciality,
                     String gmcNumber,
                     String phoneNumber,
                     String email,
                     String workplaceId,
                     String workplaceType,
                     String employmentStatus,
                     String startDate) {

        super(clinicianId, firstName, lastName, phoneNumber, "", email);
        this.clinicianId = clinicianId;
        this.title = title;
        this.speciality = speciality;
        this.gmcNumber = gmcNumber;
        this.workplaceId = workplaceId;
        this.workplaceType = workplaceType;
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public String getTitle() {
        return title;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getGmcNumber() {
        return gmcNumber;
    }

    public String getWorkplaceId() {
        return workplaceId;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    // Convenience for table display
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
