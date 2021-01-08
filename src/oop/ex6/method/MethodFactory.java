package oop.ex6.method;

import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.jacasvariable.VariableFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodFactory {
    private final static String GENERIC_RETURN_VALUE = "void";
    private final static Pattern METHOD_DECLARATION =
            Pattern.compile("^\\s*+(\\w+)\\s*+(\\w++)\\s*+\\(([^)]*+)\\)\\s*+\\{\\s*+$");
    private final static String METHOD_ARGS = "^\\s*+(?: *+\\w++ ++\\w++ *+)?+(?:, *+\\w++ ++\\w++ *+)*$";
    private final static String ILLEGAL_METHOD_DECLARATION_MSG = "illegal way to declare method";
    private final static String RETURN_VAL_MSG = "illegal way to declare method";
    private final static String BAD_ARG_MSG = "illegal method argument declaration";
    private static Matcher matcher;
    private final static String FINAL_ARG = "^\\s*+final\\s*+(\\w++)\\s++(\\w++)\\s*+$";
    private final static String ARG = "^\\s*+(\\w++)\\s++(\\w++)\\s*+$";
    private final static String EMPTY_ARG = "^\\s*+$";
    private final static String METHOD_NAME = "^[a-zA-Z]++\\w*+$";

    //todo:add save word check


    public static Method createMethod(String methodDeclaration) throws GeneralException {
        matcher = METHOD_DECLARATION.matcher(methodDeclaration);
        if (!matcher.matches()){
            throw new MethodException(ILLEGAL_METHOD_DECLARATION_MSG);
        }
        // first item in line is void
        if (!matcher.group(1).equals(GENERIC_RETURN_VALUE)){
            throw new MethodException(RETURN_VAL_MSG);
        }
        if (!matcher.group(2).matches(METHOD_NAME)){
            throw new MethodException(RETURN_VAL_MSG);
        }
        ArrayList<Variable> arguments = new ArrayList<>();
        String[] args = matcher.group(3).split(",");
        for (String arg: args){
            if (arg.matches(ARG)) {
                Matcher argMatcher = Pattern.compile(ARG).matcher(arg);
                argMatcher.matches();
                arguments.add(
                        VariableFactory.createNewVar(argMatcher.group(1), argMatcher.group(2), null, false));
            } else if (arg.matches(FINAL_ARG)) {
                Matcher argMatcher = Pattern.compile(FINAL_ARG).matcher(arg);
                argMatcher.matches();
                arguments.add(
                        VariableFactory.createNewVar(argMatcher.group(1), argMatcher.group(2), null , true));
            } else if (arg.matches(EMPTY_ARG) && args.length == 1) {
            }  else {
                throw new MethodException(BAD_ARG_MSG);
            }
        }
        return new Method(matcher.group(2), arguments);
    }

    public static void main(String[] args) {
        String str = "  ; ";
        Matcher matcher1 = Pattern.compile("\\b\\w++\\b").matcher(str);
        while (matcher1.find()){
            System.out.println(str.substring(matcher1.start(), matcher1.end()));
        }
    }
}
