package oop.ex6.scope;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.method.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class Scope {
    private final Map<String, Variable> scopeVariables = new HashMap<>();
    private Map<String, Method> scopeMethods = new HashMap<>();

    public Scope(Map<String, Method> scopeMethods){
        this.scopeMethods = scopeMethods;
    }

    public void addNewVar(Variable newVariable) throws ScopeException {
        if (!scopeVariables.containsKey(newVariable.getName())){
           scopeVariables.put(newVariable.getName(), newVariable);
           return;
        }
        throw new ScopeException("try to declare variable that already declared in this scope");
    }

    public Map<String, Variable> getScopeVariables() {
        return scopeVariables;
    }

    public abstract boolean isVariableInScope(String variableName);

    public abstract Variable getScopeVariableByName(String variableName);

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
