//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6.scope;
//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.jacasvariable.Variable;
import oop.ex6.method.Method;
import java.util.HashMap;
import java.util.Map;
//_______________________________________CLASS______________________________________________________________//

/**
 * this class creates an abstract scope
 */
public abstract class Scope {

//*********************************** MAGIC NUMBERS ********************************************************//
    private final String ERR_MSG_NO_TWO_VARIABLES_SAME_NAME = "try to declare a variable that already has " +
        "been declared in this scope";
    private final String NO_VAR_WITH_NAME_IN_SCOPE = "this variable not exist in this scope";
    private final String NO_METHOD_WITH_NAME_IN_SCOPE = "this method not exist in this scope";
    private final String METHOD_EXISTS = "given method already exists in scope";


    //*********************************** DECELERATIONS ********************************************************//
    private final Map<String, Variable> scopeVariables;
    private final Map<String, Method> scopeMethods;
//************************************* FUNCTIONS **********************************************************//

    /**
     * the class constructor - creates new scope object
     */
    public Scope(){
        scopeVariables = new HashMap<>();
        scopeMethods = new HashMap<>();
    }

    /**
     * the class constructor - creates new scope object with Maps of methods and variables
     * @param scopeMethods - a Map with the scope methods
     * @param scopeVariables - a Map with the scope variables
     */
    public Scope(Map<String, Method> scopeMethods, Map<String, Variable> scopeVariables){
        this.scopeMethods = scopeMethods;
        this.scopeVariables = scopeVariables;
    }

    /**
     * the class constructor - creates new scope object with another scope
     * @param otherScope - the other scope
     */
    public Scope(Scope otherScope){
        this.scopeMethods = otherScope.getScopeMethods();
        this.scopeVariables = new HashMap<>();
        this.scopeVariables.putAll(otherScope.scopeVariables);
    }

    /**
     * this method adds a new var to the scope variable's Map
     * @param newVariable - the new variable
     * @throws ScopeException trows when the new var already declared
     */
    public void addNewVar(Variable newVariable) throws ScopeException {
        if (!isVariableDeclaredInScope(newVariable.getName())){
           scopeVariables.put(newVariable.getName(), newVariable);
           return;
        }
        throw new ScopeException(ERR_MSG_NO_TWO_VARIABLES_SAME_NAME);
    }

    /**
     * this method returns a map with the scope variables
     * @return a map with the scope variables
     */
    public Map<String, Variable> getScopeVariables() {
        return scopeVariables;
    }

    /**
     * this method checks if a variable with the given name already declared in this scope
     * @param variableName - a name of a variable
     * @return - true - if theres a variable with this name in the scope, false - otherwise
     * @throws ScopeException - if there is no variable in the scope with this name, it throws exception
     */
    public boolean isVariableDeclaredInScope(String variableName) throws ScopeException {
        return isVariableInScope(variableName) &&
                this.equals(getScopeVariableByName(variableName).getVarScope());
    }

    /**
     * this method checks is a variable with a given name is belongs to this scope
     * @param variableName - variable name
     * @return - true - if the variable belongs to this scope, false - otherwise
     */
    public boolean isVariableInScope(String variableName){
        return scopeVariables.containsKey(variableName);
    }

    /**
     * this method gets variable name and returns the variable with this name
     * @param variableName - a variable name
     * @return - the variable object
     * @throws ScopeException - if there is no variable in the scope with this name, it throws exception
     */
    public Variable getScopeVariableByName(String variableName) throws ScopeException {
        if (isVariableInScope(variableName)){
            return scopeVariables.get(variableName);
        }
        throw new ScopeException(NO_VAR_WITH_NAME_IN_SCOPE);
    }

    /**
     * this method returns a map with the scope methods
     * @return a map with the scope methods
     */
    public Map<String, Method> getScopeMethods() {
        return scopeMethods;
    }

    /**
     * this method gets method name and returns the method with this name
     * @param methodName - a method name
     * @return - the method object
     * @throws ScopeException - if there is no method in the scope with this name, it throws exception
     */
    public Method getMethodByName(String methodName) throws ScopeException {
        if (!scopeMethods.containsKey(methodName)){
            throw new ScopeException(NO_METHOD_WITH_NAME_IN_SCOPE);
        }
        return scopeMethods.get(methodName);
    }

    /**
     * this method adds new method to the scope methods map
     * @param method - a given method
     * @throws ScopeException - if the method already exists in the scope, it trows exception
     */
    public void addNewMethod(Method method) throws ScopeException {
        if (scopeMethods.containsKey(method.getName())){
            throw new ScopeException(METHOD_EXISTS);
        }
        scopeMethods.put(method.getName(), method);
    }
}
