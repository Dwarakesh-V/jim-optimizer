package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class DeadCodeElimination {

    public void transform(Body body) {

        Set<Value> used = new HashSet<>();

        for (Unit u : body.getUnits()) {
            for (ValueBox vb : u.getUseBoxes()) {
                used.add(vb.getValue());
            }
        }

        Iterator<Unit> it =
                body.getUnits().snapshotIterator();

        while (it.hasNext()) {

            Unit u = it.next();

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;

                if (!used.contains(stmt.getLeftOp())) {
                    body.getUnits().remove(u);
                }
            }
        }
    }
}