package com.developer.chithlal.taskbubble.dependancyinjection

import com.developer.chithlal.taskbubble.ui.activities.HomeActivity
import dagger.Component

@Component( modules = [HomeActivityModule::class])
public interface AppComponent {

    fun inject(activity: HomeActivity)
}