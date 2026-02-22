package optimizer.passes;

import soot.*;
import java.util.*;

public class LoopFusion {

    public void transform(Body body) {
        // Simple, conservative fusion-like cleanup: remove immediately
        // duplicate consecutive units which often come from unoptimized
        // transformations and are safe to drop when textually identical.

        Iterator<Unit> it = body.getUnits().snapshotIterator();
        Unit prev = null;

        while (it.hasNext()) {
            Unit cur = it.next();

            if (prev != null) {
                if (prev.toString().equals(cur.toString())) {
                    body.getUnits().remove(cur);
                    // skip updating prev so we can catch runs of duplicates
                    continue;
                }
            }

            prev = cur;
        }
    }
}