package com.example.user_management.registration;

import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return s.contains("@");
    }
}
