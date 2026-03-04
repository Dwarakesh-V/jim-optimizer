package optimizer;

import soot.*;
import soot.options.Options;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        System.out.println("-> Starting Jimple Optimizer");

        String inputDir = "target/input-classes";
        String outputDir = "sootOutput";

        if (args.length > 0) {
            inputDir = args[0];
        }

        if (args.length > 1) {
            outputDir = args[1];
        }

        G.reset();

        Options.v().set_prepend_classpath(true);
        Options.v().set_process_dir(Collections.singletonList(inputDir));
        Options.v().set_output_format(Options.output_format_jimple);
        Options.v().set_allow_phantom_refs(true);

        Options.v().set_output_dir(outputDir);

        // keep this false unless doing interprocedural analysis
        Options.v().set_whole_program(false);

        Scene.v().loadNecessaryClasses();

        System.out.println("-> Registering optimization pipeline...");

        PackManager.v()
                .getPack("jtp")
                .add(new Transform("jtp.pipeline",
                        new OptimizerPipeline()));

        System.out.println("-> Running optimizer passes...");

        PackManager.v().runPacks();

        for (SootClass c : Scene.v().getApplicationClasses()) {

            System.out.println("\n===== CLASS: " + c.getName() + " =====");

            for (SootMethod m : c.getMethods()) {

                if (m.isConcrete()) {

                    Body body = m.retrieveActiveBody();

                    System.out.println("\nMETHOD: " + m.getSignature());

                    for (Unit u : body.getUnits()) {
                        System.out.println(u);
                    }
                }
            }
        }

        System.out.println("-> Output written to: " + outputDir);
        System.out.println("-> Done.");
    }
}