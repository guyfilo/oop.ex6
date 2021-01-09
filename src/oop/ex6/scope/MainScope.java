package oop.ex6.scope;

import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;
import oop.ex6.method.Method;
import oop.ex6.method.MethodException;
import oop.ex6.method.MethodFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MainScope extends Scope {
    private final Map<String, Method> programMethods = new HashMap<>();
    private LineNumberReader fileReader;
    private String line;
    private final static String COMMENT_LINE_REGEX = "^\\/\\/";
    private final static String VAR_DECLARATION_REGEX = "^[^;]*+;\\s*+$";
    private final static String NEW_SCOPE_REGEX = "^[^{]*+\\{\\s*+$";
    private final static String END_SCOPE_REGEX = "^[^}]*+}\\s*+$";
    private final static String EMPTY_LINE_REGEX = "^\\s*+$";
    private final static String REGULAR_WORD_SEPARATED = "\\b\\w++\\b";

    private final static String SCOPE_NOT_CLOSE_MSG = "} expected";
    private final static String ALREADY_EXIST_METHOD_MSG = "method named %s already exist";


    /**
     * crates file reader
     * @param sourceFilePath
     * @throws FileNotFoundException
     */
    public MainScope(String sourceFilePath) throws FileNotFoundException {
        fileReader = new LineNumberReader(new FileReader(new File(sourceFilePath)));
    }

    /**
     *
     * @throws IOException
     * @throws GeneralException
     */
    public void getGlobalMembers() throws IOException, GeneralException {
        line = fileReader.readLine();
        while (line != null){
            if (line.matches(COMMENT_LINE_REGEX) || line.matches(EMPTY_LINE_REGEX)){
                line = fileReader.readLine();
                continue;
            }
            if (line.matches(VAR_DECLARATION_REGEX)){
                LineParser.varLineCheck(line, this);
            }
            if (line.matches(NEW_SCOPE_REGEX)){
                makeNewMethod();
            } // todo: else trow an exception
        }
    }

    private void makeNewMethod() throws GeneralException, IOException {
        Method newMethod = MethodFactory.createMethod(line);
        int bracketsBalance = 1;
        line = fileReader.readLine();
        while (bracketsBalance != 0 && line != null){
            newMethod.addLine(line);
            if (line.matches(NEW_SCOPE_REGEX)){
                bracketsBalance++;
            } else if (line.matches(END_SCOPE_REGEX)){
                bracketsBalance--;
            }
            line = fileReader.readLine();
        }
        if (bracketsBalance != 0 || !newMethod.isEndWithReturn()) {
            throw new MethodException(SCOPE_NOT_CLOSE_MSG);
        }
        addNewMethod(newMethod);
    }

    @Override
    public boolean isVariableInScope(String variableName) {
        return getScopeVariables().containsKey(variableName);
    }

    @Override
    public Variable getScopeVariableByName(String variableName) {
        return getScopeVariables().get(variableName);
    }

}
