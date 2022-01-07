package Contact;

import java.time.LocalDateTime;

public class Person extends Contact {
    private String firstName;
    private String secondName;
    private String birthDate;
    private String gender;

    public Person(String phoneNumber, String firstName, String secondName, String birthDate, String gender) {
        super(phoneNumber);
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + "\n" +
                "Surname: " + secondName + "\n" +
                "Birth date: " + birthDate + "\n" +
                "Gender: " + gender + "\n" +
                "Number: " + phoneNumber + "\n" +
                "Time created: " + timeCreated.withSecond(0).withNano(0) + "\n" +
                "Time last edit: " + timeLastEdit.withSecond(0).withNano(0) + "\n";
    }

    @Override
    public String canEdit() {
        return "name, surname, birth, gender, number";
    }

    @Override
    public void setter(String field, String value) {
        switch (field){
            case "name":
                this.firstName = value;
                break;
            case "surname":
                this.secondName = value;
                break;
            case "birth":
                this.birthDate = value;
                break;
            case "gender":
                this.gender = value;
                break;
            case "number":
                this.phoneNumber = value;
                break;
        }
        timeLastEdit = LocalDateTime.now();
    }

    @Override
    public String getter(String field) {
        switch (field){
            case "firstName":
                return firstName;
            case "secondName":
                return secondName;
            case "birthDate":
                return birthDate;
            case "gender":
                return gender;
            case "phoneNumber":
                return phoneNumber;
            default:
                return null;
        }
    }

    @Override
    public String info() {
        return firstName + " " + secondName;
    }


}
