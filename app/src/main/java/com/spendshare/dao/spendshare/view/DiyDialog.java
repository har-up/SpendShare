package com.spendshare.dao.spendshare.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spendshare.dao.spendshare.R;

/**
 * Created by dao on 2018/4/8.
 */

public class DiyDialog extends Dialog {

    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;

    private DiyDialog mDiyDialog;

    private OnNoClickListener noOnclickListener;//取消按钮被点击了的监听器
    private OnYesClickLisener yesOnclickListener;//确定按钮被点击了的监听器

    public DiyDialog(@NonNull Context context) {
        super(context);
    }

    public DiyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DiyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_diy);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
    }

    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (messageStr != null) {
            messageTv.setText(messageStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        titleTv = (TextView) findViewById(R.id.title);
        messageTv = (TextView) findViewById(R.id.message);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yesOnclickListener.onYesClick();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noOnclickListener.onNoClick();
            }
        });
    }

    /**
     * 设置确定按钮的监听
     * @param onYesClickLisener
     */
    public void setYesOnclickListener(OnYesClickLisener onYesClickLisener){
        this.yesOnclickListener = onYesClickLisener;
    }

    public void setNoOnclickListener(OnNoClickListener onNoClickListener){
        this.noOnclickListener = onNoClickListener;
    }


    public interface OnNoClickListener{
        void onNoClick();
    }

    public interface OnYesClickLisener{
        void onYesClick();
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getMessageStr() {
        return messageStr;
    }

    public void setMessageStr(String messageStr) {
        this.messageStr = messageStr;
    }
}
