package by.itechart.javalab.entity;


public class ContactPhone {
    private Long idPhone;
    private Integer countryCode;
    private Integer operatorCode;
    private Integer phoneNumber;
    private PhoneType phoneType;
    private String comment;

    public Long getIdPhone() {
        return idPhone;
    }

    public void setIdPhone(Long idPhone) {
        this.idPhone = idPhone;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
