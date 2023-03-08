package com.example.ctsbe.util;

public class StringUtil {
    public String cutStringRole(String role){
        String afterCut = role.substring(5,role.length());
        return afterCut;
    }
}
