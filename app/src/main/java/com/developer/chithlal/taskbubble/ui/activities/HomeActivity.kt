package com.developer.chithlal.taskbubble.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.developer.chithlal.taskbubble.App
import com.developer.chithlal.taskbubble.R
import com.developer.chithlal.taskbubble.databinding.ActivityHomeBinding
import com.developer.chithlal.taskbubble.ui.services.TaskBubbleService
import javax.inject.Inject

class HomeActivity : AppCompatActivity(),HomeActivityContract.View{

    private val LOG_TAG: String = "LOG_HOME_ACTIVITY"
    private var isBubbleToggled: Boolean = false
    private lateinit var activityContext:Context
    private  var serviceIntent: Intent? = null

    @Inject
    lateinit var presenter: HomePresenter
    private lateinit var mBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        (application as App).getComponent().inject(this)
        presenter.setupView(this)
    }

    override fun applyView() {
        this.activityContext = this
            registerListners()
    }

    private fun registerListners() {
        mBinding.toggleButton.setOnCheckedChangeListener{ _,state ->
            isBubbleToggled = state

            if (isBubbleToggled)
                openTaskBubble()
            else activityContext.stopService(serviceIntent)
        }
    }

    override fun showMessage() {
        TODO("Not yet implemented")
    }

    override fun openTaskBubble() {
        Log.d(LOG_TAG, "openTaskBubble: called")
        serviceIntent = Intent(this,TaskBubbleService::class.java)
        startService(serviceIntent)

    }
}