package com.spendshare.dao.spendshare.model

import android.os.Handler
import android.text.TextUtils

class Login {

    fun login(username:String,password:String,listener: LoginListener){
        Handler().postDelayed({
            if (TextUtils.isEmpty(username)) {
                listener.onFail("用户名不能为空")
                return@postDelayed
            }
            if (TextUtils.isEmpty(password)) {
                listener.onFail("密码错误")
                return@postDelayed
            }
            listener.onSuccess()
        }, 2000)
    }

    interface LoginListener{
        fun onSuccess()
        fun onFail(errMsg:String)
    }
}
