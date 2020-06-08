package Model;

import java.io.Serializable;

public class Info implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String carrierEnum;
    private String localDate;

    public Info (String firstName, String lastName, String email, String phoneNumber, String carrierEnum, String localDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.carrierEnum= carrierEnum;
        this.localDate = localDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String  getCarrierEnum() {
        return carrierEnum;
    }

    public String getDate() {
        return localDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCarrierEnum(String carrierEnum) {
        this.carrierEnum = carrierEnum;
    }

    public void setDate(String localDate) {
        this.localDate = localDate;
    }
}
