package com.example.morsecode;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.Key;

public class MainActivity extends AppCompatActivity {
    Morse morse;
    CameraManager cameraManager;
    TextView letterView,codeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("LOL");
        morse = new Morse();
        letterView = findViewById(R.id.textView);
        codeView = findViewById(R.id.textView2);
        cameraManager = (CameraManager) this.getSystemService(CAMERA_SERVICE);



        final EditText edittext = (EditText) findViewById(R.id.editText);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    showText(edittext.getText().toString());
                    return true;
                }
                return false;
            }
        });



    }


    private final void clearText(){
        letterView.setText(null);
        codeView.setText(null);
    }

    private final void showLetter(char letter){
        letterView.setText(String.valueOf(letter).toUpperCase());
        codeView.setText(morse.translate(letter));
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
                    h1.postDelayed(this,times[i]-400);
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
                                sleep=800;
                                break;
                            case '.':
                                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
                                sleep=400;
                                break;
                            case '/':
                                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                                sleep=800;
                                break;
                            default:
                                cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                                sleep=400;
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
        h2.postDelayed(r2,times[0]-400);
        h3.post(r3);


    }
}
