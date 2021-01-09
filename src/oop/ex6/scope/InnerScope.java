package oop.ex6.scope;

import oop.ex6.GeneralException;
import oop.ex6.main.LineParser;


public class InnerScope extends Scope {
    private final String[] lines;
    private final int scopeFirstLineIdx;


    public InnerScope(String[] lines, InnerScope supScope, int scopeFirstLineIdx){
        super(supScope);
        this.scopeFirstLineIdx = scopeFirstLineIdx;
        this.lines = lines;
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
