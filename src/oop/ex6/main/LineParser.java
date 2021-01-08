package oop.ex6.main;

import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.jacasvariable.VariableFactory;
import oop.ex6.method.Method;
import oop.ex6.method.MethodException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {
    private final static String RETURN_LINE_REGEX = "^\\s*+return\\s*+;\\s*+$";
    private final static String REGULAR_WORD_SEPARATED = "\\b\\w++\\b";
    private final static Pattern METHOD_CALL_REGEX =
            Pattern.compile("^\\s*+(\\w++)\\s*+\\(([^\\)]*+)\\)\\s*+;\\s*+$");

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

    private static void changeExistVar( String varLine, Variable varToChange){
    }

    private static void declareNewVar(String varLine, Map<String, Variable> scopeVariables){

    }

    public static void varLine(Map<String, Variable> scopeVariables, String varLine) throws GeneralException {
        Matcher wordsInLine = Pattern.compile(REGULAR_WORD_SEPARATED).matcher(varLine);
        if (wordsInLine.find()){
            String firstWordInLine = varLine.substring(wordsInLine.start(), wordsInLine.end());
            if (scopeVariables.containsKey(firstWordInLine)){
                changeExistVar(varLine, scopeVariables.get(firstWordInLine));
                return;
            }
            if (VariableFactory.checkValidType(firstWordInLine)){
                declareNewVar(varLine, scopeVariables);
                return;
            }
        }
        throw new MethodException("invalid var line");
    }

    public static void checkDeclarationLine(String declarationLine,
                                            Map<String, Variable> scopeVariables,
                                            Map<String, Method> programMethods){
        if (isReturnLine(declarationLine)){
            return;
        }
        Matcher methodCall = METHOD_CALL_REGEX.matcher(declarationLine);
        if (methodCall.matches() && programMethods.containsKey(methodCall.group(1)) &&
                programMethods.get(methodCall.group(1)).checkMethodCall(methodCall.group(2), scopeVariables)){
            return;
        }
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
