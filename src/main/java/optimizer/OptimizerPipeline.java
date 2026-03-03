package optimizer;

import soot.*;
import soot.BodyTransformer;

import java.util.Map;

import optimizer.passes.*;

public class OptimizerPipeline extends BodyTransformer {

    private void printBody(Body body, String passName) {
        System.out.println("\n==============================");
        System.out.println("After: " + passName);
        System.out.println("==============================");
        System.out.println(body);
    }

    @Override
    protected void internalTransform(Body body,
                                     String phase,
                                     Map<String, String> options) {

        printBody(body, "Initial");

        new FunctionInlining().transform(body);
        printBody(body, "Function Inlining");

        new ConstantPropagation().transform(body);
        printBody(body, "Constant Propagation");

        new ConstantFolding().transform(body);
        printBody(body, "Constant Folding");

        new CopyPropagation().transform(body);
        printBody(body, "Copy Propagation");

        new CommonSubexpressionElimination().transform(body);
        printBody(body, "CSE");

        new LoopInvariantCodeMotion().transform(body);
        printBody(body, "LICM");

        new StrengthReduction().transform(body);
        printBody(body, "Strength Reduction");

        new InductionVariableElimination().transform(body);
        printBody(body, "Induction Variable Elimination");

        new LoopPeeling().transform(body);
        printBody(body, "Loop Peeling");

        new LoopUnrolling().transform(body);
        printBody(body, "Loop Unrolling");

        new LoopFusion().transform(body);
        printBody(body, "Loop Fusion");

        new DeadCodeElimination().transform(body);
        printBody(body, "Dead Code Elimination");

        new UnreachableCodeElimination().transform(body);
        printBody(body, "Unreachable Code Elimination");
    }
}