package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class LoopUnrolling {

    public void transform(Body body) {

        Iterator<Unit> it = body.getUnits().snapshotIterator();

        while (it.hasNext()) {

            Unit u = it.next();

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;

                if (stmt.getRightOp() instanceof IntConstant) {

                    body.getUnits().insertAfter(
                            (Unit) stmt.clone(), u);
                }
            }
        }
    }
}