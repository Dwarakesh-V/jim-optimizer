package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class LoopPeeling {

    public void transform(Body body) {
        // Very simple, conservative loop peeling: detect a backward
        // goto (a basic back-edge) and duplicate the first statement
        // of the loop body before the loop header. Only peel when the
        // first statement is an AssignStmt to avoid control-flow changes.

        List<Unit> units = new ArrayList<>();
        for (Unit u : body.getUnits())
            units.add(u);

        for (int i = 0; i < units.size(); i++) {
            Unit u = units.get(i);

            if (u instanceof GotoStmt) {
                Unit tgt = ((GotoStmt) u).getTarget();
                int tgtIdx = units.indexOf(tgt);

                if (tgtIdx >= 0 && tgtIdx < i) {
                    // found a back-edge; peel first statement if safe
                    Unit first = units.get(tgtIdx);

                    if (first instanceof AssignStmt) {
                        AssignStmt as = (AssignStmt) first;
                        AssignStmt clone = Jimple.v().newAssignStmt(as.getLeftOp(), as.getRightOp());

                        // insert clone before loop header
                        body.getUnits().insertBefore(clone, first);
                    }
                }
            }
        }
    }
}