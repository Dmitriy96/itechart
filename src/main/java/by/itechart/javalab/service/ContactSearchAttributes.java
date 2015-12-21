package by.itechart.javalab.service;


import by.itechart.javalab.entity.Address;
import by.itechart.javalab.entity.Gender;
import by.itechart.javalab.entity.MaritalStatus;

import java.util.Date;

public class ContactSearchAttributes {
    private String name;
    private String surname;
    private String patronymic;
    private String citizenship;
    private Date birthdayDateFrom;
    private Date birthdayDateTo;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Date getBirthdayDateFrom() {
        return birthdayDateFrom;
    }

    public void setBirthdayDateFrom(Date birthdayDateFrom) {
        this.birthdayDateFrom = birthdayDateFrom;
    }

    public Date getBirthdayDateTo() {
        return birthdayDateTo;
    }

    public void setBirthdayDateTo(Date birthdayDateTo) {
        this.birthdayDateTo = birthdayDateTo;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
