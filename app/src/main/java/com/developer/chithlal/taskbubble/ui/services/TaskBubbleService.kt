package com.developer.chithlal.taskbubble.ui.services

import android.app.ActivityManager
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.chithlal.taskbubble.databinding.BubbleViewBinding
import kotlin.math.abs

class TaskBubbleService : Service() {
    private var isMoved: Boolean = false

    private var isTaskbarOpen: Boolean = false
    private var windowWidth = 0
    private lateinit var mWindowManager: WindowManager
    private lateinit var mBubbleView: View
    private val immerseX = 0
    var lastAction: Int = 0
    var initialX: Int = 0
    var initialY: Int = 0
    var initialTouchX: Float = 0F
    var initialTouchY: Float = 0F
    private var activePointerId = 0
    private lateinit var mBinding: BubbleViewBinding
    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: called")
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        Log.d(TAG, "onCreate: Called")
        super.onCreate()
        mBinding = BubbleViewBinding.inflate(LayoutInflater.from(this))
        mBubbleView = mBinding.root
        ActivityManager.RunningAppProcessInfo()
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        val displayMetrics = resources.displayMetrics
        windowWidth = displayMetrics.widthPixels
        Log.d(TAG, "onCreate: Display width: $windowWidth")
        params.gravity = Gravity.TOP or Gravity.START

        params.x = 0;
        params.y = 100

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowManager.addView(mBubbleView, params)
        registerListener(params)
    }

    private fun registerListener(params: WindowManager.LayoutParams) {

        mBinding.root.setOnTouchListener { v, event ->


            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    Log.d(TAG, "registerListener: ActionDown")
                    initialX = params.x
                    initialY = params.y

                    //get touch location
                    initialTouchX = event.getRawX()
                    initialTouchY = event.getRawY()
                    lastAction = event.action


                }
                MotionEvent.ACTION_UP -> {
                    Log.d(TAG, "registerListener: ACTION_UP")
                    if (lastAction == MotionEvent.ACTION_DOWN || !isMoved) {
                        v.performClick()

                    }
                    if (params.x < windowWidth / 2)
                        params.x = 0-immerseX
                    else
                        params.x = windowWidth+immerseX
                    mWindowManager.updateViewLayout(mBinding.root, params)

                    lastAction = event.action
                }
                MotionEvent.ACTION_MOVE -> {
                    //Calculate the X and Y coordinates of the view.
                    isMoved = false

                    Log.d(
                        TAG,
                        "registerListener: InitialTouchX: $initialTouchX , InitTouchY: $initialTouchY"
                    )
                    Log.d(
                        TAG,
                        "registerListener: currentX:${event.x.toInt()} curY:${event.y.toInt()}"
                    )
                    val dx = initialX + (event.rawX - initialTouchX).toInt()
                    params.x = dx
                    params.y = initialY + (event.rawY - initialTouchY).toInt()
                    //Update the layout with new X & Y coordinate
                    mWindowManager.updateViewLayout(mBinding.root, params)
                    Log.d(TAG, "registerListener: MovedX:" + (event.rawX - initialTouchX).toInt())
                    val movedX = abs((event.rawX - initialTouchX).toInt())
                    val movedY = abs((event.rawY - initialTouchY).toInt())
                    if (movedX > 10 || movedY > 10) {
                        Log.d(TAG, "registerListener: ACTION_MOVE")
                        lastAction = event.action
                        isMoved = true
                    }
                }


            }
            true
        }

        mBinding.root.setOnClickListener {
            Log.d(TAG, "registerListener: Clicked")
            if (!isTaskbarOpen) {
                mBinding.imageViewOpenBubble.visibility = GONE
                mBinding.imageViewCloseBubble.visibility = VISIBLE
                openTaskList()
                isTaskbarOpen = true
            } else {
                mBinding.imageViewOpenBubble.visibility = VISIBLE
                mBinding.imageViewCloseBubble.visibility = GONE
                closeTaskList()
                isTaskbarOpen = false
            }
        }
    }

    private fun openTaskList() {
        val list = ArrayList<Task>()
        list.add(Task("Instagram",
        "com.instagram",120,1))
        val taskAdapter = TasklistAdapter(list)
        mBinding.rvTaskList.layoutManager = LinearLayoutManager(this)
        mBinding.rvTaskList.adapter = taskAdapter
        mBinding.rvTaskList.visibility = VISIBLE
    }
    fun closeTaskList(){
        mBinding.rvTaskList.visibility = GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mWindowManager.removeView(mBubbleView)
        Log.d(TAG, "onDestroy: called")
    }
}