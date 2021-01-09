package oop.ex6.main;

import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.jacasvariable.VariableFactory;
import oop.ex6.scope.InnerScope;
import oop.ex6.scope.Scope;

import java.util.HashMap;
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
    private final static String VAR_DECLARATION_REGEX =
            "\\s++([^;]++)\\s*+;\\s*+$";
    private final static Pattern UNINITIALISED_VAR = Pattern.compile("^\\s*+(\\w++)\\s*+$");
    private final static Pattern INITIALISED_VAR = Pattern.compile("^\\s*+(\\w++)\\s*+=\\s*+(\\S++)\\s*+$");
    private final static String FIRST_WORD_IS_IF_OR_WHILE_REGEX = "^[^}]*+}\\s*+$";
    private final static String LOOP_PREFIX_REGEX = "^\\s*(?:if|while)\\s+\\(([^\\)]++)\\)\\s*+\\{\\s*+$";
    private final static String LOOP_BOOLEAN_CONDITION_REGEX = "^\\s*(?:if|while)\\s+\\(([^\\)]++)\\)\\s*+\\{\\s*+$";
    private final static String NUM = "^\\s*+\\d+\\s*+$";
    private final static String BOOLEAN_RECOGNIZER_REGEX = "^\\btrue\\b|\\bfalse\\b|\\d+.?\\d*$";
    private final static String SPLIT_REGEX = "\\|\\||\\&\\&";





    public static boolean isReturnLine(String line){
        return line.matches(RETURN_LINE_REGEX);
    }

    private static void changeExistVar( String varLine, Variable varToChange,
                                        Scope scope) throws GeneralException {
        Matcher changeVarMach = CHANGE_VARIABLE_REGEX.matcher(varLine);
        if (changeVarMach.matches()){
            if (scope.isVariableInScope(changeVarMach.group(1))) {
                varToChange.changeValue(scope.getScopeVariableByName(changeVarMach.group(1)));
                return;
            }
            varToChange.changeValue(changeVarMach.group(1));
            return;
        }
        throw new GeneralException("");
    }

    //todo: change to scope
    private static void declareNewVar
            (String varLine, String type, boolean isFinal, Scope scope) throws GeneralException {
            Matcher varDeclaration = Pattern.compile(type + VAR_DECLARATION_REGEX).matcher(varLine);
            if (!varDeclaration.find()){
                throw new GeneralException("declaration line must contain declarations");
            }
            String[] declarations = varDeclaration.group(1).split(",");
            for (String declaration : declarations){
                Matcher varMatcher = UNINITIALISED_VAR.matcher(declaration);
                if (varMatcher.matches()){
                    Variable newVar = VariableFactory.createNewVar
                            (varMatcher.group(1), type, null, isFinal);
                    scope.addNewVar(newVar);
                    continue;
                }
                varMatcher.usePattern(INITIALISED_VAR);
                if (varMatcher.matches()){
                    Variable newVar;
                    if (scope.isVariableInScope(varMatcher.group(2))){
                        Variable otherVar = scope.getScopeVariableByName(varMatcher.group(2));
                        newVar = VariableFactory.createNewVar
                                (otherVar, varMatcher.group(1), type, isFinal);
                    } else {
                        newVar = VariableFactory.createNewVar
                                (varMatcher.group(1), type, varMatcher.group(2), isFinal);
                    }
                    scope.addNewVar(newVar);
                    continue;
                }
                throw new GeneralException("bad declaration");
            }
    }

    public static boolean varLineCheck(String varLine, Scope scope)
            throws GeneralException {
        Matcher wordsInLine = Pattern.compile(REGULAR_WORD_SEPARATED).matcher(varLine);
        if (wordsInLine.find()){
            String firstWordInLine = varLine.substring(wordsInLine.start(), wordsInLine.end());
            if (scope.isVariableInScope(firstWordInLine)){
                changeExistVar(varLine, scope.getScopeVariableByName(firstWordInLine), scope);
                return true;
            }
            if (VariableFactory.checkValidType(firstWordInLine)){
                declareNewVar(varLine, firstWordInLine, false, scope);
                return true;
            }
            if (firstWordInLine.equals(FINAL) && wordsInLine.find()){
                String type = varLine.substring(wordsInLine.start(), wordsInLine.end());
                if (VariableFactory.checkValidType(type)){
                    declareNewVar(varLine, type, true, scope);
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
        if (methodCall.matches() && scope.getMethodByName(methodCall.group(1)).checkMethodCall
                (methodCall.group(2), scope.getScopeVariables())){
            return;
        }
        if (varLineCheck(declarationLine, scope)){
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

    public boolean checkLoopLine(String line, Map<String, Variable> scopeVariables ){
        return false; //todo: write check
    }

    public static void checkIfPrefixIsIfOrWhile(String line) throws GeneralException {
        if (!line.matches(LOOP_PREFIX_REGEX)){
            throw new GeneralException("invalid boolean condition statement");
        }
    }


    public void checkValidBooleanCondition(String line, Scope scope) throws GeneralException {
        String booleanCondition = getBooleanCondition(line); // the boolean condition
        String[] conditions = booleanCondition.split(SPLIT_REGEX);
        for (String condition : conditions) {
            validCondition(condition, scope);
            }
    }


    // checks if the condition is int, true, false of suitable variable
    public static void validCondition(String condition, Scope scope) throws GeneralException {
        condition = condition.strip();
        condition.matches(BOOLEAN_RECOGNIZER_REGEX);
        isSubBooleanInitializedBoleanVar(condition, scope);
    }



    public static String getBooleanCondition(String line){
        Pattern loopFirstLinePattern = Pattern.compile(LOOP_PREFIX_REGEX);
        Matcher loopFirstLine = loopFirstLinePattern.matcher(line);
        String booleanCondition = loopFirstLine.group(1); // gets the boolean condition
        return booleanCondition;
    }


    public static void isSubBooleanInitializedBoleanVar(String condition, Scope scope) throws GeneralException {
        if (scope.isVariableInScope(condition)){
            Variable variableCondition = scope.getScopeVariableByName(condition);
            if (!(variableCondition.isInitialised() && variableCondition.getType().matches(BOOLEAN_RECOGNIZER_REGEX))){
                throw new GeneralException("invalid boolean condition statement");
            }
        }
        throw new GeneralException("invalid boolean condition statement");
    }

    public void checkLoopTitel(String line, Scope scope) throws GeneralException {
        checkIfPrefixIsIfOrWhile(line);
        checkValidBooleanCondition(line, scope);
    }





}
