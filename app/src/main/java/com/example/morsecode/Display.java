package com.example.morsecode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Display extends AppCompatActivity {
    EditText input;
    TextView output;
    Morse m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        m = new Morse();
        input = findViewById(R.id.editText2);
        output = findViewById(R.id.textView3);
        Button decrypt = findViewById(R.id.button4);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tx = input.getText().toString();
                if (tx.startsWith("Hey, put this text into MorseCode app:\n")){

                    translate(tx.split("Hey, put this text into MorseCode app:\n")[1]);
                }
                else{
                    translate(tx);
                }

            }
        });

        Button close = findViewById(R.id.button5);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void translate(String x){
        String[] letters = x.split(" ");
        String word = "";
        for(int i=0;i<letters.length;i++){
            word+=m.fromMorse(letters[i]);
        }
        output.setText(word);
    }

}
