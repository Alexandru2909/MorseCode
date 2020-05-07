package com.example.morsecode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Morse morse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("LOL");
        morse = new Morse();
        String y = "hello guy";
        showText(y);
    }

    private final void showLetter(char letter){
        if (letter != ' ') {
            TextView letterView = findViewById(R.id.textView);
            TextView codeView = findViewById(R.id.textView2);
            System.out.println("HERE");
            System.out.println(letter);
            letterView.setText(String.valueOf(letter));
            codeView.setText(morse.translate(letter));
        }
    }

    private void showText(String txt){

        final String t = txt;
        final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                int i=0;
                @Override
                public void run() {
                    showLetter(t.charAt(i));
                    i++;
                    if ( i != t.length())
                        handler.postDelayed(this,900);
                }
            }, 100);
    }
}
