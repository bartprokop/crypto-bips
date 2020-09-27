package dev.prokop.crypto.bip39;

import dev.prokop.utils.ResourceUtils;

import java.util.List;

public class WordList {

    public static List<String> getM() {
        return ResourceUtils.getResourceFileAsListOfStrings("dev/prokop/crypto/bip39/english.txt");
    }

}
