package com.sujit.trie;

/**
 * This class denotes internal data structure for trie.
 */
public class TrieNode {

    // numberOfNodes links to node children
    private TrieNode[] links;

    private final int numberOfNodes = 26; // targeting all lowercase letters only

    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[numberOfNodes];
    }

    public boolean containsKey(char ch) {
        return links[ch -'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch -'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch -'a'] = node;
    }
    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}
