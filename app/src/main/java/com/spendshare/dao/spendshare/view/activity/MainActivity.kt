package com.spendshare.dao.spendshare.view.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.spendshare.dao.spendshare.R
import com.spendshare.dao.spendshare.model.MainModel
import com.spendshare.dao.spendshare.presenter.MainPresenter
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
        btn_drawer.setOnClickListener {
            showDrawer()
        }
        toolBar.setTitle("test")

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


    }

    override fun showDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT)
    }

    override fun hideDrawer() {
        drawerLayout.closeDrawers()
    }
}
