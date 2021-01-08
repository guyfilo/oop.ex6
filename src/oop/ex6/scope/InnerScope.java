package oop.ex6.scope;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.method.Method;

import java.util.Map;

public class InnerScope extends Scope {
    //todo: for each method - inner scope

    public InnerScope(String[] methodLines, Map<String,Variable> outerVariables, Map<String, Method> globalMethods, int scopeFirstLineIdx){
        //todo: constractor

    }

    public int checkValidScope(){
        //todo: check if method: line from kind ;,
        //todo: if new scop - check id the first line is valid - if so - new inner scope
        //todo: return the last scope idx line
    }
}
