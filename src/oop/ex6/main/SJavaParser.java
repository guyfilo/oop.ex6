package oop.ex6.main;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.jacasvariable.VariableFactory;
import oop.ex6.method.Method;
import oop.ex6.method.MethodException;
import oop.ex6.method.MethodFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SJavaParser {
    private final Map<String, Variable> globalVariables = new HashMap<>();
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



    public SJavaParser(String sourceFilePath) throws FileNotFoundException {
        fileReader = new LineNumberReader(new FileReader(new File(sourceFilePath)));
    }

    public void getGlobalMethodsVariables() throws IOException, MethodException {
        line = fileReader.readLine();
        while (line != null){
            if (line.matches(COMMENT_LINE_REGEX) || line.matches(EMPTY_LINE_REGEX)){
                line = fileReader.readLine();
                continue;
            }
            if (line.matches(VAR_DECLARATION_REGEX)){
                VarLine(globalVariables, line);
            }
            if (line.matches(NEW_SCOPE_REGEX)){
                makeNewMethod();
            }
        }
    }

    private void VarLine(Map<String, Variable> scopeVariables, String varLine) throws MethodException {
        Matcher wordsInLine = Pattern.compile(REGULAR_WORD_SEPARATED).matcher(varLine);
        if (wordsInLine.find()){
            String firstWordInLine = line.substring(wordsInLine.start(), wordsInLine.end());
            if (scopeVariables.containsKey(firstWordInLine)){
                changeExistVar(scopeVariables.get(firstWordInLine), varLine);
                return;
            }
            if (VariableFactory.checkValidType(firstWordInLine)){
                declareNewVar(varLine, scopeVariables);
                return;
            }
        }
        throw new MethodException("invalid var line");
    }

    private void changeExistVar(Variable varToChange, String varLine){

    }

    private void declareNewVar(String varLine, Map<String, Variable> scopeVariables){

    }

    private void makeNewMethod() throws MethodException, IOException {
        Method newMethod = MethodFactory.createMethod(line);
        int braceBalance = 1;
        line = fileReader.readLine();
        while (braceBalance != 0 && line != null){
            newMethod.addLine(line);
            if (line.matches(NEW_SCOPE_REGEX)){
                braceBalance++;
            } else if (line.matches(END_SCOPE_REGEX)){
                braceBalance--;
            }
            line = fileReader.readLine();
        }
        if (braceBalance != 0) {
            throw new MethodException(SCOPE_NOT_CLOSE_MSG);
        }
        if (programMethods.containsKey(newMethod.getName())){
            throw new MethodException(String.format(ALREADY_EXIST_METHOD_MSG, newMethod.getName()));
        }
        programMethods.put(newMethod.getName(), newMethod);
    }


}
