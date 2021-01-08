package oop.ex6.method;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Method {
    private String name;
    private ArrayList<Variable> arguments;
    private final ArrayList<String> methodLines = new ArrayList<>();


    public static HashMap<String, Pattern> typeRecognizerDict;

    // variables types patterns
    private final static Pattern INT_RECOGNIZER = Pattern.compile("^\\d++$");
    private final static Pattern DOUBLE_RECOGNIZER = Pattern.compile("^\\d+.{0,1}\\d*+$");
    private final static Pattern BOOLEAN_RECOGNIZER = Pattern.compile("^\\btrue\\b|\\bfalse\\b|\\d+.{0,1}\\d*$");
    private final static Pattern CHAR_RECOGNIZER = Pattern.compile("'{1}\\X{0,1}'$");
    private final static Pattern STRING_RECOGNIZER = Pattern.compile("^\"{1}\\d*\"$");

    // variable types:
    private final static String INT_TYPE = "int";
    private final static String DOUBLE_TYPE = "double";
    private final static String BOOLEAN_TYPE = "boolean";
    private final static String STRING_TYPE = "String";
    private final static String CHAR_TYPE = "char";

    static {
        typeRecognizerDict = new HashMap<>();
        typeRecognizerDict.put(INT_TYPE, INT_RECOGNIZER);
        typeRecognizerDict.put(DOUBLE_TYPE, DOUBLE_RECOGNIZER);
        typeRecognizerDict.put(BOOLEAN_TYPE, BOOLEAN_RECOGNIZER);
        typeRecognizerDict.put(STRING_TYPE, STRING_RECOGNIZER);
        typeRecognizerDict.put(CHAR_TYPE, CHAR_RECOGNIZER);

    }


    public Method(String name, ArrayList<Variable> arguments){
        this.name = name;
        this.arguments = arguments;
    }

    public void addLine(String line){
        methodLines.add(line);
    }

    public String getName(){
        return name;
    }

    public ArrayList<Variable> getArguments() {
        return arguments;
    }

    public boolean isEndWithReturn() throws MethodException {
        if (!LineParser.isReturnLine(methodLines.get(methodLines.size() - 2))){
            throw new MethodException("method does not contain return line");
        }
        return true;
    }

    public boolean checkMethodCall(String argsLine, Map<String, Variable> scopeVariables) throws MethodException {
        // 0 - separate args with comma
        String[] args = argsLine.split(",");
        // 1 - check if the args in the scopeVariables
        for (int i = 0; i < args.length; i++) {
            String argAsString = args[i].strip();
            String argValidType = this.arguments.get(i).getType(); // the type the arg should be - the arg is a string
            if (!scopeVariables.containsKey(argAsString)) {
                checkValidArgAsStringType(argValidType, argAsString);
            } else {
                Variable arg = scopeVariables.get(argAsString);
                arg.checkValidValue(argValidType);
            }
        }
        return true;
    }

    private void checkValidArgAsStringType(String argValidType, String arg) throws MethodException {
        Pattern typeRecognizer = typeRecognizerDict.get(argValidType);
        Matcher m = typeRecognizer.matcher(arg);
        if (!m.matches()) {
            throw new MethodException("Method arg has invalid type");
        }
    }
}
