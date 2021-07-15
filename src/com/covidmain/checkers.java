package com.covidmain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkers {
    static boolean emailChecker(String inputMail) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        if (inputMail == null)
            return false;
        return pat.matcher(inputMail).matches();
    }

    static boolean mobileChecker(String inputMobile)
    {
        Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(inputMobile);
        return (m.find() && m.group().equals(inputMobile));
    }
}
