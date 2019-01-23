package com.spendshare.dao.spendshare.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.transition.Visibility
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.spendshare.dao.spendshare.R
import com.spendshare.dao.spendshare.R.id.action_check
import com.spendshare.dao.spendshare.R.id.action_task
import com.spendshare.dao.spendshare.model.MainModel
import com.spendshare.dao.spendshare.presenter.MainPresenter
import com.spendshare.dao.spendshare.view.fragment.CheckFragment
import com.spendshare.dao.spendshare.view.fragment.TaskAddFragmen
import com.spendshare.dao.spendshare.view.fragment.TaskFragment
import com.spendshare.dao.spendshare.view.iview.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter = MainPresenter(this, MainModel())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        navigation.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                action_task -> {
                    supportFragmentManager.beginTransaction().replace(R.id.contentContainer,TaskFragment()).commit()
                    add_task.visibility = View.VISIBLE
                    true
                }
                action_check -> {
                    supportFragmentManager.beginTransaction().replace(R.id.contentContainer,CheckFragment()).commit()
                    true
                }
                else -> false
            }
        }
        navigation.selectedItemId = R.id.action_task
        val headerView = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.drawerUserImage)
        headerView.setOnClickListener({view ->
            Toast.makeText(this@MainActivity, "hello", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawers()
        })
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawers()
            return@OnNavigationItemSelectedListener true
        })
        toolBar.navigationIcon = resources.getDrawable(R.drawable.ic_chevron_left)
        toolBar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }
        add_task.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.contentContainer,TaskAddFragmen()).commit()
            add_task.visibility = View.INVISIBLE
        }
    }

    override fun showDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT)
    }

    override fun hideDrawer() {
        drawerLayout.closeDrawers()
    }
}
