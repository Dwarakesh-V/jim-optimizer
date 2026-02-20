package optimizer;

import soot.*;
import soot.BodyTransformer;

import java.util.Map;

import optimizer.passes.*;

public class OptimizerPipeline extends BodyTransformer {

    @Override
    protected void internalTransform(Body body,
                                     String phase,
                                     Map<String, String> options) {

        new FunctionInlining().transform(body);
        new ConstantPropagation().transform(body);
        new ConstantFolding().transform(body);
        new CopyPropagation().transform(body);
        new CommonSubexpressionElimination().transform(body);
        new LoopInvariantCodeMotion().transform(body);
        new StrengthReduction().transform(body);
        new InductionVariableElimination().transform(body);
        new LoopPeeling().transform(body);
        new LoopUnrolling().transform(body);
        new LoopFusion().transform(body);
        new DeadCodeElimination().transform(body);
        new UnreachableCodeElimination().transform(body);
    }
}