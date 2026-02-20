package optimizer.passes;

import soot.*;
import soot.jimple.*;

public class InductionVariableElimination {

    public void transform(Body body) {

        for (Unit u : body.getUnits()) {

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;

                if (stmt.getRightOp() instanceof AddExpr) {

                    AddExpr add =
                            (AddExpr) stmt.getRightOp();

                    if (add.getOp1().equivTo(
                            stmt.getLeftOp())) {

                        // Simple induction detected
                        // Advanced elimination omitted
                    }
                }
            }
        }
    }
}