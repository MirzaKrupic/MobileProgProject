package com.example.itmproject;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidationHelper {
    public boolean isValidEmail(String string){
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public boolean isValidPassword(String string){
        return string.length() > 5;
    }

    public boolean isNumeric(String string){
        return TextUtils.isDigitsOnly(string);
    }
}
