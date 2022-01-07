package Contact;

import java.time.LocalDateTime;

public class Organization extends Contact {
    private String name;
    private String address;

    public Organization(String phoneNumber, String name, String address) {
        super(phoneNumber);
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Organization name: " + name + "\n" +
                "Address: " + address + "\n" +
                "Number: " + phoneNumber + "\n" +
                "Time created: " + timeCreated.withSecond(0).withNano(0) + "\n" +
                "Time last edit: " + timeLastEdit.withSecond(0).withNano(0) + "\n";
    }

    @Override
    public String canEdit() {
        return "name, address, number";
    }

    @Override
    public void setter(String field, String value) {
        switch (field) {
            case "name":
                this.name = value;
                break;
            case "address":
                this.address = value;
                break;
            case "number":
                this.phoneNumber = value;
                break;

        }
        timeLastEdit = LocalDateTime.now();
    }

    @Override
    public String getter(String field) {
        switch (field) {
            case "name":
                return name;
            case "address":
                return address;
            case "phoneNumber":
                return phoneNumber;
            default:
                return null;
        }
    }

    @Override
    public String info() {
        return name;
    }
}
