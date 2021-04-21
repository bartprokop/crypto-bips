package dev.prokop.crypto.bip39;

import dev.prokop.utils.HexUtils;
import dev.prokop.utils.ResourceUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.MnemonicUtils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Bip39MnemonicTest {

    @Test
    void generateMnemonic() {
        final String vectorsResource = ResourceUtils.getResourceFileAsString("dev/prokop/crypto/bip39/vectors.json");
        final JSONObject vectors = new JSONObject(vectorsResource);
        final JSONArray english = vectors.getJSONArray("english");

        for (int i=0; i<english.length(); i++) {
            final JSONArray vector = english.getJSONArray(i);
            final byte[] entropy = HexUtils.parseHex(vector.getString(0));
            System.out.println("entropy: " + Arrays.toString(entropy));
            final String[] generatedWords = Bip39Mnemonic.generateMnemonic(entropy);
            System.out.println("words:   " + Arrays.toString(generatedWords));
            assertArrayEquals(vector.getString(1).split(" "), generatedWords);

            final String collected = Stream.of(generatedWords).collect(Collectors.joining(" "));
            final byte[] seed = Bip39Mnemonic.PBKDF2(collected, "TREZOR");
            final String hexSeed = HexUtils.hex(seed);
            assertEquals(vector.getString(2), hexSeed.toLowerCase());
        }
    }

    @Test
    void name() {
        // Table 5-1. Mnemonic codes: entropy and word length from Mastering Ethereum Book
        assertEquals(12, Bip39Mnemonic.generateMnemonic(new byte[128 / 8]).length);
        assertEquals(15, Bip39Mnemonic.generateMnemonic(new byte[160 / 8]).length);;
        assertEquals(18, Bip39Mnemonic.generateMnemonic(new byte[192 / 8]).length);;
        assertEquals(21, Bip39Mnemonic.generateMnemonic(new byte[224 / 8]).length);;
        assertEquals(24, Bip39Mnemonic.generateMnemonic(new byte[256 / 8]).length);;
    }

    @Test
    void competition() {
        final SecureRandom secureRandom = new SecureRandom();
        for(int i=0; i<1000; i++) {
            byte[] seed = new byte[32];
            secureRandom.nextBytes(seed);
            String a = Arrays.toString(Bip39Mnemonic.generateMnemonic(seed));
            a = a.replaceAll(",", "");
            a = a.substring(1, a.length()-1);
            System.out.println(a);
            assertEquals(MnemonicUtils.generateMnemonic(seed), a);
        }
    }
}