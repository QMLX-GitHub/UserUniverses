package com.qmlx.usercenter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPasswordUtils {
    //密码长度8到20位，包含字母数字，区分大小写
    private static String regEx1 = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,20})$";

    public static boolean checkPassword(String password){
        Pattern Password_Pattern = Pattern.compile(regEx1);
        Matcher matcher = Password_Pattern.matcher(password);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
