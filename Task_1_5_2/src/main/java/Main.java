import jogamp.common.Debug;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) throws ParseException, IOException {
        Options options = new Options();
        Option add = new Option("add", "add note");
        add.setArgs(2);
        options.addOption(add);
        Option rm = new Option("rm", "remove note");
        rm.setArgs(1);
        options.addOption(rm);
        Option show = new Option("show","show notes");
        options.addOption(show);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        Notebook notebook = new Notebook("notebook.json");

        if (cmd.hasOption("add")) {
            var arguments = cmd.getOptionValues("add");
            notebook.add(arguments[0], arguments[1]);
        } else if (cmd.hasOption("rm")) {
            notebook.remove(cmd.getOptionValue("rm"));
        } else if (cmd.hasOption("show")) {
            String[] arguments = cmd.getArgs();
            if (arguments.length == 0) {
                notebook.show();
            } else {
                String[] keywords = Arrays.copyOfRange(arguments, 2, arguments.length);
                notebook.show(LocalDateTime.parse(arguments[0]), LocalDateTime.parse(arguments[1]), keywords);
            }
        }
    }
}