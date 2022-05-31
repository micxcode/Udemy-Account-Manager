//package com.avenuecode.udemy.account.manager.email;
//
//TODO: Keep investigating
//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import java.io.File;
//import java.io.IOException;
//import java.util.Date;
//import java.util.Properties;
//
//public class EmailSender {
//    private static final String SMTP_SERVER = "smtp.gmail.com ";
//    private static final String USERNAME = "michel.dassan@gmail.com";
//    private static final String PASSWORD = "your_password_here";
//
//    private static final String EMAIL_FROM = "michel.dassan@gmail.com";
//    private static final String EMAIL_TO = "michel.dassan@gmail.com";
//    private static final String EMAIL_TO_CC = "";
//
//    private static final String EMAIL_SUBJECT = "Test Send Email via SMTP";
//    private static final String EMAIL_TEXT = "Hello Java Mail \n ABC123";
//    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//
//
//
//    public void ok(){
//        Properties prop = System.getProperties();
////        props.put("mail.transport.protocol", "smtp");
////        props.put("mail.smtp.starttls.enable", "true");
////        props.put("mail.smtp.socketFactory.class",
////                "javax.net.ssl.SSLSocketFactory");
////        props.put("mail.smtp.host", SMTP_SERVER);
////        props.put("mail.smtp.socketFactory.port", "465");
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.port", "465");
//
////        prop.put("mail.smtp.auth", true);
////        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "localhost");
////        prop.put("mail.smtp.port", "25");
////        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
//
//        Session session = Session.getInstance(prop,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(USERNAME, PASSWORD);
//                    }
//                });
//
//        session.setDebug(true);
//        Message msg = new MimeMessage(session);
//
//        try {
//
//            // from
////            msg.setFrom(new InternetAddress(EMAIL_FROM));
//
//            // to
//            msg.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(EMAIL_TO, false));
//
//            // cc
//            msg.setRecipients(Message.RecipientType.CC,
//                    InternetAddress.parse(EMAIL_TO_CC, false));
//
//            // subject
//            msg.setSubject(EMAIL_SUBJECT);
//
//            // content
//            msg.setText(EMAIL_TEXT);
//
//            msg.setSentDate(new Date());
//
//            Transport.send(msg);
//
//            // Get SMTPTransport
////            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
////
////            // connect
////            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
////
////            // send
////            t.sendMessage(msg, msg.getAllRecipients());
//
////            System.out.println("Response: " + t.getLastServerResponse());
//
////            t.close();
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
////
////    private static MimeMessage createEmail(String to, String cc, String from, String subject, String bodyText) throws MessagingException {
////        Properties props = new Properties();
////        Session session = Session.getDefaultInstance(props, null);
////
////        MimeMessage email = new MimeMessage(session);
////        InternetAddress tAddress = new InternetAddress(to);
////        InternetAddress cAddress = cc.isEmpty() ? null : new InternetAddress(cc);
////        InternetAddress fAddress = new InternetAddress(from);
////
////        email.setFrom(fAddress);
////        if (cAddress != null) {
////            email.addRecipient(javax.mail.Message.RecipientType.CC, cAddress);
////        }
////        email.addRecipient(javax.mail.Message.RecipientType.TO, tAddress);
////        email.setSubject(subject);
////        email.setText(bodyText);
////        return email;
////    }
////
////    private static Message createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
////        ByteArrayOutputStream baos = new ByteArrayOutputStream();
////        email.writeTo(baos);
////        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
////        Message message = Message;
////        message.setRaw(encodedEmail);
////        return message;
////    }
////
////    public static void Send(Gmail service, String recipientEmail, String ccEmail, String fromEmail, String title, String message) throws IOException, MessagingException {
////        Message m = createMessageWithEmail(createEmail(recipientEmail, ccEmail, fromEmail, title, message));
////        service.users().messages().send("me", m).execute();
////    }
//
//    /**
//     * Create a MimeMessage using the parameters provided.
//     *
//     * @param to email address of the receiver
//     * @param from email address of the sender, the mailbox account
//     * @param subject subject of the email
//     * @param bodyText body text of the email
//     * @return the MimeMessage to be used to send email
//     * @throws MessagingException
//     */
//    public static MimeMessage createEmail(String to,
//                                          String from,
//                                          String subject,
//                                          String bodyText)
//            throws MessagingException {
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        MimeMessage email = new MimeMessage(session);
//
//        email.setFrom(new InternetAddress(from));
//        email.addRecipient(javax.mail.Message.RecipientType.TO,
//                new InternetAddress(to));
//        email.setSubject(subject);
//        email.setText(bodyText);
//        return email;
//    }
//
//    /**
//     * Create a MimeMessage using the parameters provided.
//     *
//     * @param to Email address of the receiver.
//     * @param from Email address of the sender, the mailbox account.
//     * @param subject Subject of the email.
//     * @param bodyText Body text of the email.
//     * @param file Path to the file to be attached.
//     * @return MimeMessage to be used to send email.
//     * @throws MessagingException
//     */
//    public static MimeMessage createEmailWithAttachment(String to,
//                                                        String from,
//                                                        String subject,
//                                                        String bodyText,
//                                                        File file)
//            throws MessagingException, IOException {
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        MimeMessage email = new MimeMessage(session);
//
//        email.setFrom(new InternetAddress(from));
//        email.addRecipient(javax.mail.Message.RecipientType.TO,
//                new InternetAddress(to));
//        email.setSubject(subject);
//
//        MimeBodyPart mimeBodyPart = new MimeBodyPart();
//        mimeBodyPart.setContent(bodyText, "text/plain");
//
//        Multipart multipart = new MimeMultipart();
//        multipart.addBodyPart(mimeBodyPart);
//
//        mimeBodyPart = new MimeBodyPart();
//        DataSource source = new FileDataSource(file);
//
//        mimeBodyPart.setDataHandler(new DataHandler(source));
//        mimeBodyPart.setFileName(file.getName());
//
//        multipart.addBodyPart(mimeBodyPart);
//        email.setContent(multipart);
//
//        return email;
//    }
//
//    /**
//     * Send an email from the user's mailbox to its recipient.
//     *
//     * @param service Authorized Gmail API instance.
//     * @param userId User's email address. The special value "me"
//     * can be used to indicate the authenticated user.
//     * @param emailContent Email to be sent.
//     * @return The sent message
//     * @throws MessagingException
//     * @throws IOException
//     */
//    public static Message sendMessage(Gmail service,
//                                      String userId,
//                                      MimeMessage emailContent)
//            throws MessagingException, IOException {
//        Message message = emailContent;
//        message = service.users().messages().send(userId, message).execute();
//
////        System.out.println("Message id: " + message.getId());
////        System.out.println(message.toPrettyString());
//        return message;
//    }
//
//}
