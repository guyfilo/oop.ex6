package oop.ex6.scope;

import oop.ex6.GeneralException;



public class InnerScopeException extends GeneralException {
    private final static String DEFAULT_MSG = "problem in inner scope";

    public InnerScopeException(String msg) {
        super(msg);
    }

    public InnerScopeException(){
        super(DEFAULT_MSG);
    }


}