package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class FunctionCloning {

    public void transform(Body body) {
        // Very small, conservative "cloning" optimization: if the same
        // invoke expression (textually) is computed multiple times and
        // its result is stored into a local, reuse the earlier result by
        // replacing later invokes with a copy-from-the-first-local.

        Map<String, Local> seen = new HashMap<>();

        Iterator<Unit> it = body.getUnits().snapshotIterator();

        while (it.hasNext()) {
            Unit u = it.next();

            if (u instanceof AssignStmt) {
                AssignStmt as = (AssignStmt) u;
                Value rhs = as.getRightOp();

                if (rhs instanceof InvokeExpr && as.getLeftOp() instanceof Local) {
                    String key = rhs.toString();

                    if (seen.containsKey(key)) {
                        Local prev = seen.get(key);
                        Local curLeft = (Local) as.getLeftOp();

                        // replace this invoke with a copy from previous local
                        AssignStmt copy = Jimple.v().newAssignStmt(curLeft, prev);
                        body.getUnits().insertBefore(copy, as);
                        body.getUnits().remove(as);
                    } else {
                        seen.put(key, (Local) as.getLeftOp());
                    }
                }
            }
        }
    }
}