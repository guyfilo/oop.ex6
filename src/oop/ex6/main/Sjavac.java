package oop.ex6.main;
import oop.ex6.GeneralException;
import oop.ex6.scope.MainScope;
import java.io.File;
import java.io.IOException;
//_______________________________________CLASS______________________________________________________________//
public class Sjavac {
    private final static String INVALID_ARGUMENT_AMOUNT_MSG = "The program spose to get only 1 argument";
    private final static int INVALID_INPUT_OUTPUT_NUMERIC_VALUE = 2;
    private final static int INVALID_SJAVA_FILE_NUMERIC_VALUE = 1;
    private final static int SUCCESS = 0;
    private final static int ARG_NUM = 1;

//*********************************** DECELERATIONS ********************************************************//

//************************************* FUNCTIONS **********************************************************//

    /**
     * the main method that runs the program
     * @param args - the program given arguments
     * @return a number symbolizes the file validation statues
     */
    public static void main(String[] args) {
        try{
            checkValidFile(args);
            MainScope mainScope = new MainScope(args[0]);
            mainScope.getGlobalMembers();
            mainScope.checkMethodsScopes();
            System.out.println(SUCCESS);
        } catch (IOException error) {
            System.out.println(INVALID_INPUT_OUTPUT_NUMERIC_VALUE);
            System.err.println(error.getMessage());
        } catch (GeneralException error) {
            System.out.println(INVALID_SJAVA_FILE_NUMERIC_VALUE);
            System.err.println(error.getMessage());
        }
    }

    /**
     * this method checks if the giving file excluding its content is valid
     * @param args - the program given args
     * @throws InvalidMainArguments - trows if the given file is invalid
     */
    private static void checkValidFile(String[] args) throws InvalidMainArguments {
        if (args.length != ARG_NUM){
            throw new InvalidMainArguments(INVALID_ARGUMENT_AMOUNT_MSG);
        }
        File file = new File(args[0]);

        if (!file.isFile() || !file.exists()){
            throw new InvalidMainArguments(INVALID_ARGUMENT_AMOUNT_MSG);
        }
    }

}
