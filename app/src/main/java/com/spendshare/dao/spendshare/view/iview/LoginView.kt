package com.spendshare.dao.spendshare.view.iview

interface LoginView {
    fun showProgress()
    fun hideProgress()
    fun showToast(msg:String)
    fun navigateToHome()
}
