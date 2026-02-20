package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class LoopUnrolling {

    public void transform(Body body) {

        List<Unit> copy =
                new ArrayList<>(body.getUnits());

        for (Unit u : copy) {

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;

                if (stmt.getRightOp() instanceof IntConstant) {

                    // Simple duplication example
                    body.getUnits().insertAfter(
                            (Unit) stmt.clone(), u);
                }
            }
        }
    }
}