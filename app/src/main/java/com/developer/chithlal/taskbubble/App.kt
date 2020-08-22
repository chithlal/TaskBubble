package com.developer.chithlal.taskbubble

import android.app.Application
import com.developer.chithlal.taskbubble.dependancyinjection.AppComponent
import com.developer.chithlal.taskbubble.dependancyinjection.DaggerAppComponent
import com.developer.chithlal.taskbubble.dependancyinjection.HomeActivityModule

class App: Application() {
    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .homeActivityModule(HomeActivityModule())
            .build()


    }
    fun getComponent():AppComponent = appComponent
}