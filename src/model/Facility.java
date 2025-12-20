package model;

/*
 * Represents a healthcare facility (GP surgery / hospital).
 */
public class Facility {

    private String facilityId;
    private String name;
    private String type;
    private String address;
    private String phone;
    private String email;
    private String services;
    private String capacity;

    public Facility(String facilityId, String name, String type,
                    String address, String phone, String email,
                    String services, String capacity) {

        this.facilityId = facilityId;
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.services = services;
        this.capacity = capacity;
    }

    public String getFacilityId() { return facilityId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getServices() { return services; }
    public String getCapacity() { return capacity; }
}
