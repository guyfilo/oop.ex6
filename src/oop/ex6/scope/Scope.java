package oop.ex6.scope;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.method.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class Scope {
    private final Map<String, Variable> scopeVariables;
    private final Map<String, Method> scopeMethods;

    public Scope(){
        scopeVariables = new HashMap<>();
        scopeMethods = new HashMap<>();
    }

    public Scope(Map<String, Method> scopeMethods, Map<String, Variable> scopeVariables){
        this.scopeMethods = scopeMethods;
        this.scopeVariables = scopeVariables;
    }

    public Scope(Scope otherScope){
        this.scopeMethods = otherScope.getScopeMethods();
        this.scopeVariables = new HashMap<>();
        this.scopeVariables.putAll(otherScope.scopeVariables);
    }

    public void addNewVar(Variable newVariable) throws ScopeException {
        if (!isVariableDeclaredInScope(newVariable.getName())){
           scopeVariables.put(newVariable.getName(), newVariable);
           return;
        }
        throw new ScopeException("try to declare variable that already declared in this scope");
    }

    public Map<String, Variable> getScopeVariables() {
        return scopeVariables;
    }

    public boolean isVariableDeclaredInScope(String variableName) throws ScopeException {
        return isVariableInScope(variableName) &&
                this.equals(getScopeVariableByName(variableName).getVarScope());
    }

    public boolean isVariableInScope(String variableName){
        return scopeVariables.containsKey(variableName);
    }

    public Variable getScopeVariableByName(String variableName) throws ScopeException {
        if (isVariableInScope(variableName)){
            return scopeVariables.get(variableName);
        }
        throw new ScopeException("this variable not exist in this scope");
    }

    public Map<String, Method> getScopeMethods() {
        return scopeMethods;
    }

    public Method getMethodByName(String methodName) throws ScopeException {
        if (!scopeMethods.containsKey(methodName)){
            throw new ScopeException("is method doesnt exist in the scope");
        }
        return scopeMethods.get(methodName);
    }

    public void addNewMethod(Method method) throws ScopeException {
        if (scopeMethods.containsKey(method.getName())){
            throw new ScopeException("method already exist in this scope");
        }
        scopeMethods.put(method.getName(), method);
    }
}
