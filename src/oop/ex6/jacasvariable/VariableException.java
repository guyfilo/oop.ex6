package oop.ex6.jacasvariable;

import oop.ex6.GeneralException;

public class VariableException extends GeneralException {
    private final static String DEFAULT_MSG = "cannot handle this variable";

    public VariableException(String msg) {
        super(msg);
    }

    public VariableException(){
        super(DEFAULT_MSG);
    }


}
