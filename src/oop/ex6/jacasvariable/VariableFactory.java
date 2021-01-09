package oop.ex6.jacasvariable;//import oop.ex6.jacasvariable.Variable;
//______________________________________PACKAGE_____________________________________________________________//


//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.scope.Scope;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private final static Pattern INT_RECOGNIZER = Pattern.compile("^-?\\d++$");
    private final static Pattern DOUBLE_RECOGNIZER = Pattern.compile("^-?\\d+.?\\d*+$");
    private final static Pattern BOOLEAN_RECOGNIZER = Pattern.compile
            ("^\\btrue\\b|\\bfalse\\b|-?\\d+.?\\d*$");
    private final static Pattern CHAR_RECOGNIZER = Pattern.compile("^'.?'$");
    private final static Pattern STRING_RECOGNIZER = Pattern.compile("^\".*?\"$");
    private final static Pattern NAME_RECOGNIZER = Pattern.compile("^[a-zA-Z]\\w*+|_\\w++$");
    //todo:add save word check

    // error messages:
    private final static String INVALID_VARIABLE_DECLARATION = "variable declaration is not valid";
    private final static String INVALID_ARGUMENTS_DECLARATION = "argument declaration is not valid";

    // type recognizer dict
    /**
     * a Map that its key is a variable type and its value is a pattern that recognize the type
     */
    public static HashMap<String, Pattern> typeRecognizerDict;
    static {
        typeRecognizerDict = new HashMap<>();
        typeRecognizerDict.put(INT_TYPE, INT_RECOGNIZER);
        typeRecognizerDict.put(DOUBLE_TYPE, DOUBLE_RECOGNIZER);
        typeRecognizerDict.put(BOOLEAN_TYPE, BOOLEAN_RECOGNIZER);
        typeRecognizerDict.put(STRING_TYPE, STRING_RECOGNIZER);
        typeRecognizerDict.put(CHAR_TYPE, CHAR_RECOGNIZER);

    }

    //*********************************** DECELERATIONS ****************************************************//
    Variable otherVar;
    //************************************ FUNCTIONS *******************************************************//

    /**
     * checks the validity of the arguments and create new variable from scratch
     *
     * @param name
     * @param type
     * @param value   if value null uninisielised
     * @param isFinal
     * @return
     */
    public static Variable createNewVar(String name, String type, String value, boolean isFinal,
                                        Scope varScope)
            throws VariableException {
        if (checkValidName(name) && checkValidValueType(type, value, isFinal)) {
            return new Variable(name, type, isFinal, typeRecognizerDict.get(type),
                    value != null, varScope);
        } else {
            throw new VariableException(INVALID_VARIABLE_DECLARATION);
        }
    }
//todo: desc

    public static Variable createNewVar(Variable otherVar, String name, String type, boolean isFinal,
                                        Scope varScope)
            throws VariableException {
        if (checkValidName(name) && type.equals(otherVar.getType()) && otherVar.isInitialised()) {
            return new Variable(name, type, isFinal, typeRecognizerDict.get(type)
                    , otherVar.isInitialised(), varScope);
        } else {
            throw new VariableException(INVALID_VARIABLE_DECLARATION);
        }
    }

//todo: desc
    public static Argument createNewArg(String name, String type, boolean isFinal) throws VariableException {
        if (checkValidName(name) && checkValidType(type)) {
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
    public static boolean checkValidValueType(String variableType, String variableValue, boolean isFinal) {
        if (variableValue == null){
            return !isFinal;
        }
        return variableValue.matches(typeRecognizerDict.get(variableType).toString());
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
    private static boolean checkValidName(String variableName) {
        Matcher matcher = NAME_RECOGNIZER.matcher(variableName);
        return matcher.matches();
    }

}