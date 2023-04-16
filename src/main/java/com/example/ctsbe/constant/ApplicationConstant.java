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
   ^                                # start of line
  (?=.*[0-9])                       # positive lookahead, digit [0-9]
  (?=.*[a-z])                       # positive lookahead, one lowercase character [a-z]
  (?=.*[A-Z])                       # positive lookahead, one uppercase character [A-Z]
  (?=.*[!@#&()–[{}]:;',?/*~$^+=<>]) # positive lookahead, one of the special character in this [..]
  .                                 # matches anything
  {6,20}                            # length at least 6 characters and maximum of 20 characters
  $                                 # end of line
     */
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$";
//    public static final String IMAGE_PATH = "D:/images";
    public static final String IMAGE_PATH = "D:/Capstone Project/CheckinTrackerSystem/CTS_FE/fe_check_in_checker/src/assets/Images";
    public static final String IMAGE_THRESHOLD = "80";

    public static final String VGG_URL_LOCAL = "http://localhost:5001";
    public static final String VGG_URL_AWS = "http://13.229.226.24";

}
