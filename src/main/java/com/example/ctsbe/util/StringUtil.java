package com.example.ctsbe.util;

import java.util.Random;

public class StringUtil {
    public String cutStringRole(String role){
        String afterCut = role.substring(5,role.length());
        return afterCut;
    }
    public String randomString(){
        int length = 6;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
