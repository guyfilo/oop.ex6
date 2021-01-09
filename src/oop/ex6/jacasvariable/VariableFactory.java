package oop.ex6.jacasvariable;//import oop.ex6.jacasvariable.Variable;
//______________________________________PACKAGE_____________________________________________________________//


//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.scope.Scope;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//_______________________________________CLASS______________________________________________________________//
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
    private final static Pattern BOOLEAN_RECOGNIZER = Pattern.compile("^\\btrue\\b|\\bfalse\\b|-?\\d+.?\\d*$");
    private final static Pattern CHAR_RECOGNIZER = Pattern.compile("^'.?'$");
    private final static Pattern STRING_RECOGNIZER = Pattern.compile("^\".*?\"$");

    // variable valid name pattern
    private final static Pattern NAME_RECOGNIZER = Pattern.compile("^[a-zA-Z]\\w*+|_\\w++$");
    //todo:add save word check

    // type recognizer dict
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
    public static Variable createNewVar(String name, String type, String value, boolean isFinal, Scope varScope)
            throws VariableException {
        if (checkValidName(name) && checkValidValueType(type, value)) {
            return new Variable(name, type, isFinal, typeRecognizerDict.get(type),
                    value != null, varScope);
        } else {
            throw new VariableException("variable declaration is not valid");
        }
    }

    public static Variable createNewVar(Variable otherVar, String name, String type, boolean isFinal, Scope varScope)
            throws VariableException {
        if (checkValidName(name) && type.equals(otherVar.getType()) && otherVar.isInitialised()) {
            return new Variable(name, type, isFinal, typeRecognizerDict.get(type)
                    , otherVar.isInitialised(), varScope);
        } else {
            throw new VariableException("variable declaration is not valid");
        }
    }

    public static Argument createNewArg(String name, String type, boolean isFinal) throws VariableException {
        if (checkValidName(name)) {
            return new Argument(name, type, isFinal, typeRecognizerDict.get(type));
        } else {
            throw new VariableException("argument declaration is not valid");
        }
    }


    public static boolean checkValidValueType(String variableType, String variableValue) {
        if (variableValue == null){
            return true;
        }
        return variableValue.matches(typeRecognizerDict.get(variableType).toString());
    }

    public static boolean checkValidType(String type) throws VariableException {
        return typeRecognizerDict.containsKey(type);
    }

    private static boolean checkValidName(String variableName) {
        Matcher matcher = NAME_RECOGNIZER.matcher(variableName);
        return matcher.matches();
    }

}