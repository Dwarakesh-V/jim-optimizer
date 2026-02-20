package optimizer;

import soot.*;
import soot.options.Options;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        String inputDir = "target/classes/input";

        Options.v().set_prepend_classpath(true);
        Options.v().set_process_dir(Collections.singletonList(inputDir));
        Options.v().set_output_format(Options.output_format_jimple);
        Options.v().set_whole_program(true);
        Options.v().set_allow_phantom_refs(true);

        PackManager.v().getPack("jtp")
                .add(new Transform("jtp.pipeline",
                        new OptimizerPipeline()));

        PackManager.v().runPacks();
        PackManager.v().writeOutput();
    }
}