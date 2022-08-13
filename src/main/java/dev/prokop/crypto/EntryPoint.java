package dev.prokop.crypto;

import dev.prokop.crypto.bip39.Bip39SubCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "crypto-bips-1.0.2-standalone.jar",
        subcommands = {
                Bip39SubCommand.class
        },
        mixinStandardHelpOptions = true
)
public class EntryPoint  {

    public static void main(String... args) {
        final int exitCode = new CommandLine(new EntryPoint()).execute(args);
        System.exit(exitCode);
    }

}
