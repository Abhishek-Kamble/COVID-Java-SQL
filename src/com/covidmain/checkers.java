package com.covidmain;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkers {
    boolean emailChecker(String inputMail)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        
        if (inputMail == null)
            return false;
        return pat.matcher(inputMail).matches();
    }

    boolean mobileChecker(long inputMobile)
    {
        
    }
}
