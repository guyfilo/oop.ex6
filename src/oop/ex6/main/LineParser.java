package oop.ex6.main;

import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.jacasvariable.VariableException;
import oop.ex6.jacasvariable.VariableFactory;
import oop.ex6.method.Method;
import oop.ex6.method.MethodException;
import oop.ex6.scope.InnerScope;
import oop.ex6.scope.Scope;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {
    private final static String RETURN_LINE_REGEX = "^\\s*+return\\s*+;\\s*+$";
    private final static String REGULAR_WORD_SEPARATED = "\\b\\w++\\b";
    private final static Pattern METHOD_CALL_REGEX =
            Pattern.compile("^\\s*+(\\w++)\\s*+\\(([^\\)]*+)\\)\\s*+;\\s*+$");
    private final static String FINAL = "final";

    private final static String END_SCOPE_REGEX = "^[^}]*+}\\s*+$";
    private final static String DECLERATION_LINE = "^.*+;{1}$";
    private final static String NEW_SCOPE_REGEX = "^[^{]*+\\{\\s*+$";
    private final static Pattern CHANGE_VARIABLE_REGEX =
            Pattern.compile("^\\s*+\\w++\\s*+=\\s*+([^;\\s]*+)\\s*+;\\s*+$");


    public void statementLineParser(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public void scopeBeginsParser(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public void lineVerification(String line, Map<String, Variable> scopeVariables, Map<String, Variable> methods){
    }

    public static boolean isReturnLine(String line){
        return line.matches(RETURN_LINE_REGEX);
    }

    private static void changeExistVar( String varLine, Variable varToChange,
                                        Map<String, Variable> scopeVariables) throws GeneralException {
        Matcher changeVarMach = CHANGE_VARIABLE_REGEX.matcher(varLine);
        if (changeVarMach.matches()){
            if (scopeVariables.containsKey(changeVarMach.group(1))) {
                varToChange.changeValue(scopeVariables.get(changeVarMach.group(1)));
                return;
            }
            varToChange.changeValue(changeVarMach.group(1));
        }
        throw new GeneralException("");
    }

    private static void declareNewVar
            (String varLine, String type, boolean isFinal, Map<String, Variable> scopeVariables){


    }

    public static boolean varLineCheck(String varLine, Map<String, Variable> scopeVariables)
            throws GeneralException {
        Matcher wordsInLine = Pattern.compile(REGULAR_WORD_SEPARATED).matcher(varLine);
        if (wordsInLine.find()){
            String firstWordInLine = varLine.substring(wordsInLine.start(), wordsInLine.end());
            if (scopeVariables.containsKey(firstWordInLine)){
                changeExistVar(varLine, scopeVariables.get(firstWordInLine), scopeVariables);
                return true;
            }
            if (VariableFactory.checkValidType(firstWordInLine)){
                declareNewVar(varLine, firstWordInLine, false, scopeVariables);
                return true;
            }
            if (firstWordInLine.equals(FINAL)){
                String type = varLine.substring(wordsInLine.start(), wordsInLine.end());
                if (VariableFactory.checkValidType(type)){
                    declareNewVar(varLine, firstWordInLine, true, scopeVariables);
                    return true;
                }
            }
        }
        return false;
    }

    public static void checkDeclarationLine(String declarationLine, InnerScope scope) throws GeneralException {
        if (isReturnLine(declarationLine)){
            return;
        }
        Matcher methodCall = METHOD_CALL_REGEX.matcher(declarationLine);
        if (methodCall.matches() && scope.getGlobalMethods().containsKey(methodCall.group(1)) &&
                scope.getGlobalMethods().get
                        (methodCall.group(1)).checkMethodCall(methodCall.group(2), scope.scopeVariables)){
            return;
        }
        if (varLineCheck(declarationLine, scope.scopeVariables)){
            return;
        }
        throw new GeneralException("invalid declaration line");
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

    public static boolean checkLoopLine(String line, Map<String, Variable> scopeVariables ){
        return false; //todo: write check
    }

}
