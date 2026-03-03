package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.Iterator;

public class AlgebraicSimplification {

    public void transform(Body body) {

        Iterator<Unit> it = body.getUnits().snapshotIterator();

        while (it.hasNext()) {

            Unit u = it.next();

            if (!(u instanceof AssignStmt))
                continue;

            AssignStmt stmt = (AssignStmt) u;
            Value rhs = stmt.getRightOp();

            if (!(rhs instanceof BinopExpr))
                continue;

            BinopExpr expr = (BinopExpr) rhs;

            Value op1 = expr.getOp1();
            Value op2 = expr.getOp2();

            // x = y + 0  or  x = 0 + y
            if (expr instanceof AddExpr) {
                if (op2 instanceof IntConstant &&
                        ((IntConstant) op2).value == 0) {
                    stmt.setRightOp(op1);
                }
                else if (op1 instanceof IntConstant &&
                        ((IntConstant) op1).value == 0) {
                    stmt.setRightOp(op2);
                }
            }

            // x = y - 0
            else if (expr instanceof SubExpr) {
                if (op2 instanceof IntConstant &&
                        ((IntConstant) op2).value == 0) {
                    stmt.setRightOp(op1);
                }
            }

            // x = y * 1  or  x = 1 * y
            else if (expr instanceof MulExpr) {
                if (op2 instanceof IntConstant &&
                        ((IntConstant) op2).value == 1) {
                    stmt.setRightOp(op1);
                }
                else if (op1 instanceof IntConstant &&
                        ((IntConstant) op1).value == 1) {
                    stmt.setRightOp(op2);
                }

                // x = y * 0  or  x = 0 * y
                else if (op2 instanceof IntConstant &&
                        ((IntConstant) op2).value == 0) {
                    stmt.setRightOp(IntConstant.v(0));
                }
                else if (op1 instanceof IntConstant &&
                        ((IntConstant) op1).value == 0) {
                    stmt.setRightOp(IntConstant.v(0));
                }
            }

            // x = y / 1
            else if (expr instanceof DivExpr) {
                if (op2 instanceof IntConstant &&
                        ((IntConstant) op2).value == 1) {
                    stmt.setRightOp(op1);
                }
            }
        }
    }
}