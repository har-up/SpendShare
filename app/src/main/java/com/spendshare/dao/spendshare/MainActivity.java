package com.spendshare.dao.spendshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.spendshare.dao.spendshare.view.DiyDialog;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private DiyDialog mDiyDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = (Button)findViewById(R.id.btn_show);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDiyDialog = new DiyDialog(MainActivity.this);
                mDiyDialog.setTitleStr("one");
                mDiyDialog.setMessageStr("towtwotwotwo");
                mDiyDialog.setYesOnclickListener(new DiyDialog.OnYesClickLisener() {
                    @Override
                    public void onYesClick() {
                        Toast.makeText(MainActivity.this,"点击了--确定--按钮",Toast.LENGTH_LONG).show();
                        mDiyDialog.dismiss();
                    }
                });
                mDiyDialog.setNoOnclickListener(new DiyDialog.OnNoClickListener() {
                    @Override
                    public void onNoClick() {
                        Toast.makeText(MainActivity.this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                        mDiyDialog.dismiss();
                    }
                });
                mDiyDialog.show();
            }
        });
    }
}
