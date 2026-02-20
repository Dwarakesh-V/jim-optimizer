package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class CommonSubexpressionElimination {

    public void transform(Body body) {

        Map<String, Local> exprMap = new HashMap<>();

        for (Unit u : body.getUnits()) {

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;
                Value rhs = stmt.getRightOp();

                if (rhs instanceof BinopExpr) {

                    String key = rhs.toString();

                    if (exprMap.containsKey(key)) {
                        stmt.setRightOp(exprMap.get(key));
                    } else {
                        if (stmt.getLeftOp() instanceof Local) {
                            exprMap.put(key,
                                    (Local) stmt.getLeftOp());
                        }
                    }
                }
            }
        }
    }
}