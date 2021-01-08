package oop.ex6.main;

import oop.ex6.jacasvariable.Variable;

import java.util.Map;

public class LineParser {
    private final static String RETURN_LINE_REGEX = "^\\s*+return\\s*+;\\s*+$";
    private final static String END_SCOPE_REGEX = "^[^}]*+}\\s*+$";
    private final static String DECLERATION_LINE = "^.*+;{1}$";
    private final static String NEW_SCOPE_REGEX = "^[^{]*+\\{\\s*+$";








    public void statementLineParser(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public void scopeBeginsParser(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public void lineVerification(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public static boolean isReturnLine(String line){
        return line.matches(RETURN_LINE_REGEX);
    }

    public static boolean isCloseScopeLine(String line){
        return line.matches(END_SCOPE_REGEX);
    }

    public static boolean isDeclerationLine(String line){
        return line.matches(DECLERATION_LINE);
    }

    public static boolean isNewScopeLine(String line){
        return line.matches(NEW_SCOPE_REGEX);
    }

}
