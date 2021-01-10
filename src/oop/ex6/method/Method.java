//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6.method;

//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.jacasvariable.Argument;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.jacasvariable.VariableException;
import oop.ex6.main.LineParser;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//_______________________________________CLASS______________________________________________________________//

/**
 * this class creates an method object
 */
public class Method {

//*********************************** MAGIC NUMBERS ********************************************************//
    // regexes:
    private final static Pattern WORD_NO_SPACES_IN_SIDES_PATTERN = Pattern.compile("^\\s*+(\\S++)\\s*+$");
    private final static String EMPTY_ARG = "^\\s*+$";

    // messages:
    private final String NO_RETURN_ERR_MSG = "method does not contain return statement";
    private final String INVALID_ARGS_AMOUNT_ERR_MSG = "method got invalid amount of args";
    private final String INVALID_ARG_ERR_MSG = "method got invalid argument";

    // others:
    private final String COMMA = ",";

//*********************************** DECELERATIONS ********************************************************//
    private String name;
    private ArrayList<Argument> arguments;
    private final ArrayList<String> methodLines = new ArrayList<>();
//************************************* FUNCTIONS **********************************************************//

    /**
     * class constructor
     * @param name - the method mame
     * @param arguments - the method arduments
     */
    public Method(String name, ArrayList<Argument> arguments){
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * this method adds line to the class variable of the methods lines
     * @param line - a given line
     */
    public void addLine(String line){
        methodLines.add(line);
    }

    /**
     * @return - the method name
     */
    public String getName(){
        return name;
    }

    /**
     * this method checks if the method ends with return statment
     * @return true iff the method ends with return
     * @throws MethodException - throws if the method last line is not return
     */
    public boolean isEndWithReturn() throws MethodException {
        if (!LineParser.isReturnLine(methodLines.get(methodLines.size() - 2))){
            throw new MethodException(NO_RETURN_ERR_MSG);
        }
        return true;
    }

    /**
     * this method checks if the method call is valid
     * @param argsLine - the args the method is calling with
     * @param scopeVariables - the scope variables
     * @return - true iff the method call is valid
     * @throws MethodException - if the given args are invalid
     * @throws VariableException - if a given arg is invalid variable
     */
    public boolean checkMethodCall(String argsLine, Map<String, Variable> scopeVariables)
            throws MethodException, VariableException {
        // 0 - separate args with comma
        String[] args = argsLine.split(COMMA);
        if (args.length == 1 && args[0].matches(EMPTY_ARG) && arguments.size() == 0){
            return true;
        }
        if (args.length != arguments.size()){
            throw new MethodException(INVALID_ARGS_AMOUNT_ERR_MSG);
        }
        for (int i = 0; i < args.length; i++) {
            Matcher arg = WORD_NO_SPACES_IN_SIDES_PATTERN.matcher(args[i]);
            if (!arg.matches()) {
                throw new MethodException(INVALID_ARG_ERR_MSG);
            }
            String argAsString = arg.group(1);
            Argument argDemands = this.arguments.get(i); // the type the arg should be - the arg is a string
            if (scopeVariables.containsKey(argAsString)) {
                argDemands.checkValidValue(scopeVariables.get(argAsString));
            } else {
                argDemands.checkValidValue(argAsString);
            }
        }
        return true;
    }


    /**
     * @return all the methods lines array
     */
    public ArrayList<String> getMethodLines() {
        return methodLines;
    }

    /**
     * @return the method arguments
     */
    public ArrayList<Argument> getArguments() {
        return arguments;
    }
}
