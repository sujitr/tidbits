package com.sujit.exercise.RansomNote;

public class Smart {

    private String ransomNote, magazine;

    public Smart(String ransomNote, String magazine) {
        this.ransomNote = ransomNote;
        this.magazine = magazine;
    }

    public boolean canConstruct() {
        if (magazine.length() < ransomNote.length()) return false;
        int caps[] = new int[26];
        for (char c : ransomNote.toCharArray()) {
            int index = magazine.indexOf(c, caps[c - 'a']);
            if (index == -1)
                return false;
            caps[c - 97] = index + 1;
        }
        return true;
    }
}
