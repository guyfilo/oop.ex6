package oop.ex6.main;

//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.jacasvariable.VariableFactory;
import oop.ex6.scope.InnerScope;
import oop.ex6.scope.Scope;
import oop.ex6.scope.ScopeException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//_______________________________________CLASS______________________________________________________________//

/**
 * this class parse file lines
 */
public class LineParser {

//*********************************** MAGIC NUMBERS ********************************************************//
    // error messages:
    private final static String INVALID_VARIABLE_CHANGE = "invalid attempt to change variable";
    private final static String INVALID_LOOP_DECLARATION ="invalid loop declaration statement";
    private final static String INVALID_DECLERATION_LINE = "invalid declaration line";
    private final static String INVALID_BOOLEAN_CONDITION ="invalid boolean condition statement";
    private final static String MISSING_VARIABLE ="missing variable in declaration line";


    // regexes:
    private final static String RETURN_LINE_REGEX = "^\\s*+return\\s*+;\\s*+$";
    private final static String REGULAR_WORD_SEPARATED = "\\b\\w++\\b";
    private final static String END_SCOPE_REGEX = "^\\s*+}\\s*+$";
    private final static String DECLERATION_LINE = "^.*;\\s*+$";
    private final static String NEW_SCOPE_REGEX = "^[^{]*+\\{\\s*+$";
    private final static String FIRST_WORD_IS_IF_OR_WHILE_REGEX = "^[^}]*+}\\s*+$";
    private final static String LOOP_PREFIX_REGEX = "^\\s*(?:if|while)\\s*+\\(([^\\)]++)\\)\\s*+\\{\\s*+$";
    private final static String LOOP_BOOLEAN_CONDITION_REGEX =
            "^\\s*(?:if|while)\\s+\\(([^\\)]++)\\)\\s*+\\{\\s*+$";
    private final static String NUM = "^\\s*+\\d+\\s*+$";
    private final static String BOOLEAN_RECOGNIZER_REGEX = "^\\btrue\\b|\\bfalse\\b|-?\\d+\\.?\\d*$";
    private final static String TYPE_SERVES_AS_BOOLEAN_REGEX =
            "^\\bint\\b|\\bdouble\\b|\\bboolean\\b|-?\\d+\\.?\\d*$";
    private final static String EMPTY_LINE_REGEX = "^\\s*+$";
    private final static String COMMENT_LINE_REGEX = "^\\/\\/.*+$";
    private final static String SPLIT_REGEX = "\\|\\||\\&\\&";
    private final static String VAR_DECLARATION_REGEX =
            "\\s++(.+)\\s*+;\\s*+$";

    // patterns:
    private final static Pattern METHOD_CALL_REGEX =
            Pattern.compile("^\\s*+(\\w++)\\s*+\\((.*)\\)\\s*+;\\s*+$");
    private final static Pattern CHANGE_VARIABLE_REGEX =
            Pattern.compile("^\\s*+\\w++\\s*+=\\s*+(.+?)\\s*+;\\s*+$");
    private final static Pattern UNINITIALISED_VAR = Pattern.compile("^\\s*+(\\w++)\\s*+$");
    private final static Pattern INITIALISED_VAR = Pattern.compile("^\\s*+(\\w++)\\s*+=\\s*+(\\S++)\\s*+$");

    // others:
    private final static String FINAL = "final";
    private final static int FIRST = 1;
    private final static int SECOND = 2;
    private final static String COMMA = ",";




//*********************************** DECELERATIONS ********************************************************//

//************************************* FUNCTIONS **********************************************************//

    /**
     * this method checks if a given line is a return line
     * @param line - a given line
     * @return - ture - if the line is return line, false - otherwise
     */
    public static boolean isReturnLine(String line){
        return line.matches(RETURN_LINE_REGEX);
    }

    /**
     * this method changes a variable value
     * @param varLine - a given line with a assignment to a variable
     * @param varToChange - the variable that demands changing
     * @param scope  - the current scope
     * @throws GeneralException - trows if the variable change is invalid
     */
    private static void changeExistVar( String varLine, Variable varToChange,
                                        Scope scope) throws GeneralException {
        Matcher changeVarMach = CHANGE_VARIABLE_REGEX.matcher(varLine);
        if (changeVarMach.matches()){
            if (scope.isVariableInScope(changeVarMach.group(FIRST))) {
                varToChange.changeValue(scope.getScopeVariableByName(changeVarMach.group(1)));
                return;
            }
            varToChange.changeValue(changeVarMach.group(FIRST));
            return;
        }
        throw new GeneralException(INVALID_VARIABLE_CHANGE);
    }


    private static void declareNewVar
            (String varLine, String type, boolean isFinal, Scope scope) throws GeneralException {
            Matcher varDeclaration = Pattern.compile(type + VAR_DECLARATION_REGEX).matcher(varLine);
            if (!varDeclaration.find()){
                throw new GeneralException(MISSING_VARIABLE);
            }
            String[] declarations = varDeclaration.group(FIRST).split(COMMA);
            for (String declaration : declarations){
                Matcher varMatcher = UNINITIALISED_VAR.matcher(declaration);
                if (varMatcher.matches()){
                    Variable newVar = VariableFactory.createNewVar
                            (varMatcher.group(FIRST), type, null, isFinal, scope);
                    scope.addNewVar(newVar);
                    continue;
                }
                varMatcher.usePattern(INITIALISED_VAR);
                if (varMatcher.matches()){
                    Variable newVar;
                    if (scope.isVariableInScope(varMatcher.group(SECOND))){
                        Variable otherVar = scope.getScopeVariableByName(varMatcher.group(SECOND));
                        newVar = VariableFactory.createNewVar
                                (otherVar, varMatcher.group(FIRST), type, isFinal, scope);
                    } else {
                        newVar = VariableFactory.createNewVar
                                (varMatcher.group(FIRST), type, varMatcher.group(SECOND), isFinal, scope);
                    }
                    scope.addNewVar(newVar);
                    continue;
                }
                throw new GeneralException(INVALID_DECLERATION_LINE);
            }
    }

    /**
     * this method checks that a given line with variable declaration or assignment is valid
     * @param varLine - a variable line (with variable declaration or assignment)
     * @param scope - the current scope
     * @return - true iff the line is valid
     * @throws GeneralException - trows if the line is invalid
     */
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
        throw new ScopeException(INVALID_DECLERATION_LINE);
    }

    /**
     * this method checks if a line that finishes with ";" is valid
     * @param declarationLine - a given line that finishes with ";"
     * @param scope - the current scope
     * @throws GeneralException - trows if the line is invalid
     */
    public static void checkDeclarationLine(String declarationLine, InnerScope scope) throws
            GeneralException {
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
        throw new GeneralException(INVALID_DECLERATION_LINE);
    }

    /**
     * checks if the line is close bracket
     * @param line - a given line
     * @return true - if the line is close bracket, false - otherwise
     */
    public static boolean isCloseScopeLine(String line){
        return line.matches(END_SCOPE_REGEX);
    }

    /**
     * checks if the line ends with ";"
     * @param line - a given line
     * @return true - if the line is ends with ";", false - otherwise
     */
    public static boolean isDeclerationLine(String line){
        return line.matches(DECLERATION_LINE);
    }

    /**
     * checks if the line is open bracket
     * @param line - a given line
     * @return true - if the line is open bracket, false - otherwise
     */
    public static boolean isNewScopeLine(String line){
        return line.matches(NEW_SCOPE_REGEX);
    }


    /**
     * checks if the line is declaration on "if" or "while" loop
     * @param line - a given line
     * @throws GeneralException - trows if the line is not declaration on "if" or "while" loop
     */
    public static void checkIfPrefixIsIfOrWhile(String line) throws GeneralException {
        if (!line.matches(LOOP_PREFIX_REGEX)){
            throw new GeneralException(INVALID_LOOP_DECLARATION);
        }
    }

    /**
     * this method checks if each component in a condition is a valid boolean condition
     * @param line - a given line
     * @param scope - the current scope
     * @throws GeneralException - trows if the boolean condition is not valid
     */
    public static void checkValidBooleanCondition(String line, Scope scope) throws GeneralException {
        String booleanCondition = getBooleanCondition(line); // the boolean condition
        String[] conditions = booleanCondition.split(SPLIT_REGEX);
        for (String condition : conditions) {
            validCondition(condition, scope);
            }
    }

    /**
     * this method checks if a sub boolean condition is valid
     * @param condition - a sub boolean condition
     * @param scope - the current scope
     * @throws GeneralException - trows if the boolean condition is not valid
     */
    public static void validCondition(String condition, Scope scope) throws GeneralException {
        condition = condition.strip();
        isSubBooleanInitializedBoleanVar(condition, scope);
    }


    /**
     * this method gets a line that holds boolean condition and returns only the boolean condition
     * @param line - a given line
     * @return - the boolean condition
     */
    public static String getBooleanCondition(String line){
        Pattern loopFirstLinePattern = Pattern.compile(LOOP_PREFIX_REGEX);
        Matcher loopFirstLine = loopFirstLinePattern.matcher(line);
        loopFirstLine.find();
        String booleanCondition = loopFirstLine.group(1);
        return booleanCondition;
    }

    /**
     * this method checks is a sub boolean condition is an existing variable
     * @param condition - a boolean condition
     * @param scope - the current scope
     * @throws GeneralException - trows if the boolean condition is not valid
     */
    public static void isSubBooleanInitializedBoleanVar(String condition, Scope scope) throws
            GeneralException {
        if (scope.isVariableInScope(condition)) {
            Variable variableCondition = scope.getScopeVariableByName(condition);
            if (variableCondition.isInitialised() && variableCondition.getType().
                    matches(TYPE_SERVES_AS_BOOLEAN_REGEX)) {
                return;
            }
        } else if (condition.matches(BOOLEAN_RECOGNIZER_REGEX)){
            return;
        }
        throw new GeneralException(INVALID_BOOLEAN_CONDITION);
    }

    /**
     * this method checks if the loop declaration is valid
     * @param line - a given line
     * @param scope - the current scope
     * @throws GeneralException - trows if the boolean condition is not valid
     */
    public static void checkLoopTitle(String line, Scope scope) throws GeneralException {
        checkIfPrefixIsIfOrWhile(line);
        checkValidBooleanCondition(line, scope);
    }

    /**
     * checks if the given line is empty line
     * @param line - a given line
     * @return - true - if the line is empty line, false - otherwise
     */
    public static boolean  isEmptyLine(String line){
        return line.matches(EMPTY_LINE_REGEX);
    }

    /**
     * checks if the given line is a comment line
     * @param line - a given line
     * @return - true - if the line is a comment line, false - otherwise
     */
    public static boolean isCommentLine(String line){
        return line.matches(COMMENT_LINE_REGEX);
    }
}
