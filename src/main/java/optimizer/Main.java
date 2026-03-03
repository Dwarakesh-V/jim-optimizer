package optimizer;

import soot.*;
import soot.options.Options;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        System.out.println("-> Starting Jimple Optimizer");

        String inputDir = "target/input-classes";

        G.reset();

        Options.v().set_prepend_classpath(true);
        Options.v().set_process_dir(Collections.singletonList(inputDir));
        Options.v().set_output_format(Options.output_format_jimple);
        Options.v().set_allow_phantom_refs(true);

        // DO NOT enable whole_program for now
        Options.v().set_whole_program(false);

        Scene.v().loadNecessaryClasses();

        System.out.println("-> Registering optimization pipeline...");

        PackManager.v().getPack("jtp")
                .add(new Transform("jtp.pipeline",
                        new OptimizerPipeline()));

        System.out.println("-> Running optimizer passes...");

        PackManager.v().runPacks();
        PackManager.v().writeOutput();

        System.out.println("-> Done.");
    }
}