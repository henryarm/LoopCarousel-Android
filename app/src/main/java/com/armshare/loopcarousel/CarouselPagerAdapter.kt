package com.armshare.loopcarousel

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import com.armshare.loopcarousel.model.Banner


@Suppress("NAME_SHADOWING")
class CarouselPagerAdapter(private val context: Context, private val fragmentManager:FragmentManager)
        : FragmentStatePagerAdapter(fragmentManager), ViewPager.OnPageChangeListener {
    private var scale: Float = 0.toFloat()
    lateinit var pager: ViewPager

    var banners : List<Banner> = listOf()
    var isSingle = false
    override fun getItem(position: Int): Fragment {
        var position = position
        try {
            if (position == getFirstPage())
                scale = BIG_SCALE
            else
                scale = SMALL_SCALE

            position %= banners.size

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return CarouselFragment.newInstance(context, position, scale,banners[position],isSingle)
    }

    override fun getCount(): Int {
        var count = 0
        try {
            count = banners.size * LOOPS
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return count
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        try {
            if (positionOffset in 0f..1f) {
                val cur = getRootView(position)
                val next = getRootView(position + 1)

                cur?.setScaleBoth(BIG_SCALE - DIFF_SCALE * positionOffset)
                next?.setScaleBoth(SMALL_SCALE + DIFF_SCALE * positionOffset)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    override fun onPageSelected(position: Int) {
//        val fragment = getFragmentTag(position)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    fun getRootView(position: Int): CarouselRelativeLayout? {
        return  getFragment(position).view!!.findViewById(R.id.root_container)
    }

    fun getFragment(position: Int):CarouselFragment{
        return  (instantiateItem(pager,position) as CarouselFragment)
    }


    fun getFirstPage():Int{
        if (banners.isEmpty())
            return  0
        return banners.size * (CarouselPagerAdapter.LOOPS / banners.size/3)
    }

    companion object {
        val BIG_SCALE = 1.0f
        val SMALL_SCALE = 0.85f
        val DIFF_SCALE = BIG_SCALE - SMALL_SCALE

        val LOOPS = 10000
    }

}