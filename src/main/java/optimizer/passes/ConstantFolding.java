package optimizer.passes;

import soot.*;
import soot.jimple.*;

public class ConstantFolding {

    public void transform(Body body) {

        for (Unit u : body.getUnits()) {

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;
                Value rhs = stmt.getRightOp();

                if (rhs instanceof BinopExpr) {

                    BinopExpr expr = (BinopExpr) rhs;

                    if (expr.getOp1() instanceof IntConstant &&
                        expr.getOp2() instanceof IntConstant) {

                        int v1 = ((IntConstant) expr.getOp1()).value;
                        int v2 = ((IntConstant) expr.getOp2()).value;

                        int result = v1 + v2;
                        stmt.setRightOp(IntConstant.v(result));
                    }
                }
            }
        }
    }
}