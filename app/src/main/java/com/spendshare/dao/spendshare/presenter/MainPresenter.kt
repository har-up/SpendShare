package com.spendshare.dao.spendshare.presenter

import com.spendshare.dao.spendshare.model.MainModel
import com.spendshare.dao.spendshare.view.iview.MainView

class MainPresenter(mainView: MainView, private val model: MainModel) {

    private var view: MainView?= mainView
    private var mode:MainModel?= model

}
