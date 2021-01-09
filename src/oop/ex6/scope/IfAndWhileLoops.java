package oop.ex6.scope;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfAndWhileLoops extends InnerScope{

    private final static String LOOP_PREFIX_REGEX = "^\\s*(?:if|while)\\s+\\(([^\\)]++)\\)\\s*+\\{\\s*+$";
    private final static String LOOP_BOOLEAN_CONDITION_REGEX = "^\\s*(?:if|while)\\s+\\(([^\\)]++)\\)\\s*+\\{\\s*+$";
    private final static String NUM = "^\\s*+\\d+\\s*+$";
    private final static String TRUE_FALSE_OR_NUM = "^\\s*+(?:\\d+|false|true)\\s*+$";
    private final static String BOLEAN_DOUBLE_INT_TYPES = "^\\s*+(?:int|boolean|double)\\s*+$";


    public IfAndWhileLoops(String[] lines, InnerScope supScope, int scopeFirstLineIdx) {
        super(lines, supScope, scopeFirstLineIdx);
    }


    public boolean checkValidBooleanCondition(String line) {
        String booleanCondition = getBooleanCondition(line); // the boolean condition

        Pattern booleanConditionPattern = Pattern.compile(LOOP_BOOLEAN_CONDITION_REGEX); //todo: fix regex
        Matcher booleanConditionsMatcher = booleanConditionPattern.matcher(booleanCondition);

        if (booleanConditionsMatcher.matches()) {
            int booleanSubStatements = booleanConditionsMatcher.groupCount(); // num of sub boolean strings

            for (int i = 0; i < booleanSubStatements + 1; i++) {
                String curStatment = booleanConditionsMatcher.group(i);
                if (curStatment.matches(TRUE_FALSE_OR_NUM)){
                    continue;
                } else {
                    if (isSubBooleanInitializedBoleanVar(curStatment)) {
                        continue;
                    }
                }
                return false;
            }
            return true;
        }
        return false;
    }




    public static boolean checkIfPrefixIsIfOrWhile(String line){
        return line.matches(LOOP_PREFIX_REGEX);
    }

    public static String getBooleanCondition(String line){
        Pattern loopFirstLinePattern = Pattern.compile(LOOP_PREFIX_REGEX);
        Matcher loopFirstLine = loopFirstLinePattern.matcher(line);
        String booleanCondition = loopFirstLine.group(1); // gets the boolean condition
        return booleanCondition;
    }

    public boolean isSubBooleanInitializedBoleanVar(String variableName){
        if (this.getScopeVariables().containsKey(variableName)){
            Variable variable = this.getScopeVariables().get(variableName);
            if (variable.isInitialised() && variable.getType().matches(BOLEAN_DOUBLE_INT_TYPES)){
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean checkLoopTitel(String line){
        return checkIfPrefixIsIfOrWhile(line) && checkValidBooleanCondition(line);
    }


}
