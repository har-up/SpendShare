package com.drong

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.widget.PopupWindowCompat
import android.support.v7.widget.AppCompatRadioButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RadioGroup
import android.widget.TextView
import com.example.dropmenu.R

/**
 * @Author: drong
 * @Date: 2019/3/7 15:59
 */
class DropMenuView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : RadioGroup(context, attrs) {
    private var mContext:Context
    private var mPopupWindow:PopupWindow? = null
    private var mBackground:Int
    private val mDisplayWidth:Int
    private val mDisplayHeight:Int
    private val menus:MutableList<View> = ArrayList()

    init {
        mContext = context!!
        var a = context.obtainStyledAttributes(attrs, R.styleable.DropMenuView)
        mBackground = a.getColor(R.styleable.DropMenuView_bg_color, Color.WHITE)
        var int = a.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_height,100)
        a.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_width,LinearLayout.LayoutParams.MATCH_PARENT)
        this.setBackgroundColor(mBackground)
        this.orientation = LinearLayout.HORIZONTAL
        a.recycle()

        val point = Point()
        (mContext as Activity).windowManager.defaultDisplay.getSize(point)
        mDisplayWidth = point.x
        mDisplayHeight = point.y

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
        val menu = LayoutInflater.from(mContext).inflate(R.layout.item_menu,this,false) as LinearLayout
        this.addView(menu)
        val radioButton:AppCompatRadioButton = menu.findViewById(R.id.menu_tb)
        radioButton.text = menuTitle
        radioButton.textSize = 20f
        radioButton.setOnClickListener{
            setPopWindowContentView(createListOneView(datas),menu)
            menu.isActivated = true
        }
        menu.setOnClickListener{
            radioButton.callOnClick()
        }
        menus.add(menu)
    }

    private fun setPopWindowContentView(contentView : View, anchor:View) {
        if (mPopupWindow == null){
            mPopupWindow = PopupWindow(mContext)
            mPopupWindow?.width = mDisplayWidth
            mPopupWindow?.isFocusable = false
            mPopupWindow?.isOutsideTouchable = true
        }
        mPopupWindow?.contentView = contentView
        PopupWindowCompat.showAsDropDown(mPopupWindow,anchor,0,0,Gravity.TOP)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        var perRight = r/ menus.size
        for (i in 1..menus.size){
            menus[i-1].layout(perRight*(i-1),t,perRight*i,b)
        }
    }

    /**
     * 以及菜单view
     */
    fun createListOneView(datas:List<String>):View{
        var view =  LayoutInflater.from(mContext).inflate(R.layout.list_one_view, null, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.rv_one)
        recyclerView.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = MyAdapter(datas)
        return view
    }


      class MyAdapter(datas: List<String>) : RecyclerView.Adapter<MyHodler>() {
         val mDatas = datas

         override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHodler {
             return MyHodler(LayoutInflater.from(parent?.context).inflate(R.layout.item_list_one,null))
         }

         override fun getItemCount(): Int {
             return mDatas.size
         }

         override fun onBindViewHolder(holder: MyHodler?, position: Int) {
             holder?.apply {
                 textView.text = mDatas[position]
             }
         }

    }

    class MyHodler(itemView: View?) : RecyclerView.ViewHolder(itemView){
        var textView :TextView = itemView?.findViewById(R.id.tv_one)!!
    }

}
