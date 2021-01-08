package oop.ex6.method;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;

import java.util.ArrayList;
import java.util.Map;

public class Method {
    private String name;
    private ArrayList<Variable> arguments;
    private final ArrayList<String> methodLines = new ArrayList<>();



    public Method(String name, ArrayList<Variable> arguments){
        this.name = name;
        this.arguments = arguments;
    }

    public void addLine(String line){
        methodLines.add(line);
    }

    public String getName(){
        return name;
    }

    public ArrayList<Variable> getArguments() {
        return arguments;
    }

    public boolean isEndWithReturn() throws MethodException {
        if (!LineParser.isReturnLine(methodLines.get(methodLines.size() - 2))){
            throw new MethodException("method does not contain return line");
        }
        return true;
    }

    public boolean checkMethodCall(String argsLine, Map<String, Variable> scopeVariables){
        return false;
    }
}
