package oop.ex6.main;

import java.io.File;

public class Sjavac {
    private final static String INVALID_ARGUMENT_AMOUNT_MSG = "The program spose to get only 1 argument";
    private final static int INVALID_INPUT_OUTPUT_NUMERIC_VALUE = 2;



    public static void main(String[] args) {
        try{
            checkValidFile(args);
        } catch (InvalidMainArguments error) {
            System.out.println(INVALID_INPUT_OUTPUT_NUMERIC_VALUE);
            System.err.println(error.getMessage());
        }

    }

    private static void checkValidFile(String[] args) throws InvalidMainArguments {
        if (args.length != 1){
            throw new InvalidMainArguments(INVALID_ARGUMENT_AMOUNT_MSG);
        }
        File file = new File(args[0]);

        if (!file.isFile() || !file.exists()){
            throw new InvalidMainArguments(INVALID_ARGUMENT_AMOUNT_MSG);
        }


    }


}
