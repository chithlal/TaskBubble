package com.developer.chithlal.taskbubble.ui.activities

interface HomeActivityContract {

    interface View{
        fun applyView()
        fun showMessage()
        fun openTaskBubble()
    }

    interface Presenter{

        fun setupView(view:View)
        fun openBubble()
    }
    interface Model{

        fun getTaskList():List<Tasks>
    }
}