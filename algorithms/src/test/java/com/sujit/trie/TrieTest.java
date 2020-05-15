package com.sujit.trie;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrieTest {

    @Test
    public void testTrie(){
        Trie trie = new Trie();
        trie.insert("apple");
        assertTrue(trie.search("apple"));
        assertFalse(trie.search("app"));
        assertTrue(trie.startsWith("app"));
        trie.insert("app");
        assertTrue(trie.search("app"));

        trie.insert("lock");
        assertFalse(trie.search("locker"));
        assertTrue(trie.startsWith("loc"));
    }

}