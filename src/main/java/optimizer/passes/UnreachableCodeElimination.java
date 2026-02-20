package optimizer.passes;

import soot.*;
import soot.toolkits.graph.*;

import java.util.*;

public class UnreachableCodeElimination {

    public void transform(Body body) {

        ExceptionalUnitGraph graph =
                new ExceptionalUnitGraph(body);

        Set<Unit> reachable = new HashSet<>();
        Deque<Unit> stack = new ArrayDeque<>();

        Unit entry = body.getUnits().getFirst();
        stack.push(entry);

        while (!stack.isEmpty()) {
            Unit u = stack.pop();

            if (!reachable.add(u))
                continue;

            for (Unit succ : graph.getSuccsOf(u)) {
                stack.push(succ);
            }
        }

        Iterator<Unit> it = body.getUnits().snapshotIterator();

        while (it.hasNext()) {
            Unit u = it.next();
            if (!reachable.contains(u)) {
                body.getUnits().remove(u);
            }
        }
    }
}