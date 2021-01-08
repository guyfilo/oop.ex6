package oop.ex6.main;

import oop.ex6.jacasvariable.Variable;

import java.util.Map;

public class LineParser {
    private final static String RETURN_LINE_REGEX = "^\\s*+return\\s*+;\\s*+$";
    public void statementLineParser(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public void scopeBeginsParser(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public void lineVerification(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public static boolean isReturnLine(String line){
        return line.matches(RETURN_LINE_REGEX);
    }



}
