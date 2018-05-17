package com.armshare.loopcarousel

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.widget.ImageView

import android.graphics.Outline
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import com.armshare.loopcarousel.model.Banner


class CarouselFragment : Fragment() {

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWidthAndHeight()
    }

    private lateinit var banner: Banner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null ) {
            return null
        }

        val scale = this.arguments!!.getFloat(SCALE)
        banner = this.arguments!!.getParcelable<Banner>(BANNER)
        val position = this.arguments!!.getInt(POSITON)
        val isSingle = this.arguments!!.getBoolean(SINGLE)


        val linearLayout = inflater.inflate(R.layout.widget_fragment_carousel, container, false) as RelativeLayout
            val root = linearLayout.findViewById<View>(R.id.root_container) as CarouselRelativeLayout
            val imageView = linearLayout.findViewById<View>(R.id.pagerImg) as ImageView
            val textLayout = linearLayout.findViewById<View>(R.id.textLayout) as RelativeLayout
            val label = linearLayout.findViewById<View>(R.id.label) as TextView
            val description = linearLayout.findViewById<View>(R.id.description) as TextView

            var layoutParams = RelativeLayout.LayoutParams(screenWidth, screenHeight / 4)
            var textLayoutParams = RelativeLayout.LayoutParams(screenWidth, screenHeight / 4)

            if (!isSingle) {
                val width = Math.min(screenWidth, screenHeight)
                val height = Math.round((width / 2) * .5625f)
                layoutParams = RelativeLayout.LayoutParams(width / 2, height)
                textLayoutParams = RelativeLayout.LayoutParams(width / 2, height)


                textLayout.outlineProvider = ClipOutlineProvider()
                textLayout.clipToOutline = true
                imageView.outlineProvider = ClipOutlineProvider()
                imageView.clipToOutline = true
            }
            else {
                textLayout.elevation = 0f
                imageView.elevation = 0f

            }
            textLayout.layoutParams = textLayoutParams

            imageView.layoutParams = layoutParams

            imageView.setImageResource(banner.imageResource)

            label.text = banner.title
            description.text = banner.description

            root.setScaleBoth(scale)

            imageView.setOnClickListener {
               startImageActivity()
            }
//        val loopViewPager = (activity.supportFragmentManager.findFragmentByTag(MainActivity.DASHBOARD_TAG) as DashBoardFragment).loopViewPager
//
//        imageView.setOnTouchListener({v: View?, event: MotionEvent? ->
//            when(event!!.action){
//                MotionEvent.ACTION_DOWN -> loopViewPager.stopAutoScroll()
//                MotionEvent.ACTION_CANCEL -> loopViewPager.startAutoScroll()
//                MotionEvent.ACTION_UP -> loopViewPager.startAutoScroll()
//            }
//            imageView.onTouchEvent(event)
//        })

        return linearLayout
    }

    /**
     * Get device screen width and height
     */
    private fun getWidthAndHeight() {
        val displaymetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displaymetrics)
        screenHeight = displaymetrics.heightPixels
        screenWidth = displaymetrics.widthPixels
    }

    companion object {

        private val POSITON = "position"
        private val SCALE = "scale"
        private val DRAWABLE_RESOURE = "resource"
        private val BANNER = "banner"
        private val SINGLE = "single"
        fun newInstance(context: Context, pos: Int, scale: Float,banner: Banner,single:Boolean): Fragment {
            val b = Bundle()
            b.putInt(POSITON, pos)
            b.putFloat(SCALE, scale)
            b.putParcelable(BANNER,banner)
            b.putBoolean(SINGLE,single)
            return Fragment.instantiate(context, CarouselFragment::class.java.name,b)
        }
    }


    private inner class ClipOutlineProvider : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val margin = Math.min(view.width, view.height) / 8
            outline.setRoundRect(margin, margin, view.width - margin,
                    view.height - margin, (margin).toFloat())
        }
    }


    private fun startImageActivity(){
        startActivity(Intent(activity,ImageDetailsActivity::class.java)
                .putExtra(DRAWABLE_RESOURE,banner.imageResource))
    }

}


