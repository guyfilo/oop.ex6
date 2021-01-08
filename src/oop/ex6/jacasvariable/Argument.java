package oop.ex6.jacasvariable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Argument extends Variable{
    public Argument(String name, String type, boolean isFinal, Pattern typeRecognizer) {
        super(name, type, isFinal, typeRecognizer, false);
    }


    public void checkValidValue(String demandedValue) throws VariableException {
        Matcher m = getTypeRecognizer().matcher(demandedValue);
        if (!m.matches()){
            throw new VariableException("try to initialise with invalid value");
        }
    }

    public void checkValidValue(Variable otherVar) throws VariableException {
        if(!this.getType().equals(otherVar.getType()) || !otherVar.isInitialised()){
            throw new VariableException("try to initialise with invalid value");
        }
    }
}
