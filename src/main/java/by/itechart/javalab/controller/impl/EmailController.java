package by.itechart.javalab.controller.impl;

import by.itechart.javalab.controller.Controller;
import by.itechart.javalab.service.EmailAttributes;
import by.itechart.javalab.service.FindContactService;
import by.itechart.javalab.service.SendEmailService;
import by.itechart.javalab.service.ServiceException;
import by.itechart.javalab.util.MainUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STRawGroupDir;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class EmailController implements Controller {
    private static Logger log = LogManager.getLogger(EmailController.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doGet: ");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.debug("doPost: {}", request.getPathInfo());
        String splittedURL[] = request.getPathInfo().split("/");
        if (splittedURL.length > 2) {
            if ("send".equals(splittedURL[2]))
                sendEmail(request, response);
        } else {
            showPage(request, response);
        }
    }

    private void sendEmail(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        EmailAttributes emailAttributes = new EmailAttributes();
        emailAttributes.setEmailTitle(request.getParameter("subject"));
        emailAttributes.setRecipientEmails(request.getParameter("recipients"));
        emailAttributes.setFromEmail(MainUtils.getEmail());
        String template = request.getParameter("template");
        if (StringUtils.isNotEmpty(template) && !"NONE".equals(template)) {
            STRawGroupDir stGroup = new STRawGroupDir(request.getServletContext().getInitParameter("templatesDirectory"), '$', '$');
            List<String> templateNames = MainUtils.getTemplates();
            request.setAttribute("templates", templateNames);
            ST st = stGroup.getInstanceOf(request.getParameter("template") + "ForEmail");
            int i = 0;
            while (request.getParameter("chosenTemplate" + i) != null) {
                st = st.add("chosenTemplate" + i, request.getParameter("chosenTemplate" + i));
                i++;
            }
            emailAttributes.setEmailText(st.render());
        } else {
            emailAttributes.setEmailText(request.getParameter("textareaContent"));
        }
        try {
            log.debug("text: " + emailAttributes.getEmailText());
            log.debug("subject: " + emailAttributes.getEmailTitle());
            SendEmailService.sendEmail(emailAttributes);
            String path = request.getContextPath() + "/pages/contacts";
            response.sendRedirect(path);
        } catch (ServiceException | IOException e) {
            log.error(e);
        }
    }

    private void showPage(HttpServletRequest request, HttpServletResponse response) {
        String emailContactsId = request.getParameter("emailContactsId");
        try {
            if (emailContactsId != null) {
                String contactIds[] = emailContactsId.split(",");
                Long contactId[] = new Long[contactIds.length];
                for (int i = 0; i < contactIds.length; i++) {
                    contactId[i] = Long.parseLong(contactIds[i]);
                }
                List<String> emailsList = FindContactService.getEmails(contactId);
                StringBuilder emails = new StringBuilder();
                for (String email : emailsList) {
                    emails.append(email);
                    emails.append(",");
                }
                String contactEmails = emails.toString();
                contactEmails = contactEmails.substring(0, contactEmails.length() - 1);
                log.debug(contactEmails);
                request.setAttribute("recipients", contactEmails);
                request.setAttribute("emailContactsId", request.getParameter("emailContactsId"));
            }
            List<String> templateNames = MainUtils.getTemplates();
            request.setAttribute("templates", templateNames);
            if (request.getParameter("subject") != null) {
                request.setAttribute("subject", request.getParameter("subject"));
            }
            String template = request.getParameter("template");
            if (StringUtils.isNotEmpty(template) && !"NONE".equals(template)) {
                STRawGroupDir stGroup = new STRawGroupDir(request.getServletContext().getInitParameter("templatesDirectory"), '$', '$');
                ST st = stGroup.getInstanceOf(request.getParameter("template"));
                request.setAttribute("chosenTemplate", st.render());
                request.setAttribute("chosenValue", request.getParameter("template"));
            }
            request.getServletContext().getRequestDispatcher("/WEB-INF/pages/email.jsp").forward(request, response);
        } catch (ServiceException | ServletException | IOException | NumberFormatException e) {
            log.error(e);
        }
    }
}
