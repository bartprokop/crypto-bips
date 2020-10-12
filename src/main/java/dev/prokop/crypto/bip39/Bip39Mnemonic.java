package dev.prokop.crypto.bip39;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import static dev.prokop.utils.HashUtils.sha256;

public class Bip39Mnemonic {

    private final SecureRandom secureRandom;

    public Bip39Mnemonic() {
        secureRandom = new SecureRandom();
    }

    public Bip39Mnemonic(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    public String[] generateMnemonic(final int size) {
        if (size != 16) throw new IllegalArgumentException("Only 16 bytes allowed.");

        final byte[] ent = new byte[size];
        secureRandom.nextBytes(ent);
        return generateMnemonic(ent);
    }

    public static String[] generateMnemonic(final byte[] entropy) {
        final byte[] hash = sha256(entropy); // Hash the Entropy value
        final int checksumLength = entropy.length*8/32;
        // Copy first 4 bits of Hash as Checksum
        boolean[] checksum = Arrays.copyOfRange(bytesToBits(hash), 0, checksumLength);
        // Add Checksum to the end of Entropy bits
        boolean[] ENT_CS = Arrays.copyOf(bytesToBits(entropy), bytesToBits(entropy).length + checksum.length);
        System.arraycopy(checksum, 0, ENT_CS, bytesToBits(entropy).length, checksum.length);

//        System.out.println(ENT_CS.length);
//        System.out.println(ENT_CS.length/8);
//        System.out.println(ENT_CS.length/11);

        final int noOfWords = ENT_CS.length/11;


        // Split ENT_CS into groups of 11 bits and creates String array for
        // mnemonicWords
        String[] mnemonicWords = new String[noOfWords];
        for (int i = 0; i < mnemonicWords.length; i++) {
            boolean[] numBits = Arrays.copyOfRange(ENT_CS, i * 11, i * 11 + 11);
            mnemonicWords[i] = WordList.english().get(bitsToInt(numBits));
        }
        return mnemonicWords;
    }

    // Returns bit representation of byte array
    public static boolean[] bytesToBits(byte[] data) {
        boolean[] bits = new boolean[data.length * 8];
        for (int i = 0; i < data.length; ++i)
            for (int j = 0; j < 8; ++j)
                bits[(i * 8) + j] = (data[i] & (1 << (7 - j))) != 0;
        return bits;
    }

    // Returns int value of a bit array
    public static int bitsToInt(boolean[] bits) {
        int n = 0, l = bits.length;
        for (int i = 0; i < l; ++i) {
            n = (n << 1) + (bits[i] ? 1 : 0);
        }
        return n;
    }

    public static byte[] PBKDF2(String mnemonic, String salt) {
        try {
            byte[] fixedSalt = ("mnemonic" + salt).getBytes(StandardCharsets.UTF_8);
            KeySpec spec = new PBEKeySpec(mnemonic.toCharArray(), fixedSalt, 2048, 512);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

}
