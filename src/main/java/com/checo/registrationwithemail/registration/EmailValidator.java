package com.checo.registrationwithemail.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";

    @Override
    public boolean test(String s) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(s).matches();
    }
}
