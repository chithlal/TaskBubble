package com.developer.chithlal.taskbubble.ui.activities

class HomePresenter(model: HomeActivityContract.Model) : HomeActivityContract.Presenter {
    override fun setupView(view: HomeActivityContract.View) {
        view.applyView();
    }

    override fun openBubble() {
        TODO("Not yet implemented")
    }
}