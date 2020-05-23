package com.example.morsecode;

import java.util.ArrayList;
import java.util.HashMap;

public class Morse {
    private HashMap<String,Character> dictionary;
    private String [] morseCodes= {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....",
            "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
            "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
            "-.--", "--.."};
    private int unit;
    Morse(){
        unit = 300;
        Character letter = 'A';
        this.dictionary = new HashMap<String,Character>();
        for(int i =0;i<morseCodes.length;i++){
            this.dictionary.put(morseCodes[i],letter);
            letter++;
        }
    }
    public String translate(char letter){
        if(letter != ' ') {
            if (letter < 'a')
                return morseCodes[letter - 'A']+' ';
            return morseCodes[letter - 'a']+' ';
        }
        return "/ ";
    }

    public char fromMorse(String s){
        if (s.equals("/")){
            return ' ';
        }
        System.out.println("Trying to translate "+s);
        return dictionary.get(s);
    }

    public String translate(String str){
        String[] words = str.split("\\s+");
        String code = "";
        for (String w : words) {
            for(int i=0;i<w.length();i++) {
                code += translate(w.charAt(i));
            }
            code += "/ ";
        }
        return code;
    }
    public String check(String str){
        String[] words = str.split("\\s+");
        String code = "";
        for (String w : words) {
            for(int i=0;i<w.length();i++) {
                if ((w.charAt(i) <= 'z') && (w.charAt(i) >= 'a') || (w.charAt(i) <= 'Z') && (w.charAt(i) >= 'A'))
                    code += w.charAt(i);
            }
            code += " ";
        }
        return code;
    }

    public int[] getTimes(String s){
        int time;
        int [] timeVars;
        timeVars = new int[s.length()];
        String code;
        for(int i=0;i<s.length();i++){
            time = 0;
            if ((s.charAt(i) <= 'z') && (s.charAt(i) >= 'a') || (s.charAt(i) <= 'Z') && (s.charAt(i) >= 'A') || (s.charAt(i)==' ')) {
                code = translate(s.charAt(i));
                for (int j = 0; j < code.length(); j++) {
                    switch(code.charAt(j)){
                        case '.':
                            time+=2*unit;
                            break;
                        case '-':
                            time += 4*unit;
                            break;
                        case '/':
                            time+=2*unit;
                            break;
                        default:
                            time+= 2*unit;
                    }
                }
//            System.out.println("time:"+time+ " for letter " + s.charAt(i) + " with code= "+code);
            }
            timeVars[i]=time;
        }
        return  timeVars;
    }
}
