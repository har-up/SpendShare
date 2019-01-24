package com.spendshare.dao.spendshare.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.spendshare.dao.spendshare.R
import com.spendshare.dao.spendshare.adapter.BoxAdapter
import com.spendshare.dao.spendshare.model.Box
import com.spendshare.dao.spendshare.view.RecyclerViewDivider
import com.warmtel.expandtab.ExpandPopTabView
import com.warmtel.expandtab.KeyValueBean
import com.warmtel.expandtab.PopOneListView
import com.warmtel.expandtab.PopTwoListView
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class CheckFragment : Fragment() {

    private val mParentLists = java.util.ArrayList<KeyValueBean>()
    private val mChildrenListLists = java.util.ArrayList<java.util.ArrayList<KeyValueBean>>()
    private var mPriceLists: List<KeyValueBean>? = null
    private var mSortLists: List<KeyValueBean>? = null
    private var mFavorLists: List<KeyValueBean>? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_check, container, false)
        var recycleView = view?.findViewById<RecyclerView>(R.id.recycleView)
        var expandtab_view = view?.findViewById<ExpandPopTabView>(R.id.expandtab_view)

        setConfigsDatas()//加载数据

        recycleView?.layoutManager = LinearLayoutManager(context)
        var boxAdapter = BoxAdapter(getTaskDatas())
        boxAdapter.setOnItemClickListener { view, position, id ->
            Toast.makeText(context, getTaskDatas().get(position).boxAddress, Toast.LENGTH_SHORT).show()
        }
        recycleView?.adapter = boxAdapter
        recycleView?.addItemDecoration(RecyclerViewDivider(context, LinearLayoutManager.VERTICAL, 5, Color.GRAY))
        addItem(expandtab_view, mPriceLists, "", "状态")
        addItem(expandtab_view, mFavorLists, "默认", "所属区域")
//        addItem(expandtab_view, mParentLists, mChildrenListLists, "锦江区", "合江亭", "区域")

        return view
    }

    fun getTaskDatas(): ArrayList<Box> {
        var boxs: ArrayList<Box> = ArrayList()
        for (i in 0..10) {
            var box= Box()
            box.boxAddress = "testAddress"
            box.status = "已核查"
            box.checkManName = "张三-李四"
            box.checkDate = "1993/3/4"
            boxs.add(box)
        }
        return boxs
    }

    fun addItem(expandTabView: ExpandPopTabView?, lists: List<KeyValueBean>?, defaultSelect: String, defaultShowText: String) {
        val popOneListView = PopOneListView(context)
        popOneListView.setDefaultSelectByValue(defaultSelect)
        //popViewOne.setDefaultSelectByKey(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, object : PopOneListView.OnSelectListener {
            override fun getValue(key: String, value: String) {
                Log.e("tag", "key :$key ,value :$value")
            }
        })
        expandTabView?.addItemToExpandTab(defaultShowText, popOneListView)
    }

    fun addItem(expandTabView: ExpandPopTabView?, parentLists: List<KeyValueBean>?,
                childrenListLists: List<java.util.ArrayList<KeyValueBean>>, defaultParentSelect: String, defaultChildSelect: String, defaultShowText: String) {
        val popTwoListView = PopTwoListView(context)
        popTwoListView.setDefaultSelectByValue(defaultParentSelect, defaultChildSelect)
        //distanceView.setDefaultSelectByKey(defaultParent, defaultChild);
        popTwoListView.setCallBackAndData(expandTabView, parentLists, childrenListLists, object : PopTwoListView.OnSelectListener {
            override fun getValue(showText: String, parentKey: String, childrenKey: String) {
                Log.e("tag", "showText :$showText ,parentKey :$parentKey ,childrenKey :$childrenKey")
            }
        })
        expandTabView?.addItemToExpandTab(defaultShowText, popTwoListView)
    }

    private fun setConfigsDatas() {
        try {
            val `is` = context.getAssets().open("searchType")
            val searchTypeJson = readStream(`is`)
            val info = JSONObject(searchTypeJson).getJSONObject("info")
//            val messageDTO = Gson().fromJson(searchTypeJson, ConfigsMessageDTO::class.java)
//            val configsDTO = messageDTO.getInfo()
            mPriceLists = toBeans(info.getJSONArray("priceType"))
            mSortLists = toBeans(info.getJSONArray("priceType"))
            mFavorLists = toBeans(info.getJSONArray("priceType"))
//            mChildrenListLists = toBeans(info.getJSONArray("priceType"))

//            val configAreaListDTO = configsDTO.getCantonAndCircle()
//            for (configAreaDTO in configAreaListDTO) {
//                val keyValueBean = KeyValueBean()
//                keyValueBean.key = configAreaDTO.getKey()
//                keyValueBean.value = configAreaDTO.getValue()
//                mParentLists.add(keyValueBean)
//
//                val childrenLists = java.util.ArrayList<KeyValueBean>()
//                for (keyValueBean1 in configAreaDTO.getBusinessCircle()) {
//                    childrenLists.add(keyValueBean1)
//                }
//                mChildrenListLists.add(childrenLists)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun readStream(`is`: InputStream): String {
        try {
            val bo = ByteArrayOutputStream()
            var i = `is`.read()
            while (i != -1) {
                bo.write(i)
                i = `is`.read()
            }
            return bo.toString()
        } catch (e: IOException) {
            return ""
        }

    }

    fun toBeans(jsonArray: JSONArray): List<KeyValueBean> {

        var arrayList = ArrayList<KeyValueBean>()
        for (i in 0 until jsonArray.length()){
            var jsonObject = jsonArray.getJSONObject(i)
            arrayList.add(KeyValueBean("key", jsonObject.getString("key")))
        }
        return arrayList
    }
}

