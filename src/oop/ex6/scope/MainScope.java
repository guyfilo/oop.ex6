//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6.scope;

//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Argument;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;
import oop.ex6.method.Method;
import oop.ex6.method.MethodException;
import oop.ex6.method.MethodFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
//_______________________________________CLASS______________________________________________________________//

/**
 * this class creats a main scope object - the outer scope
 */
public class MainScope extends Scope {

//*********************************** MAGIC NUMBERS ********************************************************//
    // regexes:
    private final static String COMMENT_LINE_REGEX = "^\\/\\/.*+$";
    private final static String VAR_DECLARATION_REGEX = "^[^;]*+;\\s*+$";
    private final static String NEW_SCOPE_REGEX = "^[^{]*+\\{\\s*+$";
    private final static String END_SCOPE_REGEX = "^[^}]*+}\\s*+$";
    private final static String EMPTY_LINE_REGEX = "^\\s*+$";

    // err messages:
    private final static String SCOPE_NOT_CLOSE_MSG = "} expected";
    private final static String INVALID_GLOBAL_LINE_ERR_MSG = "line %d is an invalid global scope line";

    // others:
    private final static int ZERO = 0;
    private final static int ONE = 1;


//*********************************** DECELERATIONS ********************************************************//
    private final Map<String, Method> programMethods = new HashMap<>();
    private LineNumberReader fileReader;
    private String line;

//************************************* FUNCTIONS **********************************************************//
    /**
     * the main scope constractor - crates file reader
     * @param sourceFilePath - the path of the current checked file
     * @throws FileNotFoundException - throws if the given file in not found in the path
     */
    public MainScope(String sourceFilePath) throws FileNotFoundException {
        fileReader = new LineNumberReader(new FileReader(new File(sourceFilePath)));
    }

    /**
     * this methods creates the methods and variables of this scope
     * @throws IOException - throws if there is a problem with the file reader
     * @throws GeneralException - throws if there is invalid line in the main scope
     */
    public void getGlobalMembers() throws IOException, GeneralException {
        line = fileReader.readLine();
        while (line != null){
            if (line.matches(COMMENT_LINE_REGEX) || line.matches(EMPTY_LINE_REGEX)){
                line = fileReader.readLine();
                continue;
            }
            if (line.matches(VAR_DECLARATION_REGEX)){
                LineParser.varLineCheck(line, this);
                line = fileReader.readLine();
                continue;
            }
            if (line.matches(NEW_SCOPE_REGEX)){
                makeNewMethod();
                continue;
            }
            throw new ScopeException(String.format(INVALID_GLOBAL_LINE_ERR_MSG, fileReader.getLineNumber()));
        }
    }

    /**
     * this method creates new method
     * @throws GeneralException - throws if not all the methods in the scope are valid
     * @throws IOException - throws if theres is an input of output problem with the file reader
     */
    private void makeNewMethod() throws GeneralException, IOException {
        Method newMethod = MethodFactory.createMethod(line);
        int bracketsBalance = ONE;
        line = fileReader.readLine();
        while (bracketsBalance != ZERO && line != null){
            newMethod.addLine(line);
            if (line.matches(NEW_SCOPE_REGEX)){
                bracketsBalance++;
            } else if (line.matches(END_SCOPE_REGEX)){
                bracketsBalance--;
            }
            line = fileReader.readLine();
        }
        if (bracketsBalance != ZERO || !newMethod.isEndWithReturn()) {
            throw new MethodException(SCOPE_NOT_CLOSE_MSG);
        }
        addNewMethod(newMethod);
    }

    /**
     * this method checks is a variable with a given name is belongs to this scope
     * @param variableName - variable name
     * @return - true - if the variable belongs to this scope, false - otherwise
     */
    @Override
    public boolean isVariableInScope(String variableName) {
        return getScopeVariables().containsKey(variableName);
    }

    /**
     * this method gets variable name and returns the variable with this name
     * @param variableName - a variable name
     * @return - the variable object
     * @throws ScopeException - if there is no variable in the scope with this name, it throws exception
     */
    @Override
    public Variable getScopeVariableByName(String variableName) {
        return getScopeVariables().get(variableName);
    }

    /**
     * this methods returns a map with the global variables
     * @return  a map with the global variables
     */
    private Map<String, Variable> getGlobalVariables() {
        Map<String, Variable> globalVariables = new HashMap<>();
        for (Variable globalVariable: getScopeVariables().values()){
            globalVariables.put(globalVariable.getName(), new Variable(globalVariable));
        }
        return globalVariables;
    }

    /**
     * this method returns the method scope
     * @param method - a given method
     * @return - the scope that defines the method
     * @throws ScopeException
     */
    private InnerScope getMethodScope(Method method) throws ScopeException {
        InnerScope methodScope =
                new InnerScope(method.getMethodLines(), getScopeMethods(), getGlobalVariables());
        for (Argument arg: method.getArguments()){
            methodScope.addNewVar(new Variable(arg, methodScope));
        }
        return methodScope;
    }

    /**
     * this method checks that all the methods in the scope are valid
     * @throws GeneralException - throws if not all the methods in the scope are valid
     */
    public void checkMethodsScopes() throws GeneralException {
        for (Method method: getScopeMethods().values()){
            InnerScope methodScope = getMethodScope(method);
            methodScope.checkValidScope();
        }
    }

}
