package dev.prokop.crypto.bip39;

import dev.prokop.utils.HexUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyEthereumWallet {

    private final static String[] mnemonic = new String[] {
            "donate", "february", "display", "wash",
            "joy", "vehicle", "level", "provide",
            "reform", "giggle", "science", "danger",
            "elegant", "cabin", "minor", "border",
            "tornado", "affair", "swallow", "guard",
            "board", "bag", "label", "equip"
    };

    private final static String bip39Seed = "ed89ff0aa6adc5f1f30ab2d6348abca0d13d20c90b37d23a0ac610c1e0d2fc453d4498da53a5228659c542c5118c8e2e5c7a962093dc13d47a712cef14bf7192";

    public static byte[] realWorldSeed() {
        final String combined = Bip39Mnemonic.combine(mnemonic);
        return Bip39Mnemonic.PBKDF2(combined, "");
    }

    @Test
    public void test() {
        assertTrue(WordList.isValidMnemonic(mnemonic));
        final String combined = Bip39Mnemonic.combine(mnemonic);
        System.out.println(">" + combined + "<");
        final byte[] bytes = Bip39Mnemonic.PBKDF2(combined, "");
        assertEquals(bip39Seed, HexUtils.hex(bytes));
        System.out.println(HexUtils.hex(bytes));
    }

}
