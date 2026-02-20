package optimizer.passes;

import soot.*;
import soot.jimple.*;

public class StrengthReduction {

    public void transform(Body body) {

        for (Unit u : body.getUnits()) {

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;

                if (stmt.getRightOp() instanceof MulExpr) {

                    MulExpr mul =
                            (MulExpr) stmt.getRightOp();

                    if (mul.getOp2() instanceof IntConstant) {

                        int value =
                                ((IntConstant) mul.getOp2()).value;

                        if (value == 2) {
                            stmt.setRightOp(
                                    Jimple.v().newAddExpr(
                                            mul.getOp1(),
                                            mul.getOp1()
                                    )
                            );
                        }
                    }
                }
            }
        }
    }
}