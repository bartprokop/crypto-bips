package dev.prokop.crypto.bip32;

import dev.prokop.utils.HashUtils;
import dev.prokop.utils.HexUtils;

import java.util.Arrays;

public class Bip32MasterRawKey {

    private static final byte[] BITCOIN_SEED = "Bitcoin seed".getBytes();
    private final byte[] secretKey;
    private final byte[] chainCode;

    public static Bip32MasterRawKey fromSeed(byte[] seed) {
        final byte[] i = HashUtils.hmacSha512(BITCOIN_SEED, seed);
        final byte[] il = Arrays.copyOfRange(i, 0, 32);
        final byte[] ir = Arrays.copyOfRange(i, 32, 64);
        Arrays.fill(i, (byte) 0);

        return new Bip32MasterRawKey(il, ir);
    }

    private Bip32MasterRawKey(byte[] secretKey, byte[] chainCode) {
        this.secretKey = secretKey;
        this.chainCode = chainCode;
    }

    public byte[] getChainCode() {
        return chainCode;
    }

    public byte[] getSecretKey() {
        return secretKey;
    }

    public void wipe() {
        Arrays.fill(secretKey, (byte) 0);
        Arrays.fill(chainCode, (byte) 0);
    }

    @Override
    public String toString() {
        return "Bip32MasterRawKey{" +
                "secretKey=" + HexUtils.hex(secretKey) +
                ", chainCode=" + HexUtils.hex(chainCode) +
                '}';
    }

}
