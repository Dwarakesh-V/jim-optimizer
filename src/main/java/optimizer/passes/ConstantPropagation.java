package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class ConstantPropagation {

    public void transform(Body body) {

        Map<Local, Integer> constants = new HashMap<>();

        for (Unit u : body.getUnits()) {

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;

                Value left = stmt.getLeftOp();
                Value right = stmt.getRightOp();

                if (right instanceof IntConstant) {
                    constants.put((Local) left,
                            ((IntConstant) right).value);
                }

                if (right instanceof Local) {
                    Local r = (Local) right;
                    if (constants.containsKey(r)) {
                        stmt.setRightOp(
                                IntConstant.v(constants.get(r)));
                    }
                }
            }
        }
    }
}