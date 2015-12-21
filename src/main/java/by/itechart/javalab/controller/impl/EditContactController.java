package by.itechart.javalab.controller.impl;


import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.controller.ControllerException;
import by.itechart.javalab.entity.*;
import by.itechart.javalab.service.ContactAttributesService;
import by.itechart.javalab.service.FindContactService;
import by.itechart.javalab.service.ModificationContactService;
import by.itechart.javalab.service.ServiceException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class EditContactController implements Controller {
    private static Logger log = LogManager.getLogger(EditContactController.class.getName());
    private Map<String, String> formFields = new HashMap<>();
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 100;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 300;
    ServletContext servletContext = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doGet: " + request.getPathInfo());
        String splittedURL[] = request.getPathInfo().split("/");
        if (splittedURL.length != 3) return;
        Contact contact = null;
        try {
            Long contactId = Long.parseLong(splittedURL[2]);
            contact = FindContactService.getContact(contactId);
            request.setAttribute("contact", contact);
            List<String> countries = ContactAttributesService.getAllCountries();
            request.setAttribute("countries", countries);
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/editContact.jsp").forward(request, response);
        } catch (ServiceException | ServletException | IOException | NumberFormatException e) {
            log.error(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doPost: ");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            log.debug("Not multipart type request: " + request.getRequestURI() + " " + request.getContentType());
            return;
        }
        servletContext = request.getServletContext();
        ServletFileUpload upload = createUploadRestrictions();
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            log.error(e);
        }
        List<FileItem> fileItems = parseFormFieldValues(items);
        Contact contact = updateContact(request, response);
        try {
            saveAttachmentsOnDisk(fileItems, contact);
            ModificationContactService.updateContactAttachments(contact);
            response.sendRedirect(request.getContextPath() + "/pages/contacts");
        } catch (Exception ex) {
            log.error(ex);
        }
    }


    private ServletFileUpload createUploadRestrictions() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        upload.setHeaderEncoding("UTF-8");
        return upload;
    }


    private List<FileItem> parseFormFieldValues(List<FileItem> items) {
        try {
            Iterator<FileItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = iterator.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    formFields.put(name, value);
                    iterator.remove();
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return items;
    }


    private Contact updateContact(HttpServletRequest request, HttpServletResponse response) {
        Contact contact = null;
        try {
            contact = new Contact();
            setPersonalData(contact);
            setAddressToContact(contact);
            Map<String, List<ContactPhone>> phoneGroups = getContactPhoneGroups(contact);
            Map<String, List<ContactAttachment>> attachmentGroups = getContactAttachmentGroups(contact);
            ModificationContactService.updateContact(contact, phoneGroups, attachmentGroups);
            contact = FindContactService.getContact(contact.getIdContact());
        } catch (ParseException | ControllerException e) {
            log.error(e);
            request.setAttribute("invalidInput", "Invalid input.");
            returnPage(request, response);
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute("error", "Sorry, contact hasn't been updated.");
            returnPage(request, response);
        }
        return contact;
    }


    private void returnPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/editContact.jsp").forward(request, response);
        } catch (ServletException | IOException e1) {
            log.error(e1);
        }
    }


    private String getDirectoryPath(String directoryName) {
        ServletContext servletContext = getServletContext();
        String attachmentsDirectory = servletContext.getInitParameter(directoryName);
        ensureDirectoryExists(attachmentsDirectory);
        return attachmentsDirectory;
    }

    private ServletContext getServletContext() {
        return servletContext;
    }

    private ContactAttachment getUpdatedAttachment(List<ContactAttachment> updatedAttachments, String fileName) {
        ContactAttachment contactAttachment = null;
        for (ContactAttachment attachment : updatedAttachments) {
            if (attachment.getFileName().equals(fileName) && StringUtils.isEmpty(attachment.getRealFileName())) {
                contactAttachment = attachment;
                break;
            }
        }
        return contactAttachment;
    }

    private void ensureDirectoryExists(String directory) {
        File uploadDir = new File(directory);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }

    private void saveAttachmentsOnDisk(List<FileItem> fileItems, Contact contact) throws Exception {
        Iterator<FileItem> iterator = fileItems.iterator();
        while (iterator.hasNext()) {
            FileItem item = iterator.next();
            String realFileName = item.getName();
            String fieldName = item.getFieldName();
            String filePath = null;
            if ("userImage".equals(item.getFieldName())) {
                String imagesDirectory = getDirectoryPath("imagesDirectory");
                if (realFileName.lastIndexOf(".") != -1) {
                    filePath = imagesDirectory + File.separator + contact.getIdContact() +
                            realFileName.substring(realFileName.lastIndexOf("."));
                    File uploadedFile = new File(filePath);
                    item.write(uploadedFile);
                }
            } else {
                Long attachmentId = null;
                List<ContactAttachment> updatedAttachments = contact.getAttachmentList();
                String fileIndex = fieldName.substring(7);                          // "newfile{fileIndex}"
                String fileName = formFields.get("newfileName" + fileIndex);
                ContactAttachment attachment = getUpdatedAttachment(updatedAttachments, fileName);
                attachmentId = attachment.getIdAttachment();
                attachment.setRealFileName(realFileName);
                String attachmentsDirectory = getDirectoryPath("attachmentsDirectory");
                filePath = attachmentsDirectory + File.separator + attachmentId + "_" + realFileName;
                File uploadedFile = new File(filePath);
                item.write(uploadedFile);
            }
        }
    }


    private void checkCorrectness(String inputName) throws ControllerException {
        if (StringUtils.isEmpty(formFields.get(inputName)))
            throw new ControllerException("Invalid input.");
    }


    private void setPersonalData(Contact contact) throws ParseException, ControllerException {
        checkCorrectness("idContact");
        contact.setIdContact(Long.parseLong(formFields.get("idContact")));
        checkCorrectness("name");
        contact.setName(formFields.get("name"));
        checkCorrectness("surname");
        contact.setSurname(formFields.get("surname"));
        contact.setPatronymic(formFields.get("patronymic"));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date parsed = format.parse(formFields.get("birthday"));
        contact.setBirthday(new Date(parsed.getTime()));
        String gender = formFields.get("gender");
        for (Gender genderValue : Gender.values()) {
            if (genderValue.name().equals(gender))
                contact.setGender(Gender.valueOf(gender));
        }
        contact.setCitizenship(formFields.get("citizenship"));
        contact.setWebsite(formFields.get("website"));
        checkCorrectness("email");
        contact.setEmail(formFields.get("email"));
        checkCorrectness("company");
        contact.setCompany(formFields.get("company"));
        String maritalStatus = formFields.get("marital");
        for (MaritalStatus status : MaritalStatus.values()) {
            if (status.name().equals(maritalStatus)) {
                contact.setMaritalStatus(MaritalStatus.valueOf(maritalStatus));
            }
        }
    }

    private void setAddressToContact(Contact contact) throws ControllerException {
        Address address = new Address();
        checkCorrectness("country");
        address.setCountry(formFields.get("country"));
        address.setCity(formFields.get("city"));
        address.setStreet(formFields.get("street"));
        address.setHouseNumber(formFields.get("houseNumber"));
        address.setApartmentNumber(formFields.get("apartmentNumber"));
        address.setZipCode(formFields.get("zipCode"));
        contact.setAddress(address);
    }

    private Map<String, List<ContactPhone>> getContactPhoneGroups(Contact contact) throws ControllerException {
        Map<String, List<ContactPhone>> phoneGroups = new HashMap<>();
        String actions[] = {"update", "delete"};
        int phonesInitialCount = Integer.parseInt(formFields.get("phonesInitialCount"));
        for (int i = 0; i < actions.length; i++) {
            int j = 0;
            List<ContactPhone> phones = new ArrayList<>();
            while (j < phonesInitialCount) {
                if (formFields.get(actions[i] + "phoneNumber" + j) != null) {
                    ContactPhone phone = new ContactPhone();
                    String countryCodeParameter = formFields.get(actions[i] + "countryCode" + j);
                    phone.setCountryCode(StringUtils.isEmpty(countryCodeParameter) ? null : Integer.parseInt(countryCodeParameter));
                    String operatorCode = formFields.get(actions[i] + "operatorCode" + j);
                    phone.setOperatorCode(StringUtils.isEmpty(operatorCode) ? null : Integer.parseInt(operatorCode));
                    checkCorrectness(actions[i] + "phoneNumber" + j);
                    phone.setPhoneNumber(Integer.parseInt(formFields.get(actions[i] + "phoneNumber" + j)));
                    phone.setPhoneType(PhoneType.valueOf(formFields.get(actions[i] + "phoneType" + j)));
                    phone.setComment(formFields.get(actions[i] + "phoneComment" + j));
                    phone.setIdPhone(Long.parseLong(formFields.get("idPhone" + j)));
                    phone.setIdContact(contact.getIdContact());
                    phones.add(phone);
                }
                j++;
            }
            phoneGroups.put(actions[i], phones);
        }
        int i = 0;
        List<ContactPhone> newContactPhones = new ArrayList<>();
        while (formFields.get("new" + "phoneNumber" + i) != null) {
            ContactPhone phone = new ContactPhone();
            String countryCodeParameter = formFields.get("new" + "countryCode" + i);
            phone.setCountryCode(StringUtils.isEmpty(countryCodeParameter) ? null : Integer.parseInt(countryCodeParameter));
            String operatorCode = formFields.get("new" + "operatorCode" + i);
            phone.setOperatorCode(StringUtils.isEmpty(operatorCode) ? null : Integer.parseInt(operatorCode));
            checkCorrectness("new" + "phoneNumber" + i);
            phone.setPhoneNumber(Integer.parseInt(formFields.get("new" + "phoneNumber" + i)));
            phone.setPhoneType(PhoneType.valueOf(formFields.get("new" + "phoneType" + i)));
            phone.setComment(formFields.get("new" + "phoneComment" + i));
            phone.setIdContact(contact.getIdContact());
            newContactPhones.add(phone);
            i++;
        }
        phoneGroups.put("new", newContactPhones);
        return phoneGroups;
    }

    private Map<String, List<ContactAttachment>> getContactAttachmentGroups(Contact contact) throws ControllerException {
        Map<String, List<ContactAttachment>> attachmentGroups = new HashMap<>();
        String actions[] = {"update", "delete"};
        int attachmentsInitialCount = Integer.parseInt(formFields.get("attachmentsInitialCount"));
        for (int i = 0; i < actions.length; i++) {
            int j = 0;
            List<ContactAttachment> attachments = new ArrayList<>();
            while (j < attachmentsInitialCount) {
                if (formFields.get(actions[i] + "fileName" + j) != null) {
                    ContactAttachment attachment = new ContactAttachment();
                    checkCorrectness(actions[i] + "fileName" + j);
                    attachment.setFileName(formFields.get(actions[i] + "fileName" + j));
                    attachment.setComment(formFields.get(actions[i] + "attachmentComment" + j));
                    attachment.setIdAttachment(Long.parseLong(formFields.get("idAttachment" + j)));
                    attachment.setIdContact(contact.getIdContact());
                    attachments.add(attachment);
                }
                j++;
            }
            attachmentGroups.put(actions[i], attachments);
        }
        int i = 0;
        List<ContactAttachment> newContactAttachments = new ArrayList<>();
        while (formFields.get("new" + "fileName" + i) != null) {
            ContactAttachment attachment = new ContactAttachment();
            checkCorrectness("new" + "fileName" + i);
            attachment.setFileName(formFields.get("new" + "fileName" + i));
            checkCorrectness("new" + "attachingDate" + i);
            attachment.setUploadDate(new Date(Long.parseLong(formFields.get("new" + "attachingDate" + i))));
            attachment.setComment(formFields.get("new" + "attachmentComment" + i));
            attachment.setIdContact(contact.getIdContact());
            newContactAttachments.add(attachment);
            i++;
        }
        attachmentGroups.put("new", newContactAttachments);
        return attachmentGroups;
    }
}
