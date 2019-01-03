package com.spendshare.dao.spendshare.presenter

import com.spendshare.dao.spendshare.model.Login
import com.spendshare.dao.spendshare.view.iview.LoginView

class LoginPresenter:Login.LoginListener{
    private var view:LoginView ?= null

    private val model:Login
    constructor(loginView: LoginView, model: Login){
        this.view = loginView
        this.model = model
    }

    fun login(userName:String,password:String){
        model.login(userName,password,this)
    }

    fun onDestroy(){
        view = null
    }

    override fun onSuccess() {
        view?.showToast("登录成功")
        view?.navigateToHome()
    }

    override fun onFail(errMsg: String) {
        view?.showToast(errMsg)
    }
}
