package com.spendshare.dao.spendshare.view;

import android.content.Context;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.spendshare.dao.spendshare.R;
import com.spendshare.dao.spendshare.view.iview.Ibar;

public class MyBar extends View implements Ibar {

    private Context mContext;
    private RadioGroup mBar;
    private int mIcon;
    private int mText;
    public MyBar(Context context) {
        this(context,null);
    }

    public MyBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        ViewGroup parent = (ViewGroup)this.getParent();
        mBar = (RadioGroup) LayoutInflater.from(mContext).inflate(R.layout.bar_item,parent,false);
    }

    @Override
    public View getView() {
        return mBar;

    }
}
