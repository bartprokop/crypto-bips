package dev.prokop.crypto.bip39;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordListTest {

    @Test
    void getM() {
        final List<String> mnemonics = WordList.getM();
        assertEquals(2048, mnemonics.size());
        assertEquals("abandon", mnemonics.get(0));
        assertEquals("animal", mnemonics.get(72));
        assertEquals("cruel", mnemonics.get(420));
        assertEquals("media", mnemonics.get(1107));
        assertEquals("turkey", mnemonics.get(1877));
        assertEquals("zoo", mnemonics.get(2048-1));
    }

}