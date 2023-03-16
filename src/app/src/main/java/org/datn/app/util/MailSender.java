package org.datn.app.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {
    public static void sendCode(String email,String name) throws MessagingException {
        String emailHTML = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>Chào mừng đến với Aboo Shop</title>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div style=\"max-width: 600px; margin: 0 auto; font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5; color: #333;\">\n" +
                "\t\t<h1 style=\"font-size: 24px; margin-top: 30px; margin-bottom: 30px; text-align: center;\">Xác nhận đăng ký tài khoản</h1>\n" +
                "\t\t<p style=\"margin-bottom: 20px;\">Cảm ơn <b> " + name+ "</b> bạn đã đăng ký tài khoản tại Aboo Shop.</p>\n" +
                "\t\t<div style=\"text-align: center; margin-bottom: 40px;\">\n" +
                "\t\t</div>\n" +
                "\t\t<p style=\"margin-bottom: 20px;\">Nếu bạn không đăng ký tài khoản tại Aboo Shop, vui lòng bỏ qua email này.</p>\n" +
                "\t\t<p style=\"margin-bottom: 0;\">Trân trọng,</p>\n" +
                "\t\t<p style=\"margin-top: 0;\">Aboo Shop Team</p>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>\n";
        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage mailMessage;
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        mailMessage = new MimeMessage(getMailSession);
        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        mailMessage.setContent(emailHTML,"text/html;charset = UTF-8");
        mailMessage.setSubject("Xác nhận đăng ký thành công");
        Transport transport = getMailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", "lavantho0508@gmail.com", "zjipajndhlovrtvk");
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        transport.close();
    }
}
