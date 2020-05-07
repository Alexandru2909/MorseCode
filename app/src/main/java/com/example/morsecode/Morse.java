package com.example.morsecode;

import java.util.ArrayList;
import java.util.HashMap;

public class Morse {
    private HashMap<String,Character> dictionary;
    private String [] morseCodes= {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....",
            "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
            "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
            "-.--", "--.."};
    Morse(){
        System.out.print("HA");
        Character letter = 'a';
        this.dictionary = new HashMap<String,Character>();
        for(int i =0;i<morseCodes.length;i++){
            this.dictionary.put(morseCodes[i],letter);
            letter++;
        }
    }
    public String translate(char letter){
        int ind = letter-'a';
        return morseCodes[ind];
    }
    public char translate(String str){
        return this.dictionary.get(str);
    }
}
