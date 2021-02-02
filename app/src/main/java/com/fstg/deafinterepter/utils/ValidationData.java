package com.fstg.deafinterepter.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class ValidationData {
    public static boolean phoneValidation(String phone) {
        if (phone.isEmpty()) {
            return true;
        }
        return !Pattern.compile("^((?:[+?0?0?966]+)(?:\\s?\\d{2})(?:\\s?\\d{7}))$").matcher(phone).matches();
    }

    public static boolean fieldValidation(String field) {
        if (TextUtils.isEmpty(field) || field.length() < 6) {
            return false;
        }
        return true;
    }

    public static boolean validationSpinnerExpp(String selectedItem) {
        return !selectedItem.equalsIgnoreCase("select the years");
    }

    public static boolean validationSpinnerNatio(String selectedItem) {
        return !selectedItem.equalsIgnoreCase("select your country");
    }

    public static boolean validationEmail(String email) {
        if (TextUtils.isEmpty(email) || email.length() < 10 || !email.contains("@")) {
            return false;
        }
        return true;
    }
}
