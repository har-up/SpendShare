package com.spendshare.dao.spendshare.presenter

import com.spendshare.dao.spendshare.model.Login
import com.spendshare.dao.spendshare.view.iview.LoginView

class LoginPresenter(loginView: LoginView, private val model: Login) :Login.LoginListener{
    private var view:LoginView ?= loginView

    fun login(userName:String,password:String){
        view?.showProgress()
        model.login(userName,password,this)
    }

    fun onDestroy(){
        view = null
    }

    override fun onSuccess() {
        view?.hideProgress()
        view?.showToast("登录成功")
        view?.navigateToHome()
    }

    override fun onFail(errMsg: String) {
        view?.hideProgress()
        view?.showToast(errMsg)
    }
}
