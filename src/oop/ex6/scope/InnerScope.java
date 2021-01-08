package oop.ex6.scope;

import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;
import oop.ex6.method.Method;

import java.util.HashMap;
import java.util.Map;

public class InnerScope extends Scope {
    private final String[] lines;
    private final Map<String,Variable> supScopesVariables;
    private final int scopeFirstLineIdx;


    public InnerScope(String[] lines, InnerScope supScope, int scopeFirstLineIdx){
        super(supScope.getScopeMethods());
        this.scopeFirstLineIdx = scopeFirstLineIdx;
        this.lines = lines;
        supScopesVariables = new HashMap<>();
        supScopesVariables.putAll(supScope.getSupScopesVariables());
        supScopesVariables.putAll(supScope.getScopeVariables());
    }

    public InnerScope(String[] lines, MainScope mainScope){
        super(mainScope.getScopeMethods());
        this.scopeFirstLineIdx = 0;
        this.lines = lines;
        supScopesVariables = new HashMap<>();//todo: get global vars and not really change theme
    }

    public Map<String, Variable> getSupScopesVariables() {
        return supScopesVariables;
    }

    @Override
    public boolean isVariableInScope(String variableName) {
        return false;
    }

    @Override
    public Variable getScopeVariableByName(String variableName) {
        return null;
    }

    public int checkValidScope() throws GeneralException {
        int curLineIdx = this.scopeFirstLineIdx;
        while (curLineIdx < this.lines.length) {
            String line = this.lines[curLineIdx];
            // first check - close bracket
            if (LineParser.isCloseScopeLine(line)) {
                return curLineIdx;
            }
            // second check - line finishes with ";"
            else if (LineParser.isDeclerationLine(line)) {
                LineParser.checkDeclarationLine(line, this);
            }
            else if (LineParser.isNewScopeLine(line)) {
                LineParser.checkLoopLine(line, getScopeVariables());
                InnerScope innerScope = new InnerScope(this.lines, this,
                        ++curLineIdx);
                curLineIdx = innerScope.checkValidScope();
            }
            else throw new InnerScopeException("Problem in line within inner scope");
            curLineIdx ++;
        }
        throw new InnerScopeException("Inner scope doesn't ends properly");
    }

}
