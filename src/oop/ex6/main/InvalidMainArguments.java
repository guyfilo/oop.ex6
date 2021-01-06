package oop.ex6.main;

import oop.ex6.GeneralException;

public class InvalidMainArguments extends GeneralException {
    public InvalidMainArguments(String msg) {
        super(msg);
    }
}
