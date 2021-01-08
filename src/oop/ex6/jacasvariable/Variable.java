package oop.ex6.jacasvariable;//______________________________________PACKAGE_____________________________________________________________//


//______________________________________IMPORTS_____________________________________________________________//

import oop.ex6.method.MethodException;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//_______________________________________CLASS______________________________________________________________//

/**
 * this class creates a variable object
 */
public class Variable {

//*********************************** MAGIC NUMBERS ********************************************************//

    // variable types:
    private final static String INT_TYPE = "int";
    private final static String DOUBLE_TYPE = "double";
    private final static String BOOLEAN_TYPE = "boolean";
    private final static String STRING_TYPE = "String";
    private final static String CHAR_TYPE = "char";
    // variables types patterns
    private final static Pattern INT_RECOGNIZER = Pattern.compile("^\\d++$");
    private final static Pattern DOUBLE_RECOGNIZER = Pattern.compile("^\\d+.{0,1}\\d*+$");
    private final static Pattern BOOLEAN_RECOGNIZER = Pattern.compile("^\\btrue\\b|\\bfalse\\b|\\d+.{0,1}\\d*$");
    private final static Pattern CHAR_RECOGNIZER = Pattern.compile("'{1}\\X{0,1}'$");
    private final static Pattern STRING_RECOGNIZER = Pattern.compile("^\"{1}\\d*\"$");

    // variable valid name pattern
    private final static Pattern NAME_RECOGNIZER = Pattern.compile("^[a-zA-Z]\\w*+|_\\w++$");

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
    public void changeValue(String newValue) throws VariableException {
        if (this.isFinal){
            throw new VariableException("trying to change final var");
        }
        Matcher matcher = this.typeRecognizer.matcher(newValue);
        if (!matcher.matches()) {
            throw new VariableException("invalid type");
        }
        this.initialised = true;
    }


    /**
     * this method checks if the variable can get a new value
     * @param otherVar - a requested value for the variable
     * @return
     */
    public void changeValue(Variable otherVar) throws VariableException {
        if (this.isFinal){
            throw new VariableException("trying to change final var");
        }
        Matcher matcher = this.typeRecognizer.matcher(otherVar.type);
        if (!matcher.matches()) {
            throw new VariableException("invalid type");
        }
        this.initialised = true;
    }

    public boolean checkValidValue(String demandedValue){
        Pattern typeRecognizerPattern = typeRecognizerDict.get(demandedValue);
        Matcher m = typeRecognizerPattern.matcher(this.type);
        if (!m.matches()){
            return false;
        }
        return true;
    }

//todo: 2 versions - in one gets param and in the other string



}
