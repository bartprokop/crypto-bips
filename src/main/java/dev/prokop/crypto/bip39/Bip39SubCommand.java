package dev.prokop.crypto.bip39;

import picocli.CommandLine;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "bip39",
        mixinStandardHelpOptions = true
)
public class Bip39SubCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-l", "--length"}, description = "seed size, e.g. 128, 160, 192, 224, 256", defaultValue = "128")
    int seed_size;

    @Override
    public Integer call() throws Exception {
        if (seed_size%8!=0) {
            return -1;
        }

        final byte[] seed = new byte[seed_size/8];
        new SecureRandom().nextBytes(seed);
        String[] generateMnemonic = Bip39Mnemonic.generateMnemonic(seed);
        System.out.print(generateMnemonic[0]);
        for (int i=1; i<generateMnemonic.length; i++)
            System.out.print(" " + generateMnemonic[i]);
        System.out.println();
        return 0;
    }
}
