package com.spendshare.dao.spendshare.view;

import android.animation.*;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import com.spendshare.dao.spendshare.R;
import com.spendshare.dao.spendshare.view.iview.Ibar;


public class TabBar extends LinearLayout {

    private Context mContext;
    private SelctedChangeListener listener;

    public TabBar(Context context) {
        this(context,null);
    }

    public TabBar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TabBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        LinearLayout.LayoutParams params = (LayoutParams) getLayoutParams();
        if (params == null){
            params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        }

        setLayoutParams(params);
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutDirection(LAYOUT_DIRECTION_RTL);
        setGravity(Gravity.CENTER);
        setPaddingRelative(0,0,0,0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabBar);
        int resources = a.getResourceId(R.styleable.TabBar_menu,0);
        a.recycle();
    }

//    public TabBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }



    /**
     *  添加bar
     * @param bar
     */
    public void addBar(Ibar bar){
        addView(bar.getView());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        if (childCount == 0){
            return;
        }
        if (childCount == 1){
            if (getChildAt(0) instanceof RadioGroup){
                RadioGroup radioGroup = (RadioGroup) getChildAt(0);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        AppCompatRadioButton view = group.findViewById(group.getCheckedRadioButtonId());
                        view.setBackgroundColor(Color.RED);
                        listener.onSelectedChange(view,view.getId());
                    }
                });
                int childNum = radioGroup.getChildCount();
                for (int i = 0; i < childNum; i++) {
                    if (radioGroup.getChildAt(i) instanceof AppCompatRadioButton){
                        final AppCompatRadioButton radioButton = (AppCompatRadioButton) radioGroup.getChildAt(i);
                        if (radioButton.isChecked()){
                            radioButton.setBackgroundColor(Color.RED);
                        }
                        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (!isChecked){
                                    buttonView.setBackgroundColor(Color.BLUE);
                                }
                            }
                        });
                    }
                }
            }
        }
        for (int i = 0; i < childCount; i++) {
        }
    }

    public void setOnSelectedChangeListener(SelctedChangeListener listener){
        this.listener = listener;
    }

    interface SelctedChangeListener{
        /**
         *  tab回调
         * @param view
         * @param viewId
         */
        void onSelectedChange(View view,int viewId);
    }
}
