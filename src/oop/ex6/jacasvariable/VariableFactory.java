package oop.ex6.jacasvariable;//import oop.ex6.jacasvariable.Variable;
//______________________________________PACKAGE_____________________________________________________________//


//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.scope.Scope;

import java.util.HashMap;

//_______________________________________CLASS______________________________________________________________//

/**
 * this class creates variables
 */
public class VariableFactory {

    //*********************************** MAGIC NUMBERS ****************************************************//
    // variable types:
    private final static String INT_TYPE = "int";
    private final static String DOUBLE_TYPE = "double";
    private final static String BOOLEAN_TYPE = "boolean";
    private final static String STRING_TYPE = "String";
    private final static String CHAR_TYPE = "char";

    // variables types patterns
    private final static String INT_RECOGNIZER = "^-?\\d++$";
    private final static String DOUBLE_RECOGNIZER = "^-?\\d+.?\\d*+$";
    private final static String BOOLEAN_RECOGNIZER = "^\\btrue\\b|\\bfalse\\b|-?\\d+.?\\d*$";
    private final static String CHAR_RECOGNIZER = "^'.?'$";
    private final static String STRING_RECOGNIZER = "^\".*?\"$";

    // variable valid name pattern
    private final static String NAME_RECOGNIZER = "^[a-zA-Z]\\w*+|_\\w++$";
    //todo:add save word check

    // error messages:
    private final static String INVALID_VARIABLE_DECLARATION = "variable declaration is not valid";
    private final static String INVALID_ARGUMENTS_DECLARATION = "argument declaration is not valid";
    private final static String NOT_INITIALIZE_CANT_BE_FINAL = "try to declare uninitialised final variable";
    private final static String VARIABLE_INVALID_TYPE = "try to declare variable with invalid type value";
    private final static String INVALID_VARIABLE_NAME_PREFIX = "invalid variable name: ";
    private final static String INVALID_TYPE = "invalid type";
    private final static String INITIALIZE_WITH_NOT_INITIALIZE_VARIABLE = "try to initialise with " +
            "uninitialised variable";
    private final static String WRONG_INITIALIZED_VALUE = "try to initialise new variable with wrong type " +
            "other variable";


    // type recognizer dict
    private static final HashMap<String, String> typeRecognizerDict;

    /**
     * a Map that its key is a variable type and its value is a pattern that recognize the type
     */
    static {
        typeRecognizerDict = new HashMap<>();
        typeRecognizerDict.put(INT_TYPE, INT_RECOGNIZER);
        typeRecognizerDict.put(DOUBLE_TYPE, DOUBLE_RECOGNIZER);
        typeRecognizerDict.put(BOOLEAN_TYPE, BOOLEAN_RECOGNIZER);
        typeRecognizerDict.put(STRING_TYPE, STRING_RECOGNIZER);
        typeRecognizerDict.put(CHAR_TYPE, CHAR_RECOGNIZER);
    }

    protected static final HashMap<String, String> VALID_TYPES_INITIALIZE;

    static {
        VALID_TYPES_INITIALIZE = new HashMap<>();
        VALID_TYPES_INITIALIZE.put(INT_TYPE, INT_TYPE);
        VALID_TYPES_INITIALIZE.put(DOUBLE_TYPE, INT_TYPE + "|" + DOUBLE_TYPE);
        VALID_TYPES_INITIALIZE.put(BOOLEAN_TYPE, INT_TYPE + "|" + DOUBLE_TYPE + "|" + BOOLEAN_TYPE);
        VALID_TYPES_INITIALIZE.put(STRING_TYPE, STRING_TYPE);
        VALID_TYPES_INITIALIZE.put(CHAR_TYPE, CHAR_TYPE);
    }
    //************************************ FUNCTIONS *******************************************************//

    /**
     * checks the validity of the arguments and create new variable from scratch
     * @param name - the argument name
     * @param type - the argument type
     * @param value - the argument value
     * @param isFinal - true if the argument if final, false otherwise
     * @return the new crated argument (as a variable)
     */
    public static Variable createNewVar(String name, String type, String value, boolean isFinal,
                                        Scope varScope)
            throws VariableException {
        checkValidName(name);
        checkValidValueType(type, value, isFinal);
        return new Variable(name, type, isFinal, typeRecognizerDict.get(type),
                value != null, varScope);
    }

    /**
     * checks the validity of the arguments and create new variable from another variable
     * @param otherVar - another variable
     * @param name - the argument name
     * @param type - the argument type
     * @param isFinal - true if the argument if final, false otherwise
     * @param varScope - the current scope
     * @return the new crated argument (as a variable)
     * @throws VariableException - trows if the arg is not valid
     */
    public static Variable createNewVar
            (Variable otherVar, String name, String type, boolean isFinal, Scope varScope)
            throws VariableException {
        checkValidName(name);
        checkValidOtherVar(type, otherVar);
        return new Variable(name, type, isFinal, typeRecognizerDict.get(type)
                , otherVar.isInitialised(), varScope);
    }

    /**
     * this method creates new argument
     * @param name - arg name
     * @param type - arg type
     * @param isFinal - true if the arg if final, false - otherwise
     * @return the new created argument
     * @throws VariableException - trows if the arg name is not valid
     */
    public static Argument createNewArg(String name, String type, boolean isFinal) throws VariableException {
        if (checkValidType(type)) {
            checkValidName(name);
            return new Argument(name, type, isFinal, typeRecognizerDict.get(type));
        } else {
            throw new VariableException(INVALID_ARGUMENTS_DECLARATION);
        }
    }

    /**
     * check is a given value is from the type of the given type
     * @param variableType - a given type
     * @param variableValue - a given value
     * @param isFinal - true if the variable is final, false - otherwise
     * @return if the value is from the given type, false otherwise
     */
    private static void checkValidValueType(String variableType, String variableValue, boolean isFinal)
            throws VariableException {
        if (variableValue == null && isFinal){
            throw new VariableException(NOT_INITIALIZE_CANT_BE_FINAL);
        }
        if (variableValue != null && !variableValue.matches(typeRecognizerDict.get(variableType))){
            throw new VariableException(VARIABLE_INVALID_TYPE);
        }
    }

    /**
     * this method checks if a type is a valid type
     * @param type - a given type
     * @return true - if the type is valid, false - otherwise
     * @throws VariableException - trows if the type is not valid
     */
    public static boolean checkValidType(String type) throws VariableException {
        return typeRecognizerDict.containsKey(type);
    }


    /**
     * this method checks if a variable name is valid
     * @param variableName - a variable name
     * @return true - if the variable name is valid, false - otherwise
     */
    private static void checkValidName(String variableName) throws VariableException {
        if (!variableName.matches(NAME_RECOGNIZER)) {
            throw new VariableException(INVALID_VARIABLE_NAME_PREFIX + variableName);
        }
    }

    /**
     * checks if the given variable is from the demanded type
     * @param type - a given type
     * @param otherVar - another variable
     * @throws VariableException - trows if the type is not valid
     */
    public static void checkValidOtherVar(String type ,Variable otherVar) throws VariableException {
        if (!otherVar.isInitialised()){
            throw new VariableException(INITIALIZE_WITH_NOT_INITIALIZE_VARIABLE);
        }
        if (!VALID_TYPES_INITIALIZE.containsKey(type)){
            throw new VariableException(INVALID_TYPE);
        }
        if (!otherVar.getType().matches(VALID_TYPES_INITIALIZE.get(type))){
            throw new VariableException(WRONG_INITIALIZED_VALUE);
        }
    }


}