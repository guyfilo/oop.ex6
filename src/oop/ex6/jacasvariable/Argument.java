//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6.jacasvariable;

//______________________________________IMPORTS_____________________________________________________________//

//_______________________________________CLASS______________________________________________________________//

/**
 * this method creates method argument object
 */
public class Argument extends Variable{


//*********************************** MAGIC NUMBERS ********************************************************//
    private final String INVALID_METHOD_ARG = "try to initialise with invalid value";
//*********************************** DECELERATIONS ********************************************************//

//************************************* FUNCTIONS **********************************************************//

    /**
     * the class constructor
     * @param name - the argument name
     * @param type - the argument type
     * @param isFinal - true if the argument is final, false - otherwise
     * @param typeRecognizer - a pattern that recognizes the argument type
     */
    public Argument(String name, String type, boolean isFinal, String typeRecognizer) {
        super(name, type, isFinal, typeRecognizer, false, null);
    }

    /**
     * this method checks if a given argument is an method valid argument
     * @param demandedValue - the demanded argument value
     * @throws VariableException - if the argument type is invalid
     */
    public void checkValidValue(String demandedValue) throws VariableException {
        if (!demandedValue.matches(getTypeRecognizer())){
            throw new VariableException("try to initialise with invalid value");
        }
    }

    /**
     * this method checks if a given variable is an method valid argument
     * @param otherVar - a variable
     * @throws VariableException - if the argument type is invalid
     */
    public void checkValidValue(Variable otherVar) throws VariableException {
        VariableFactory.checkValidOtherVar(getType(), otherVar);
    }
}
