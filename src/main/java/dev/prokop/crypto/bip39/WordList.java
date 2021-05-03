package dev.prokop.crypto.bip39;

import dev.prokop.utils.ResourceUtils;

import java.util.Collections;
import java.util.List;

public class WordList {

    private static final List<String> wordList;

    static {
        final List<String> stringList =
                ResourceUtils.getResourceFileAsListOfStrings("dev/prokop/crypto/bip39/english.txt");
        wordList = Collections.unmodifiableList(stringList);
    }

    public static List<String> english() {
        return wordList;
    }

    public static boolean isValidWord(String word) {
        return wordList.contains(word);
    }

    public static boolean isValidMnemonic(String[] mnemonic) {
        for (String word : mnemonic) {
            if (!isValidWord(word))
                return false;
        }
        return true;
    }

}
