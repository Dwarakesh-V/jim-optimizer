package optimizer.passes;

import soot.*;
import soot.jimple.*;
import soot.toolkits.graph.*;

import java.util.*;

public class LoopInvariantCodeMotion {

    public void transform(Body body) {

        ExceptionalUnitGraph graph =
                new ExceptionalUnitGraph(body);

        for (Unit u : body.getUnits()) {

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
                            body.getUnits().getFirst()
                    );
                }
            }
        }
    }
}