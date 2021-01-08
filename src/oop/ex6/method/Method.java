package oop.ex6.method;

import oop.ex6.jacasvariable.Argument;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.jacasvariable.VariableException;
import oop.ex6.main.LineParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Method {
    private String name;
    private ArrayList<Argument> arguments;
    private final ArrayList<String> methodLines = new ArrayList<>();


    public static HashMap<String, Pattern> typeRecognizerDict;

    // more regexes:
    private final static Pattern WORD_NO_SPACES_IN_SIDES_PATTERN = Pattern.compile("^\\s*+(\\S++)\\s*+$");
    private final static String EMPTY_ARG = "^\\s*+$";


    public Method(String name, ArrayList<Argument> arguments){
        this.name = name;
        this.arguments = arguments;
    }

    public void addLine(String line){
        methodLines.add(line);
    }

    public String getName(){
        return name;
    }

    public boolean isEndWithReturn() throws MethodException {
        if (!LineParser.isReturnLine(methodLines.get(methodLines.size() - 2))){
            throw new MethodException("method does not contain return line");
        }
        return true;
    }

    public boolean checkMethodCall(String argsLine, Map<String, Variable> scopeVariables) //todo: change argsLine to splited arguments array
            throws MethodException, VariableException {
        // 0 - separate args with comma
        String[] args = argsLine.split(",");
        if (args.length == 1 && args[0].matches(EMPTY_ARG) && arguments.size() == 0){
            return true;
        }
        if (args.length != arguments.size()){
            throw new MethodException("Invalid amount of args");
        }
        for (int i = 0; i < args.length; i++) {
            Matcher arg = WORD_NO_SPACES_IN_SIDES_PATTERN.matcher(args[i]);
            if (!arg.matches()) {
                throw new MethodException("Invalid method arg");
            }
            String argAsString = arg.group(1);
            Argument argDemands = this.arguments.get(i); // the type the arg should be - the arg is a string
            if (scopeVariables.containsKey(argAsString)) {
                argDemands.checkValidValue(scopeVariables.get(argAsString));
            } else {
                argDemands.checkValidValue(argAsString);
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
