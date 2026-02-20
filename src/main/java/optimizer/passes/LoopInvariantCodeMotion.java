package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class LoopInvariantCodeMotion {

    public void transform(Body body) {

        Iterator<Unit> it = body.getUnits().snapshotIterator();

        while (it.hasNext()) {

            Unit u = it.next();

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;

                boolean invariant = true;

                for (ValueBox vb : stmt.getUseBoxes()) {
                    if (vb.getValue() instanceof Local) {
                        invariant = false;
                        break;
                    }
                }

                if (invariant) {
                    body.getUnits().remove(u);
                    body.getUnits().insertBefore(
                            u,
                            body.getUnits().getFirst());
                }
            }
        }
    }
}