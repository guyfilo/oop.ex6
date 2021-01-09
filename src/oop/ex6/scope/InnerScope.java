package oop.ex6.scope;

import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;
import oop.ex6.method.Method;

import java.util.ArrayList;
import java.util.Map;


public class InnerScope extends Scope {
    private final ArrayList<String> lines;
    private final int scopeFirstLineIdx;


    public InnerScope(ArrayList<String> lines, InnerScope supScope, int scopeFirstLineIdx){
        super(supScope);
        this.scopeFirstLineIdx = scopeFirstLineIdx;
        this.lines = lines;
    }

    public InnerScope(ArrayList<String> lines, Map<String, Method> scopeMethods, Map<String,
            Variable> scopeVariables){
        super(scopeMethods, scopeVariables);
        this.lines = lines;
        this.scopeFirstLineIdx = 0;
    }

    public int checkValidScope() throws GeneralException {
        int curLineIdx = this.scopeFirstLineIdx;
        while (curLineIdx < this.lines.size()) {
            String line = this.lines.get(curLineIdx);
            // first check - close bracket
            if (LineParser.isCloseScopeLine(line)) {
                return curLineIdx;
            }
            // second check - line finishes with ";"
            else if (LineParser.isDeclerationLine(line)) {
                LineParser.checkDeclarationLine(line, this);
            }
            else if (LineParser.isNewScopeLine(line)) {
                LineParser.checkLoopTitle(line, this);
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
