//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6.jacasvariable;

//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.scope.Scope;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//_______________________________________CLASS______________________________________________________________//
/**
 * this class creates a variable object
 */
public class Variable {

//*********************************** MAGIC NUMBERS ********************************************************//

//*********************************** DECELERATIONS ********************************************************//
    private final boolean isFinal;
    private final String type;
    private final String name;
    private boolean initialised;
    private final Pattern typeRecognizer;
    private final Scope varScope;

//************************************* FUNCTIONS **********************************************************//

    /**
     * this class is the variable contracture for creating a new variable object from sketch
     * @param name - the variable name
     * @param type - the variable type
     * @param isFinal - true - if the variable is final, false - otherwise
     * @param typeRecognizer - a Pattern object with regex suits to the variable type
     * @param initialised - true - if the variable is initialized, false - otherwise
     * @param varScope - the scope the variable belongs to
     */
    public Variable(String name, String type, boolean isFinal, Pattern typeRecognizer, boolean initialised, Scope varScope) {
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
        this.typeRecognizer = typeRecognizer;
        this.initialised = initialised;
        this.varScope = varScope;
    }

    /**
     * this class is the variable contracture for creating a new variable object with other variable
     * @param otherVariable - the variable that the current is initialised with
     */
    public Variable(Variable otherVariable){
        this.type = otherVariable.type;
        this.name = otherVariable.name;
        this.isFinal = otherVariable.isFinal;
        this.typeRecognizer = otherVariable.typeRecognizer;
        this.initialised = otherVariable.isInitialised();
        this.varScope = otherVariable.varScope;
    }

    /**
     * contractor for creating variables that are method arguments
     * @param argument - an argument object
     * @param scope - the scope the argument belongs to
     */
    public Variable(Argument argument, Scope scope){
        this.type = argument.getType();
        this.name = argument.getName();
        this.isFinal = argument.isFinal();
        this.typeRecognizer = argument.getTypeRecognizer();
        this.initialised = true;
        this.varScope = scope;
    }

    /**
     * this method return the variable name
     * @return - the variable name
     */
    public String getName(){
        return this.name;
    }

    /**
     * this method return the variable type
     * @return - the variable type
     */
    public String getType(){
        return this.type;
    }

    /**
     * this method return if the variable is final
     * @return - true - if its final, false - otherwise
     */
    public boolean isFinal(){
        return this.isFinal;
    }

    /**
     * this method return the Pattern with a regex that suits the variable type
     * @return the Pattern with a regex that suits the variable type
     */
    public Pattern getTypeRecognizer() {
        return typeRecognizer;
    }

    /**
     * this method return if the variable is initialized
     * @return - true - if its initialized, false - otherwise
     */
    public boolean isInitialised() {
        return initialised;
    }

    /**
     * this method checks if the variable can get a new given value, and is so it updated the value initialized
     * field to ture
     * @param newValue - a requested value for the variable
     * @return - true iff the variable can get a new value
     */
    public void changeValue(String newValue) throws VariableException {
        if (this.isFinal){
            throw new VariableException("trying to change final var");
        }
        Matcher matcher = this.typeRecognizer.matcher(newValue);
        if (!matcher.matches()) {
            throw new VariableException("invalid type");
        }
        this.initialised = true;
    }


    /**
     * this method checks if the variable can get a new value as the value of another parameter
     * @param otherVar - a requested value for the variable
     * @return - true iff the variable can get a new value
     */
    public void changeValue(Variable otherVar) throws VariableException {
        if (this.isFinal){
            throw new VariableException("trying to change final var");
        }
        Matcher matcher = this.typeRecognizer.matcher(otherVar.type);
        if (!matcher.matches()) {
            throw new VariableException("invalid type");
        }
        if (!otherVar.isInitialised()){
            throw new VariableException("try to initialise with unInitialised variable");
        }
        this.initialised = true;
    }

    /**
     * this method returns the scope that the variable is belongs to
     * @return the scope that the variable is belongs to
     */
    public Scope getVarScope() {
        return varScope;
    }



}
