package com.automationexercise.utils;

import com.github.javafaker.Faker;

public class FakerUtils {
    private FakerUtils() {
        super();
    }

    public static String generateName() {
        return new Faker().name().firstName().replace("'", "''");
    }

    public static String generateCompany() {
        return new Faker().company().name().replace("'", "''");
    }

    public static String generateAddress() {
        return new Faker().address().fullAddress().replace("'", "''");
    }

    public static String generateCity() {
        return new Faker().address().city().replace("'", "''");
    }

    public static String generateZipCode() {
        return new Faker().address().zipCode();
    }

    public static String generatePhoneNumber() {
        return new Faker().phoneNumber().phoneNumber();
    }

    public static String generateEmail() {
        return new Faker().internet().emailAddress();
    }

    public static String generatePassword() {
        return new Faker().internet().password();
    }

    public static String generateText() {
        return new Faker().lorem().sentence();
    }

    public static String generateText(int length) {
        return new Faker().lorem().characters(length);
    }

    public static String generateText(int minLength, int maxLength) {
        return new Faker().lorem().characters(minLength, maxLength);
    }


}
