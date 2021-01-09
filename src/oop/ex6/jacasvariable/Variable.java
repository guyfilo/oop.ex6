//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6.jacasvariable;

//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.method.MethodException;

import java.util.HashMap;
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

//************************************* FUNCTIONS **********************************************************//

    /**
     * this class is the variable contracture
     * @param name - the variable name
     * @param type - the variable type
     * @param isFinal
     * @param typeRecognizer
     * @param initialised
     */
    public Variable(String name, String type, boolean isFinal, Pattern typeRecognizer, boolean initialised) {
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
        this.typeRecognizer = typeRecognizer;
        this.initialised = initialised;
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

    public Pattern getTypeRecognizer() {
        return typeRecognizer;
    }

    public boolean isInitialised() {
        return initialised;
    }

    /**
     * this method checks if the variable can get a new value
     * @param newValue - a requested value for the variable
     * @return
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
     * this method checks if the variable can get a new value
     * @param otherVar - a requested value for the variable
     * @return
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

//todo: 2 versions - in one gets param and in the other string



}
