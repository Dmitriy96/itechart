package by.itechart.javalab.entity;

import java.util.Date;


public class ContactAttachment {
    private Long idAttachment;
    private String fileName;
    private String realFileName;
    private Date uploadDate;
    private String comment;
    private Long idContact;

    public Long getIdAttachment() {
        return idAttachment;
    }

    public void setIdAttachment(Long idAttachment) {
        this.idAttachment = idAttachment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRealFileName() {
        return realFileName;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
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
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
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
        ContactAttachment attachment = (ContactAttachment) obj;
        if (idAttachment != null && !idAttachment.equals(attachment.idAttachment))
            return false;
        if (fileName != null && !fileName.equals(attachment.fileName))
            return false;
        return true;
    }
}
