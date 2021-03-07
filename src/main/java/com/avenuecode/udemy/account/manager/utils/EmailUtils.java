package com.avenuecode.udemy.account.manager.utils;

public class EmailUtils {

    private EmailUtils() {
    }

    public static String maskEmail(String originalEmail) {
        if(originalEmail == null || originalEmail.isEmpty() || originalEmail.indexOf('@') < 1) {
            return originalEmail;
        }

        int at = originalEmail.indexOf('@');
        String namePart = originalEmail.substring(0, at);

        String maskedEmail;
        if(namePart.length() > 4) {
            maskedEmail = String.format("%c..%c", namePart.charAt(0), namePart.charAt(namePart.length()-1));
        }
        else if(namePart.length() > 2) {
            maskedEmail = String.format("%c..", namePart.charAt(0));
        }
        else {
            maskedEmail = "..";
        }

        String domainPart = originalEmail.substring(at+1);

        maskedEmail = String.format("%s@%s",maskedEmail, domainPart);

        return maskedEmail;
    }
}
