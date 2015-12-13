package by.itechart.javalab.entity;


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
