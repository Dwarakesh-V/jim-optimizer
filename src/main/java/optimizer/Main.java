package optimizer;

import soot.*;
import soot.options.Options;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        String inputDir = "target/input-classes";

        G.reset();

        System.out.println("-> Starting Jimple Optimizer");
        System.out.println("-> Input directory: " + inputDir);

        Options.v().set_prepend_classpath(true);
        Options.v().set_process_dir(Collections.singletonList(inputDir));
        Options.v().set_output_format(Options.output_format_jimple);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_whole_program(true);

        Options.v().set_main_class("Test");

        System.out.println("-> Loading classes...");
        Scene.v().loadNecessaryClasses();

        System.out.println("-> Registering optimization pipeline...");
        PackManager.v().getPack("jtp")
                .add(new Transform("jtp.pipeline",
                        new OptimizerPipeline()));

        System.out.println("-> Running optimizer passes...");
        PackManager.v().runPacks();

        System.out.println("-> Writing optimized Jimple...");
        PackManager.v().writeOutput();

        System.out.println("-> Optimization complete. Done.");
    }
}