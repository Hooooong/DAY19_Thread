package com.hooooong.threadbasic;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final int ACTION_SET = 999;

    Button button;

    Rotate rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);

        rotate = new Rotate(handler);

        rotate.start();
    }

    public void stop(View view){
        rotate.setStop();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ACTION_SET:
                    float curRot = button.getRotation();
                    button.setRotation(curRot + 6);
                    break;
            }
        }
    };
}

class Rotate extends Thread {

    public boolean RUNNING = true;
    Handler handler;


    public Rotate(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        while (RUNNING) {
            Message message = new Message();
            message.what = MainActivity.ACTION_SET;
            handler.sendMessage(message);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStop(){
        RUNNING = false;
    }
}
