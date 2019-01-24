package com.spendshare.dao.spendshare.view.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spendshare.dao.spendshare.R

class TaskDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_detail_task,container,false)
        activity.supportFragmentManager.beginTransaction().replace(R.id.container_task_detail,TaskDetailBaseFragment()).commit()
        val tabLayout = view?.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout?.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        activity.supportFragmentManager.beginTransaction().replace(R.id.container_task_detail,TaskDetailBaseFragment()).commit()
                    }
                    1 -> {
                        activity.supportFragmentManager.beginTransaction().replace(R.id.container_task_detail,TaskBoxListFragment()).commit()
                    }
                }
            }

        })
        return view
    }
}
