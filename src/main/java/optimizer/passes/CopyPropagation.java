package optimizer.passes;

import soot.*;
import soot.jimple.*;

import java.util.*;

public class CopyPropagation {

    public void transform(Body body) {

        Map<Value, Value> copies = new HashMap<>();

        for (Unit u : body.getUnits()) {

            if (u instanceof AssignStmt) {

                AssignStmt stmt = (AssignStmt) u;
                Value left = stmt.getLeftOp();
                Value right = stmt.getRightOp();

                if (right instanceof Local) {
                    copies.put(left, right);
                }

                for (ValueBox vb : stmt.getUseBoxes()) {
                    if (copies.containsKey(vb.getValue())) {
                        vb.setValue(copies.get(vb.getValue()));
                    }
                }
            }
        }
    }
}