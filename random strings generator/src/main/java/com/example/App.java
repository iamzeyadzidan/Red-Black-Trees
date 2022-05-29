package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ArrayList<String> randomStrings = new ArrayList<>();
        for(Integer index = 0; index < 10000; index++) {
            randomStrings.add(randomString());
        }
        stringHandle(randomStrings);
    }

    public static String randomString(){
        // create a string of uppercase and lowercase characters and numbers
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length =140;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        System.out.println("Random String is: " + randomString);
        return randomString;
    }

    public static void stringHandle(ArrayList<String> stringList) throws IOException {
        String stringListSTR = stringList.toString();
        stringListSTR = stringListSTR.replaceAll(", ", "\n");
        stringListSTR = stringListSTR.replace("[", "");
        stringListSTR = stringListSTR.replace("]", "");
        BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary6.txt"));
        writer.write(stringListSTR);
        writer.close();
    }
}
