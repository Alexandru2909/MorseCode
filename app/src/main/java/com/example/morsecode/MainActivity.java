package com.example.morsecode;

import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Morse morse;
    CameraManager cameraManager;
    TextView letterView,codeView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("LOL");
        morse = new Morse();
        letterView = findViewById(R.id.textView);
        codeView = findViewById(R.id.textView2);
        cameraManager = (CameraManager) this.getSystemService(CAMERA_SERVICE);
        editText = (EditText) findViewById(R.id.editText);
        Button smsButton = findViewById(R.id.button3);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"));
                intent.putExtra("sms_body", "Hey, put this text into MorseCode app:\n"+morse.translate(editText.getText().toString()));
                startActivity(intent);
            }
        });
    }

    public void click(View v){
        showText(editText.getText().toString());
    }

    private final void clearText(){
        letterView.setText(null);
        codeView.setText(null);
    }

    public void display(View v){
        Intent intent = new Intent(this,Display.class);
        startActivity(intent);
    }

    private final void showLetter(char letter){
        if (letter != '/' && letter!=' ') {
            letterView.setText(String.valueOf(letter).toUpperCase());
            codeView.setText(morse.translate(letter));
        }
    }

    private void showText(String txt){
        final String t = morse.check(txt);
        final int [] times = morse.getTimes(t);
        final String code = morse.translate(t);
        for(int i = 0 ; i <times.length;i++)
            System.out.println("Time for "+ i + " is "+times[i]);
        System.out.println("Text = "+t);
        System.out.println("morse="+code);
        final Handler h1 = new Handler();
        final Handler h2 = new Handler();
        final Handler h3 = new Handler();
        Runnable r1 = new Runnable() {
            int i=0;
            @Override
            public void run(){
                if(i<t.length()){
                    showLetter(t.charAt(i));
                    h1.postDelayed(this,times[i]);
                    i++;
                }
            }
        };
        Runnable r2 = new Runnable() {
            int i=1;
            @Override
            public void run(){
                if(i<t.length()){
                    clearText();
                    try {
                        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    h2.postDelayed(this,times[i]);
                    i++;
                }
            }
        };
        Runnable r3 = new Runnable() {
            int i=0;
            int sleep = 0;

            @Override
            public void run(){
                try {
                    cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                if(i<code.length()){
                    try {
                        switch (code.charAt(i)) {
                            case '-':
                                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
                                sleep=1000;
                                break;
                            case '.':
                                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
                                sleep=500;
                                break;
                            case '/':
                                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                                sleep=1000;
                                break;
                            default:
                                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                                sleep=500;
                                break;
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    h3.postDelayed(this,sleep);
                    i++;
                }
            }
        };

        h1.post(r1);
        h2.postDelayed(r2,times[0]-500);
        h3.post(r3);


    }
}
