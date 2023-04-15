package com.example.ctsbe.constant;

import java.time.LocalTime;

public class ApplicationConstant {
    public static final LocalTime MORNING_START = LocalTime.of(8, 0, 0);
    public static final LocalTime MORNING_END = LocalTime.of(11, 30, 0);
    public static final LocalTime AFTERNOON_START = LocalTime.of(13, 0, 0);
    public static final LocalTime AFTERNOON_END = LocalTime.of(17, 30, 0);
    public static final Double WORKING_HOURS_DEFAULT = 8.0;
    public static final Double WORKING_HOURS_MORNING = 3.5;
    public static final Double WORKING_HOURS_AFTERNOON = 4.5;
    public static final Double WORKING_HOURS_ABSENT = 0.0;


    public static final String VN_TIME_ZONE = "Asia/Ho_Chi_Minh";

    /*
    ^                                   # start of line
  (?=.*[0-9])                       # positive lookahead, digit [0-9]
  (?=.*[a-z])                       # positive lookahead, one lowercase character [a-z]
  (?=.*[A-Z])                       # positive lookahead, one uppercase character [A-Z]
  (?=.*[!@#&()–[{}]:;',?/*~$^+=<>]) # positive lookahead, one of the special character in this [..]
  .                                 # matches anything
  {8,20}                            # length at least 8 characters and maximum of 20 characters
  $                                 # end of line
     */
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$";
}
