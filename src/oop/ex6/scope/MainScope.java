package oop.ex6.scope;

import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Argument;
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
    private final static String SCOPE_NOT_CLOSE_MSG = "} expected";

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
                line = fileReader.readLine();
                continue;
            }
            if (line.matches(NEW_SCOPE_REGEX)){
                makeNewMethod();
                continue;
            }
            throw new ScopeException(String.format("line %d is an invalid global scope line", fileReader.getLineNumber()));
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

    private Map<String, Variable> getGlobalVariables() {
        Map<String, Variable> globalVariables = new HashMap<>();
        for (Variable globalVariable: getScopeVariables().values()){
            globalVariables.put(globalVariable.getName(), new Variable(globalVariable));
        }
        return globalVariables;
    }

    private InnerScope getMethodScope(Method method) throws ScopeException {
        InnerScope methodScope =
                new InnerScope(method.getMethodLines(), getScopeMethods(), getGlobalVariables());
        for (Argument arg: method.getArguments()){
            methodScope.addNewVar(new Variable(arg, methodScope));
        }
        return methodScope;
    }

    public void checkMethodsScopes() throws GeneralException {
        for (Method method: getScopeMethods().values()){
            InnerScope methodScope = getMethodScope(method);
            methodScope.checkValidScope();
        }
    }

}
