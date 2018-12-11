package com.lh.lhzkas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.lh.lhzkas.R;
import com.lh.lhzkas.utils.HttpServerUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        HttpServerUtil.getInstance().startServer();

    }


    @OnClick(R.id.btn_seting)
    public void btn_seting() {
        startActivity(new Intent(Settings.ACTION_SETTINGS));
//        MainActivity.this.finish();
//        Process.killProcess(Process.myPid());//杀死进程，防止dialog.show()出现错误

    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
