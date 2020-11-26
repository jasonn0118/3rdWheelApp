package com.example.a3rdwheel.Models;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String userId, email, firstName, lastName, gender, age, phone, driverLN;

    //TODO: Driver licence is private information, we should store it as a secret value.
    public User() {

    }

    public User(String newEmail) {
        this.email = newEmail;
    }

    public User(String newFirstName, String newLastName, String newGender, String newAge, String newPhone, String newDriver) {
        this.firstName = newFirstName;
        this.lastName = newLastName;
        this.gender = newGender;
        this.phone = newPhone;
        this.age = newAge;
        this.driverLN = newDriver;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDriverLN() {
        return driverLN;
    }

    public void setDriverLN(String driverLN) {
        this.driverLN = driverLN;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
