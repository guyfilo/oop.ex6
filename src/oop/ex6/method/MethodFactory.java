//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6.method;

//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Argument;
import oop.ex6.jacasvariable.VariableFactory;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//_______________________________________CLASS______________________________________________________________//

/**
 * this class creates methods
 */
public class MethodFactory {

//*********************************** MAGIC NUMBERS ********************************************************//
    // regexes:
    private final static String FINAL_ARG_REGEX = "^\\s*+final\\s*+(\\w++)\\s++(\\w++)\\s*+$";
    private final static String ARG_REGEX = "^\\s*+(\\w++)\\s++(\\w++)\\s*+$";
    private final static String EMPTY_ARG_REGEX = "^\\s*+$";
    private final static String METHOD_NAME_REGEX = "^[a-zA-Z]++\\w*+$";
    private final static String METHOD_ARGS = "^\\s*+(?: *+\\w++ ++\\w++ *+)?+(?:, *+\\w++ ++\\w++ *+)*$";


    // patterns:

    private final static Pattern METHOD_DECLARATION =
            Pattern.compile("^\\s*+(\\w+)\\s*+(\\w++)\\s*+\\(([^)]*+)\\)\\s*+\\{\\s*+$");

    // error messages:
    private final static String ILLEGAL_METHOD_DECLARATION_MSG = "illegal way to declare method";
    private final static String RETURN_VAL_MSG = "illegal way to declare method";
    private final static String BAD_ARG_MSG = "illegal method argument declaration";

    //others:
    private final static String GENERIC_RETURN_VALUE = "void"; //todo:?
    private final static String COMMA = ",";
    private final static int FIRST = 1;
    private final static int SECOND = 2;



    //*********************************** DECELERATIONS ********************************************************//
    private static Matcher matcher;

//************************************* FUNCTIONS **********************************************************//

    /**
     * this method creats new method
     * @param methodDeclaration - the method decleration line
     * @return - the created method
     * @throws GeneralException - if theres a problem while creating the method
     */
    public static Method createMethod(String methodDeclaration) throws GeneralException {
        matcher = METHOD_DECLARATION.matcher(methodDeclaration);
        if (!matcher.matches()){
            throw new MethodException(ILLEGAL_METHOD_DECLARATION_MSG);
        }
        // first item in line is void
        if (!matcher.group(1).equals(GENERIC_RETURN_VALUE)){
            throw new MethodException(RETURN_VAL_MSG);
        }
        if (!matcher.group(2).matches(METHOD_NAME_REGEX)){
            throw new MethodException(RETURN_VAL_MSG);
        }
        ArrayList<Argument> arguments = new ArrayList<>();
        String[] args = matcher.group(3).split(COMMA);
        for (String arg: args){
            if (arg.matches(ARG_REGEX)) {
                Matcher argMatcher = Pattern.compile(ARG_REGEX).matcher(arg);
                argMatcher.matches();
                arguments.add(
                        VariableFactory.createNewArg(argMatcher.group(SECOND), argMatcher.group(FIRST), false));
            } else if (arg.matches(FINAL_ARG_REGEX)) {
                Matcher argMatcher = Pattern.compile(FINAL_ARG_REGEX).matcher(arg);
                argMatcher.matches();
                arguments.add(
                        VariableFactory.createNewArg(argMatcher.group(SECOND), argMatcher.group(FIRST), true));
            } else if (arg.matches(EMPTY_ARG_REGEX) && args.length == FIRST) {
            }  else {
                throw new MethodException(BAD_ARG_MSG);
            }
        }
        return new Method(matcher.group(SECOND), arguments);
    }
}
