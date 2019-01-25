package com.lh.lhzkas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.lh.lhzkas.R;
import com.lh.lhzkas.utils.ELog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android_serialport_api.SerialPort;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_name)
    EditText login_name;
    @BindView(R.id.login_password)
    EditText login_password;
    private SerialPort serialPort1;
    private InputStream inputStream1;
    private boolean threadStatus1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        try {
            serialPort1 = new SerialPort(new File("/dev/ttyS1"), 9600, 0);
            //获取打开的串口中的输入输出流，以便于串口数据的收发
            inputStream1 = serialPort1.getInputStream();
//            outputStream1 = serialPort1.getOutputStream();
            threadStatus1 = true;
            new ReadThread().start(); //开始线程监控是否有数据要接收
        } catch (IOException e) {
            ELog.e("======open_ck=====打开串口异常");
            e.printStackTrace();
        }

    }


    /**
     * 单开一线程，来读数据
     */
    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            //判断进程是否在运行，更安全的结束进程
            byte[] buffer = new byte[1024];
            int size; //读取数据的大小
            try {
                while (threadStatus1 && (size = inputStream1.read(buffer, 0, 1024)) > 0) {
                    if (size > 0) {
                        ELog.i("=========run: 接收到了数据=======" + new String(buffer, 0, size));
                        ELog.i("=========run: 接收到了数据大小=====" + size);
                    }
                }

            } catch (IOException e) {
                ELog.i("=========run: 数据读取异常========" + e.toString());
            }
        }
    }


    @OnClick(R.id.login_btn)
    public void login_btn() {
        if (login_name.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (login_password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (login_name.getText().toString().trim().equals("admin") &&
                login_password.getText().toString().trim().equals("admin")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        threadStatus1 = false;
    }


}
