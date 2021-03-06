//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6;
//______________________________________IMPORTS_____________________________________________________________//

//_______________________________________CLASS______________________________________________________________//

/**
 * This class creates a general exception that has prefix to all the error messages
 */
public class GeneralException extends Exception{

//*********************************** MAGIC NUMBERS ********************************************************//
    private final static String GENERAL_MSG_PREFIX = "ERROR: ";

//*********************************** DECELERATIONS ********************************************************//
    private static final long serialVersionID = 1L;

//************************************* FUNCTIONS **********************************************************//

    /**
     * the class constructor
     * @param msg - prefix to all the error messages
     */
    public GeneralException(String msg){
        super(GENERAL_MSG_PREFIX + msg);
    }
}
