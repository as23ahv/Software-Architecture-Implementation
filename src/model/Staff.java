package model;

/*
 * Represents a non-clinical staff member (admin, receptionist, support, etc.).
 */
public class Staff {

    private String staffId;
    private String firstName;
    private String lastName;
    private String role;
    private String department;
    private String facilityId;
    private String phone;
    private String email;

    public Staff(String staffId, String firstName, String lastName,
                 String role, String department, String facilityId,
                 String phone, String email) {

        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.facilityId = facilityId;
        this.phone = phone;
        this.email = email;
    }

    public String getStaffId() { return staffId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getRole() { return role; }
    public String getDepartment() { return department; }
    public String getFacilityId() { return facilityId; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
