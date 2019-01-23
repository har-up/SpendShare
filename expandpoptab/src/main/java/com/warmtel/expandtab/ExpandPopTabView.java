package com.warmtel.expandtab;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;
import com.example.expandpoptab.R;

import java.util.ArrayList;

public class ExpandPopTabView extends LinearLayout implements OnDismissListener {
    private ArrayList<RelativeLayout> mViewLists = new ArrayList<RelativeLayout>();
    private ToggleButton mSelectedToggleBtn;
    private PopupWindow mPopupWindow;
    private Context mContext;
    private int mDisplayWidth;
    private int mDisplayHeight;
    private int mSelectPosition;
    private int mTabPostion = -1; //记录TAB页号
    private int mToggleBtnBackground;
    private int mToggleBtnBackgroundColor;
    private int mToggleTextColor;
    private int mPopViewBackgroundColor;
    private float mToggleTextSize;

    public ExpandPopTabView(Context context) {
        super(context);
        init(context,null);
    }

    public ExpandPopTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.ExpandPopTabView);
            mToggleBtnBackground = a.getResourceId(R.styleable.ExpandPopTabView_tab_toggle_btn_bg, -1);
            mToggleBtnBackgroundColor = a.getColor(R.styleable.ExpandPopTabView_tab_toggle_btn_color, -1);
            mToggleTextColor = a.getColor(R.styleable.ExpandPopTabView_tab_toggle_btn_font_color,-1);
            mPopViewBackgroundColor = a.getColor(R.styleable.ExpandPopTabView_tab_pop_bg_color,-1);
            mToggleTextSize = a.getDimension(R.styleable.ExpandPopTabView_tab_toggle_btn_font_size, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(a != null) {
                a.recycle();
            }
        }
        mContext = context;
        Point point = new Point();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getSize(point);
        mDisplayWidth = point.x;
        mDisplayHeight = point.y;
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void addItemToExpandTab(String tabTitle, ViewGroup tabItemView) {
        ToggleButton tButton = (ToggleButton) LayoutInflater.from(mContext).inflate(R.layout.expand_tab_toggle_button, this, false);
        if(mToggleBtnBackground != -1){
            tButton.setBackgroundResource(mToggleBtnBackground);
        }
        if(mToggleBtnBackgroundColor != -1){
            tButton.setBackgroundColor(mToggleBtnBackgroundColor);
        }
        if(mToggleTextColor != -1){
            tButton.setTextColor(mToggleTextColor);
        }
        if(mToggleTextSize != -1){
            tButton.setTextSize(mToggleTextSize);
        }

        tButton.setText(tabTitle);
        tButton.setTag(++mTabPostion);
        tButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleButton tButton = (ToggleButton) view;
                if (mSelectedToggleBtn != null && mSelectedToggleBtn != tButton) {
                    mSelectedToggleBtn.setChecked(false);
                }
                mSelectedToggleBtn = tButton;
                mSelectPosition = (Integer) mSelectedToggleBtn.getTag();
                expandPopView();
            }
        });
        addView(tButton);

        RelativeLayout popContainerView = new RelativeLayout(mContext);

        if(mPopViewBackgroundColor != -1){
            popContainerView.setBackgroundColor(mPopViewBackgroundColor);
        }else{
            popContainerView.setBackgroundColor(Color.parseColor("#b0000000"));
        }
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (mDisplayHeight * 0.7));
        popContainerView.addView(tabItemView, rl);
        popContainerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onExpandPopView();
            }
        });

        mViewLists.add(popContainerView);
    }

    public void setToggleButtonText(String tabTitle){
        ToggleButton toggleButton = (ToggleButton) getChildAt(mSelectPosition);
        toggleButton.setText(tabTitle);
    }

    private void expandPopView() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mViewLists.get(mSelectPosition));
            mPopupWindow.setWidth(mDisplayWidth);
            mPopupWindow.setHeight(mPopupWindow.getMaxAvailableHeight(mSelectedToggleBtn));
            mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            mPopupWindow.setFocusable(false);
            mPopupWindow.setOutsideTouchable(true);
        }

        if (mSelectedToggleBtn.isChecked()) {
            if (!mPopupWindow.isShowing()) {
                showPopView();
            } else {
                mPopupWindow.setOnDismissListener(this);
                mPopupWindow.dismiss();
            }
        } else {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
        }
    }

    /**
     * 如果菜单成展开状态，则让菜单收回去
     * 注：Activty 销毁时 如果对话框没有关闭刚关闭
     */
    public boolean onExpandPopView() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            if (mSelectedToggleBtn != null) {
                mSelectedToggleBtn.setChecked(false);
            }
            return true;
        } else {
            return false;
        }
    }

    public void showPopView(){
        if (mPopupWindow.getContentView() != mViewLists.get(mSelectPosition)) {
            mPopupWindow.setContentView(mViewLists.get(mSelectPosition));
        }
//        mPopupWindow.showAsDropDown(mSelectedToggleBtn, 0, 0);
        PopupWindowCompat.setOverlapAnchor(mPopupWindow,false);
        PopupWindowCompat.showAsDropDown(mPopupWindow,mSelectedToggleBtn,20,20,Gravity.CENTER);
    }

    @Override
    public void onDismiss() {
        showPopView();
        mPopupWindow.setOnDismissListener(null);
    }

}
