package com.automationexercise.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomDataGenerator {

    public static String generateUniqueName() {
        return "Test" + TimeUtils.getSimpleTimestamp();
    }

    public static String generateUniqueInteger() {
        return new SimpleDateFormat("mmssSSS").format(new Date());
    }

    public static String generateUniqueEmail() {
        return "Test" + TimeUtils.getSimpleTimestamp() + "@gmail.com";
    }

    public static String generateStrongPassword() {
        return "Test" + "@#." + TimeUtils.getSimpleTimestamp();
    }


    public static int generateRandomNumber(int upperBound) { //0 >> upper-1  > 5
        return new Random().nextInt(upperBound) + 1;
    }

    public static Set<Integer> generateUniqueNumber(int numberOfItemsNeeded, int totalNumberOfItems) //5 >> 50
    {
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberOfItemsNeeded) { //11111 > 1 2 10 5 7
            int randomNumber = generateRandomNumber(totalNumberOfItems);
            generatedNumbers.add(randomNumber);
        }
        return generatedNumbers;
    }

}
