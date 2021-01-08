package oop.ex6.method;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;

import java.util.ArrayList;

public class Method {
    private String name;
    private ArrayList<Variable> arguments;
    private ArrayList<Variable> localVariables;
    private ArrayList<String> methodLines;



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
}
