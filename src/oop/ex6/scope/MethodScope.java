package oop.ex6.scope;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.method.Method;

import java.util.Map;

public class MethodScope extends InnerScope{
    public MethodScope(String[] methodLines, Map<String, Variable> outerVariables, Map<String, Method> globalMethods, int scopeFirstLineIdx) {
        super(methodLines, outerVariables, globalMethods, scopeFirstLineIdx);
    }
}
