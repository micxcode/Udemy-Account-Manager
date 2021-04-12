//package com.avenuecode.udemy.account.manager.email;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class EmailServiceImpl implements EmailService{
//
//    private static final String FROM = "michel.dassan@gmail.com";
//    private static final String FROM_NAME = "Michel";
//    private final JavaMailSender javaMailSender;
//
//    @Autowired
//    public EmailServiceImpl(final JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//    @Override
//    public void sendEmail(final String email) {
//        Runnable runnable = () -> {
//            try {
//                final String SEND_TO_REGEX = "%s<%s>";
//                MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage());
//                messageHelper.setTo(String.format(SEND_TO_REGEX, FROM_NAME, FROM));
//                messageHelper.setSubject("Udemy Account Manager - Test");
//                messageHelper.setFrom(String.format(SEND_TO_REGEX, FROM_NAME, FROM));
//                messageHelper.setText("Hello World" , false);
//
//                javaMailSender.send(messageHelper.getMimeMessage());
//            } catch (Exception e){
//                log.error("Something went wrong while sending e-mail to "+ email, e);
//                throw new RuntimeException(e);
//            }
//        };
//        waitAndRunThread(runnable);
//    }
//
//    private void waitAndRunThread(Runnable runnable) {
//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Thread thread = new Thread(runnable);
//        thread.start();
//    }
//}
