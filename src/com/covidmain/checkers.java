package com.covidmain;

public class checkers {
    boolean emailChecker(String inputMail)
    {
        boolean isAtCon = false;
        boolean dotCon = false;
        boolean charNextToAtCon = false;
        if(inputMail.contains("@"))
            isAtCon = true;
        
        if(inputMail.charAt(0)!='.' && inputMail.charAt(inputMail.length()-1)!='.')
            dotCon = true;

        char charNextToAt = inputMail.charAt(inputMail.indexOf("@")+1);
        if((charNextToAt >= 'A' && charNextToAt <= 'Z') || (charNextToAt >= 'a' && charNextToAt <= 'z'))
            charNextToAtCon = true;
   
        if(isAtCon && dotCon && charNextToAtCon)
            return true;
        else
            return false;
    }
}
