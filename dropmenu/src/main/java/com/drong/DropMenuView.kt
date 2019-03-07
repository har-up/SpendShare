package com.drong

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ToggleButton
import com.example.dropmenu.R

/**
 * @Author: drong
 * @Date: 2019/3/7 15:59
 */
class DropMenuView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var mContext:Context
    private var mBackground:Int

    init {
        mContext = context!!
        var a = context.obtainStyledAttributes(attrs, R.styleable.DropMenuView)
        mBackground = a.getColor(R.styleable.DropMenuView_bg_color, Color.WHITE)
        var int = a.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_height,100)
        a.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_width,LinearLayout.LayoutParams.MATCH_PARENT)
        this.setBackgroundColor(mBackground)
        a.recycle()
    }

    constructor(context: Context?) : this(context,null){
        DropMenuView(context, null)
        mContext = context!!
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : this(context, attrs, defStyleAttr)

    /**
     *  一级菜单
     */
    fun addMenu(menuTitle:String, datas:List<String>){
        val menu = LayoutInflater.from(mContext).inflate(R.layout.item_menu,null)
        val toggleButton = menu.findViewById<ToggleButton>(R.id.menu_tb)
        toggleButton.text = menuTitle
        toggleButton.textSize = 20f
        this.addView(menu)
    }


}
