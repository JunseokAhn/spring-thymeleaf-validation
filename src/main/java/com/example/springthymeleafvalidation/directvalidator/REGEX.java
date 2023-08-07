package com.example.springthymeleafvalidation.directvalidator;

public class REGEX {
    public static final String TEL_REGEX = "\\d{2,3}-\\d{3,4}-\\d{4}";
    public static final String ID_REGEX = "^[a-zA-Z0-9]*$";
    public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\\\.\" +\n" +
            "\"[a-zA-Z0-9_+&*-]+)*@\" +\n" +
            "\"(?:[a-zA-Z0-9-]+\\\\.)+[a-z\" +\n" +
            "\"A-Z]{2,7}$";

}
