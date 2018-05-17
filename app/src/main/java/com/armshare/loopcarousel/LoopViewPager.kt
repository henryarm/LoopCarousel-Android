package com.armshare.loopcarousel
import android.content.Context
import android.os.Handler
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import java.util.*

/**
 * Created by armshare on 21/2/2018 AD.
 */

class LoopViewPager:ViewPager{

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    private lateinit var autoScroll: Runnable

    private var myhandler:Handler = Handler()
    var delay: Long = 3000//delay in milliseconds before task is to be executed
    var period: Long = 3000 // time in milliseconds between successive task executions.

    private lateinit var timer: Timer
    private var isAutoScroll = false

    fun startAutoScroll(){

        autoScroll = Runnable {
            if (currentItem === adapter!!.count - 1) {
                currentItem = currentItem
            }
            setCurrentItem(currentItem+1, true)
        }
        timer = Timer() // This will create a new Thread
        timer.schedule(object : TimerTask() { // task to be scheduled
            override fun run() {
                myhandler.post(autoScroll)
            }
        }, delay, period)
    }

    fun stopAutoScroll(){

        isAutoScroll = false
        myhandler.removeCallbacks(autoScroll)
        timer.cancel()

    }

}