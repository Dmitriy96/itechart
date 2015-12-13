package by.itechart.javalab.entity;


public class ContactPhone {
    private Long idPhone;
    private Integer countryCode;
    private Integer operatorCode;
    private Integer phoneNumber;
    private PhoneType phoneType;
    private String comment;
    private Long idContact;

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

    public Long getIdContact() {
        return idContact;
    }

    public void setIdContact(Long idContact) {
        this.idContact = idContact;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContactPhone phone = (ContactPhone) obj;
        if (idPhone != null && !idPhone.equals(phone.idPhone))
            return false;
        if (phoneNumber != null && !phoneNumber.equals(phone.phoneNumber))
            return false;
        return true;
    }
}
