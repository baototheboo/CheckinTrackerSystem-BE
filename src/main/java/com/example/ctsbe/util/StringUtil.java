package com.example.ctsbe.util;

import com.example.ctsbe.constant.ApplicationConstant;

import java.util.Random;

public class StringUtil {
    public String cutStringRole(String role) {
        String afterCut = role.substring(5, role.length());
        String firstLetter = afterCut.substring(0, 1);
        String otherLetters = afterCut.substring(1, afterCut.length()).toLowerCase();
        String res = firstLetter + otherLetters;
        return res;
    }

    public String randomString() {
        int length = 6;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#&()–[{}]:;',?/*~$^+=<>";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        boolean check = false;
        while(check == false){
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(allowedChars.length());
                char randomChar = allowedChars.charAt(index);
                sb.append(randomChar);
            }
            if(sb.toString().matches(ApplicationConstant.PASSWORD_REGEX)){
                check = true;
            }else{
                check = false;
                sb.setLength(0);
            }
        }
        return sb.toString();
    }

    public String convertNumberToString(int num) {
        String convert = "";
        if (num < 10 && num > 0) {
            convert = "0" + String.valueOf(num);
        } else {
            convert = String.valueOf(num);
        }
        return convert;
    }
}
