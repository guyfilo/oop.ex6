package oop.ex6.jacasvariable;

public class VariableRegex {
    private final static String INT_VAR_REGEX =
            "^ *+\\bint\\b ++([a-zA-Z]\\w*|_\\w++) ++= ++\\d+ *+; *+$";
    private final static String STRING_VAR_REGEX =
            "^ *+\\bString\\b ++([a-zA-Z]\\w*|_\\w++) ++={1} ++\"{1}\\N*\"{1} *+;{1} *+$";
    private final static String CHAR_VAR_REGEX =
            "^ *+\\bchar\\b ++([a-zA-Z]\\w*|_\\w++) ++={1} ++'{1}\\X{0,1}'{1} *+;{1} *+$";
    private final static String DOUBLE_VAR_REGEX =
            "^ *+\\bdouble\\b ++([a-zA-Z]\\w*|_\\w++) ++={1} ++\\d+.{0,1}\\d*+ *+;{1} *+$";
    private final static String BOOLEAN_VAR_REGEX =
            "^ *+\\bboolean\\b ++([a-zA-Z]\\w*+|_\\w++) ++={1} ++(?:\\btrue\\b|\\bfalse\\b|\\d+.{0,1}\\d*+) *+;{1} *+$";
}
