package oop.ex6.scope;

import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;
import oop.ex6.method.Method;

import java.util.Map;

public class InnerScope extends Scope {
    //todo: for each method - inner scope

    public String[] methodLines;
    public Map<String,Variable> outerVariables;
    public Map<String, Method> globalMethods;
    public int scopeFirstLineIdx;


    public InnerScope(String[] methodLines, Map<String,Variable> outerVariables, Map<String, Method> globalMethods, int scopeFirstLineIdx){
        this.methodLines = methodLines;
        this.outerVariables = outerVariables;
        this.globalMethods = globalMethods;
        this.scopeFirstLineIdx = scopeFirstLineIdx;

    }

    public int checkValidScope() throws InnerScopeException {
        int curLineIdx = this.scopeFirstLineIdx;
        while (curLineIdx < this.methodLines.length) {
            String line = this.methodLines[curLineIdx];
            // first check - close bracket
            if (LineParser.isCloseScopeLine(line)) {
                return curLineIdx;
            }
            // second check - line finishes with ";"
            else if (LineParser.isDeclerationLine(line)) {
                // todo: check valid decleration line
            }
            else if (LineParser.isNewScopeLine(line)) {
                InnerScope innerScope = new InnerScope(this.methodLines, this.outerVariables, this.globalMethods,
                        ++curLineIdx);
                curLineIdx = innerScope.checkValidScope();
            }
            else throw new InnerScopeException("Problem in line within inner scope");
            curLineIdx ++;
        }
        throw new InnerScopeException("Inner scope doesn't ends properly");
    }
}
