package dev.prokop.crypto.bip32;

import dev.prokop.crypto.bip39.MyEthereumWallet;
import dev.prokop.utils.HexUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Bip32Test {

    // https://bip32jp.github.io/english/
    private final static String SK = "92873ab239b4a1822a9edd633126760202855be759ef64099655e53596f5ee58";
    private final static String CC = "04c60c287af528f770e7201760c9bc3a92b887e3e3c1986b9816a6b51e3f52c3";

    @Test
    void masterKeyGeneration() {
        final byte[] realWorldSeed = MyEthereumWallet.realWorldSeed();
        System.out.println("Seed: " + HexUtils.hex(realWorldSeed));
        final Bip32MasterRawKey masterRawKey = Bip32MasterRawKey.fromSeed(realWorldSeed);
        System.out.println(masterRawKey);

        assertEquals(SK, HexUtils.hex(masterRawKey.getSecretKey()));
        assertEquals(CC, HexUtils.hex(masterRawKey.getChainCode()));
    }

}