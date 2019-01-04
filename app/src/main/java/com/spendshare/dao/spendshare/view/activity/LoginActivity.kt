package com.spendshare.dao.spendshare.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.spendshare.dao.spendshare.R
import com.spendshare.dao.spendshare.model.Login
import com.spendshare.dao.spendshare.presenter.LoginPresenter
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
        login_btn.setOnClickListener{
            presenter.login(login_account_edit.text.toString(),login_password_edit.text.toString())
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
