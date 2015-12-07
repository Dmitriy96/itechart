package by.itechart.javalab.entity;

/**
 * Created by Dmitriy on 14.11.2015.
 */
public enum PhoneType {
    LANDLINE("Домашний"), MOBILE("Мобильный");

    private String phoneType;

    private PhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneType() {
        return phoneType;
    }
}
