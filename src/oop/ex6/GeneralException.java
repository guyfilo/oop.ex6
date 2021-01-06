package oop.ex6;

public class GeneralException extends Exception{
    private static final long serialVersionID = 1L;
    private final static String GENERAL_MSG = "ERROR: ";

    public GeneralException(String msg){
        super(GENERAL_MSG + msg);
    }
}
