package com.spendshare.dao.spendshare.view.activity

import android.animation.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import com.drong.DropMenuView
import com.spendshare.dao.spendshare.R
import com.spendshare.dao.spendshare.model.Login
import com.spendshare.dao.spendshare.presenter.LoginPresenter
import com.spendshare.dao.spendshare.view.MyBar
import com.spendshare.dao.spendshare.view.TabBar
import com.spendshare.dao.spendshare.view.iview.LoginView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),LoginView{

    private val presenter:LoginPresenter = LoginPresenter(this, Login())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }


    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var loadStateListAnimator = AnimatorInflater.loadStateListAnimator(this, R.animator.animate_scale)
            login_btn.stateListAnimator = loadStateListAnimator
        }

        val button = Button(this).apply { text = "新增button" }
        var relativeLayout = login_btn.parent.parent as RelativeLayout
        var layoutTransition = LayoutTransition()
        var scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f)
        var scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f)
        var holder = ObjectAnimator.ofPropertyValuesHolder(relativeLayout, scaleX, scaleY)

        var dropMenuView = findViewById<DropMenuView>(R.id.drop)
        dropMenuView.addMenu("区域",arrayOf("aa","bb","cc").toList())
        dropMenuView.addMenu("价格",arrayOf("bb","cc","dd").toList())
        dropMenuView.addMenu("价格",arrayOf("cc","ee","ff").toList())
        layoutTransition.setAnimator(LayoutTransition.APPEARING, holder)
        relativeLayout.layoutTransition = layoutTransition
        var flag = false
        login_btn.setOnClickListener{

            if (!flag) {
//                relativeLayout.addView(button)
                relativeLayout.addView(dropMenuView)
            } else {
//                relativeLayout.removeView(button)
                relativeLayout.removeView(dropMenuView)
            }
            flag = !flag
//            presenter.login(login_account_edit.text.toString(),login_password_edit.text.toString())
        }

        val tabbar: TabBar = findViewById<TabBar>(R.id.tabbar)
        tabbar.addBar(MyBar(this))
        tabbar.setOnSelectedChangeListener { view, viewId ->
            Toast.makeText(this, "$viewId", Toast.LENGTH_SHORT).show()
        }
        getAnimation()

    }

    fun getAnimation() {
        login_btn.x
        var animator = ObjectAnimator.ofFloat(login_btn, "x", 0f, 100f, 110f, 400f)
        animator.apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }


    override fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }


    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    override fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
