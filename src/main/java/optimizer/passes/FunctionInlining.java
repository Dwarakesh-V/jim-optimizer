package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class FunctionInlining {

    public void transform(Body body) {

        Iterator<Unit> it =
                body.getUnits().snapshotIterator();

        while (it.hasNext()) {

            Unit u = it.next();

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;

                if (stmt.containsInvokeExpr()) {

                    InvokeExpr invoke =
                            stmt.getInvokeExpr();

                    SootMethod callee =
                            invoke.getMethod();

                    if (!callee.isConcrete())
                        continue;

                    Body calleeBody =
                            callee.retrieveActiveBody();

                    Body clone =
                            (Body) calleeBody.clone();

                    body.getUnits().insertBefore(
                            clone.getUnits(),
                            stmt
                    );

                    body.getUnits().remove(stmt);
                }
            }
        }
    }
}