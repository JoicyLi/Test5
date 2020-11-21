package com.example.parameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String UPPER_NUM = "upper";
    EditText etNum;
    Button bn;
    CalThread calThread;
    String num = null;
    class CalThread extends Thread {
        public Handler mHandler;
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                @SuppressLint("HandlerLeak")
                public void handleMessage(Message msg) {
                    if(msg.what == 0x123) {
                        int upper = msg.getData().getInt(UPPER_NUM);
                        outer:
                        for(int i = 2; i <= Math.sqrt(upper); i++) {
                                if(upper % i==0) {
                                    Toast.makeText(MainActivity.this, etNum.getText().toString() + "不是素数", Toast.LENGTH_LONG).show();
                                }
                        }
                        Toast.makeText(MainActivity.this, etNum.getText().toString() + "是素数", Toast.LENGTH_LONG).show();

                    }

                }
            };
            Looper.loop();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNum = (EditText)findViewById(R.id.etNum);

        bn = (Button)findViewById(R.id.bn);
        bn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                cal();
            }
        });
        calThread = new CalThread();
        calThread.start();

    }

    public void cal() {
        Message msg = new Message();
        msg.what = 0x123;
        Bundle bundle = new Bundle();
        bundle.putInt(UPPER_NUM, Integer.parseInt(etNum.getText().toString()));
        msg.setData(bundle);
        calThread.mHandler.sendMessage(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cal_prime,
                menu);
        return true;
    }

}
