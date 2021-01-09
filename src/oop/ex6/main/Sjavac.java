package oop.ex6.main;
import oop.ex6.GeneralException;
import oop.ex6.scope.MainScope;
import java.io.File;
import java.io.IOException;

public class Sjavac {
    private final static String INVALID_ARGUMENT_AMOUNT_MSG = "The program spose to get only 1 argument";
    private final static int INVALID_INPUT_OUTPUT_NUMERIC_VALUE = 2;
    private final static int INVALID_SJAVA_FILE_NUMERIC_VALUE =1;



    public static void main(String[] args) {
        try{
            checkValidFile(args);
            MainScope mainScope = new MainScope(args[0]);
            mainScope.getGlobalMembers();
            mainScope.checkMethodsScopes();
        } catch (IOException error) {
            System.out.println(INVALID_INPUT_OUTPUT_NUMERIC_VALUE);
            System.err.println(error.getMessage());
        } catch (GeneralException error) {
            System.out.println(INVALID_SJAVA_FILE_NUMERIC_VALUE);
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
