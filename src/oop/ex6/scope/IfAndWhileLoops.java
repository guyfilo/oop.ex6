//package oop.ex6.scope;
//
//import oop.ex6.GeneralException;
//import oop.ex6.jacasvariable.Variable;
//import oop.ex6.main.LineParser;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class IfAndWhileLoops extends InnerScope{
//
//    private final static String LOOP_PREFIX_REGEX = "^\\s*(?:if|while)\\s+\\(([^\\)]++)\\)\\s*+\\{\\s*+$";
//    private final static String LOOP_BOOLEAN_CONDITION_REGEX = "^\\s*(?:if|while)\\s+\\(([^\\)]++)\\)\\s*+\\{\\s*+$";
//    private final static String NUM = "^\\s*+\\d+\\s*+$";
//    private final static String TRUE_FALSE_OR_NUM = "^\\s*+(?:\\d+|false|true)\\s*+$";
//    private final static String BOLEAN_DOUBLE_INT_TYPES = "^\\s*+(?:int|boolean|double)\\s*+$";
//    private final static String SPLIT_REGEX = "\\|\\||\\&\\&";
//    private final static Pattern BOOLEAN_RECOGNIZER = Pattern.compile("^\\btrue\\b|\\bfalse\\b|\\d+.?\\d*$");
//    private final static String BOOLEAN_RECOGNIZER_REGEX = "^\\btrue\\b|\\bfalse\\b|\\d+.?\\d*$";
//
//
//
//
//    public IfAndWhileLoops(String[] lines, InnerScope supScope, int scopeFirstLineIdx) {
//        super(lines, supScope, scopeFirstLineIdx);
//    }
//
//
//    public boolean checkValidBooleanCondition(String line, Scope scope)  {
//        String booleanCondition = getBooleanCondition(line); // the boolean condition
//        String[] conditions = booleanCondition.split(SPLIT_REGEX);
//        try {
//            for (String condition : conditions) {
//                validCondition(condition, scope);
//            }
//        } catch (GeneralException exception){
//            return false;
//        }
//        return true;
//    }
//
//
//    // checks if the condition is int, true, false of suitable variable
//    public static void validCondition(String condition, Scope scope) throws GeneralException {
//        condition = condition.strip();
//        if (!(condition.matches(BOOLEAN_RECOGNIZER_REGEX) || isSubBooleanInitializedBoleanVar(condition, scope))){
//            throw new GeneralException("invalid boolean condition statement");
//        }
//
//    }
//
//    public static boolean checkIfPrefixIsIfOrWhile(String line){
//        return line.matches(LOOP_PREFIX_REGEX);
//    }
//
//    public static String getBooleanCondition(String line){
//        Pattern loopFirstLinePattern = Pattern.compile(LOOP_PREFIX_REGEX);
//        Matcher loopFirstLine = loopFirstLinePattern.matcher(line);
//        String booleanCondition = loopFirstLine.group(1); // gets the boolean condition
//        return booleanCondition;
//    }
//
//
//    public static boolean isSubBooleanInitializedBoleanVar(String condition, Scope scope) throws GeneralException {
//        if (scope.isVariableInScope(condition)){
//            Variable variableCondition = scope.getScopeVariableByName(condition);
//            if (!(variableCondition.isInitialised() && variableCondition.getType().matches(BOOLEAN_RECOGNIZER_REGEX))){
//                return false;
//            }
//            return true;
//        }
//        return false;
//    }
//
//    public boolean checkLoopTitel(String line, Scope scope){
//        return checkIfPrefixIsIfOrWhile(line) && checkValidBooleanCondition(line, scope);
//    }
//
//
//}
