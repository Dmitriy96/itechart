package by.itechart.javalab.entity;

import java.util.Date;
import java.util.List;


public class Contact {
    private Long idContact;
    private String name;
    private String surname;
    private String patronymic;
    private Date birthday;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private String citizenship;
    private String website;
    private String email;
    private String company;
    private Address address;
    private List<ContactPhone> phoneList;
    private List<ContactAttachment> attachmentList;

    public Long getIdContact() {
        return idContact;
    }

    public void setIdContact(Long idContact) {
        this.idContact = idContact;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ContactPhone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<ContactPhone> phoneList) {
        this.phoneList = phoneList;
    }

    public List<ContactAttachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<ContactAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
