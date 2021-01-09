//______________________________________PACKAGE_____________________________________________________________//
package oop.ex6.scope;
//______________________________________IMPORTS_____________________________________________________________//
import oop.ex6.GeneralException;
import oop.ex6.jacasvariable.Variable;
import oop.ex6.main.LineParser;
import oop.ex6.method.Method;
import java.util.ArrayList;
import java.util.Map;
//_______________________________________CLASS______________________________________________________________//

/**
 * this class creates an inner scope (method scope, if scope or while scope) object
 */
public class InnerScope extends Scope {

//*********************************** MAGIC NUMBERS ********************************************************//
    // error messages:
    private final String INVALID_INNER_SCOPE_LINE_ERR_MSG = "invalid line within inner scope";
    private final String SCOPE_END_INVALID_ERR_MSG = "Inner scope doesn't ends properly - '}' is missing";

    // others:
    private final int ZERO = 0;
//*********************************** DECELERATIONS ********************************************************//
    private final ArrayList<String> lines;
    private final int scopeFirstLineIdx;
//************************************* FUNCTIONS **********************************************************//

    /**
     * class constructor
     * @param lines - the scope inner lines
     * @param supScope - the sup scope
     * @param scopeFirstLineIdx - the index of the first scope line in the lines array
     */
    public InnerScope(ArrayList<String> lines, InnerScope supScope, int scopeFirstLineIdx){
        super(supScope);
        this.scopeFirstLineIdx = scopeFirstLineIdx;
        this.lines = lines;
    }

    /**
     * class constructor
     * @param lines - the scope inner lines
     * @param scopeMethods - the scope methods
     * @param scopeVariables - the scope variables
     */
    public InnerScope(ArrayList<String> lines, Map<String, Method> scopeMethods, Map<String,
            Variable> scopeVariables){
        super(scopeMethods, scopeVariables);
        this.lines = lines;
        this.scopeFirstLineIdx = ZERO;
    }

    /**
     * this method does trough all the scope line and checks line by line if the line is valid
     * @return - the index of the scope close bracket
     * @throws GeneralException - throws if a line is invalid line of the scope doesn't ends properly
     */
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
            } else if (!LineParser.isEmptyLine(line) && !LineParser.isCommentLine(line)){
                throw new InnerScopeException(INVALID_INNER_SCOPE_LINE_ERR_MSG);
            }
            curLineIdx ++;
        }
        throw new InnerScopeException(SCOPE_END_INVALID_ERR_MSG);
    }

}
