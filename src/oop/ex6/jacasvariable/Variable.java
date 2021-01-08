package oop.ex6.jacasvariable;//______________________________________PACKAGE_____________________________________________________________//


//______________________________________IMPORTS_____________________________________________________________//

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//_______________________________________CLASS______________________________________________________________//

/**
 * this class creates a variable object
 */
public class Variable {

//*********************************** MAGIC NUMBERS ********************************************************//



    //*********************************** DECELERATIONS ********************************************************//
    private boolean isFinal;
    private String type;
    private String name;
    private boolean initialised;
    private Pattern typeRecognizer;




//************************************* FUNCTIONS **********************************************************//


    public Variable(String name, String type, boolean isFinal, Pattern typeRecognizer, boolean initialised) {
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
        this.typeRecognizer = typeRecognizer;
        this.initialised = initialised;
    }

    /**
     * this method return the variable name
     * @return - the variable name
     */
    public String getName(){
        return this.name;
    }

    /**
     * this method return the variable type
     * @return - the variable type
     */
    public String getType(){
        return this.type;
    }

    /**
     * this method return if the variable is final
     * @return - true - if its final, false - otherwise
     */
    public boolean isFinal(){
        return this.isFinal;
    }


    public Pattern getTypeRecognizer(){
        return this.typeRecognizer;
    }

    public boolean isInitialised() {
        return initialised;
    }

    /**
     * this method checks if the variable can get a new value
     * @param newValue - a requested value for the variable
     * @return
     */
    public void changeValue(String newValue){
        if (this.isFinal){
            //todo: trow exception - trying to change final var
        }
        Matcher matcher = this.typeRecognizer.matcher(newValue);
        if (!matcher.matches()) {
            //todo: trow exception - invaldi type
        }
        this.initialised = true;
    }


    /**
     * this method checks if the variable can get a new value
     * @param otherVar - a requested value for the variable
     * @return
     */
    public void changeValue(Variable otherVar){
        if (this.isFinal){
            //todo: trow exception - trying to change final var
        }
        Matcher matcher = this.typeRecognizer.matcher(otherVar.type);
        if (!matcher.matches()) {
            //todo: trow exception - invaldi type
        }
        this.initialised = true;
    }

//    /**
//     * this method checks if the newValue type is equal to the variable type
//     * @param newValue - the new value
//     * @return - true if the types are equal, false - otherwise
//     */
//    private boolean checkValidNewValue(String newValue){
//
//        //todo: check in dict by value
//
//        if (this.type.equals(INT_TYPE)){
//            Matcher matcher = INT_RECOGNIZER.matcher(newValue);
//            return matcher.matches();
//        }
//        if (this.type.equals(DOUBLE_TYPE)){
//            Matcher matcher = DOUBLE_RECOGNIZER.matcher(newValue);
//            return matcher.matches();
//        }
//        if (this.type.equals(BOOLEAN_TYPE)){
//            Matcher matcher = BOOLEAN_RECOGNIZER.matcher(newValue);
//            return matcher.matches();
//        }
//        if (this.type.equals(STRING_TYPE)){
//            Matcher matcher = STRING_RECOGNIZER.matcher(newValue);
//            return matcher.matches();
//        }
//        if (this.type.equals(CHAR_TYPE)){
//            Matcher matcher = CHAR_RECOGNIZER.matcher(newValue);
//            return matcher.matches();
//        }
//        return false;
//    }
//
//



}
