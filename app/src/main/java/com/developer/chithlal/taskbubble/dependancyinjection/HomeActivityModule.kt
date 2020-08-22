package com.developer.chithlal.taskbubble.dependancyinjection

import com.developer.chithlal.taskbubble.ui.activities.HomeActivityContract
import com.developer.chithlal.taskbubble.ui.activities.HomeModel
import com.developer.chithlal.taskbubble.ui.activities.HomePresenter
import dagger.Module
import dagger.Provides


@Module
class HomeActivityModule {

    @Provides
    fun providePresenter(model: HomeActivityContract.Model) = HomePresenter(model)

    @Provides
    fun provideModel():HomeActivityContract.Model = HomeModel()


}