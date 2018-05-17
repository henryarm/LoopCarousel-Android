package com.armshare.loopcarousel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.armshare.loopcarousel.model.Banner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var loopIndicator: LoopPagerIndicator
    lateinit var loopViewPager: LoopViewPager
    private lateinit var carouselPagerAdapter: CarouselPagerAdapter

    private lateinit var loopIndicatorSingle: LoopPagerIndicator
    lateinit var loopViewPagerSingle: LoopViewPager
    private lateinit var carouselPagerAdapterSingle: CarouselPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loopViewPager = viewPager
        initView()
        setupBanner(listOf(
                Banner("test1","description1",R.drawable.cat1),
                Banner("test2","description2",R.drawable.cat1),
                Banner("test3","description3",R.drawable.cat1)))

        loopViewPagerSingle = viewPagerSingle
        initViewSingle()
        setupBannerSingle(listOf(
                Banner("test1","description1",R.drawable.cat1),
                Banner("test2","description2",R.drawable.cat1),
                Banner("test3","description3",R.drawable.cat1)))


    }

    private fun setupBanner(banners: List<Banner>) {
        loopViewPager.addOnPageChangeListener(carouselPagerAdapter)

        carouselPagerAdapter.banners = banners
        carouselPagerAdapter.notifyDataSetChanged()

        loopViewPager.apply {
            setCurrentItem(carouselPagerAdapter.getFirstPage(), false)
            visibility = View.VISIBLE
            setOnTouchListener({ _, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> startAutoScroll()
                    MotionEvent.ACTION_MOVE -> stopAutoScroll()
                    MotionEvent.ACTION_DOWN -> stopAutoScroll()
                    else -> stopAutoScroll()

                }
                onTouchEvent(event)
            })
        }

        loopIndicator.setPageCount(carouselPagerAdapter.banners.size)
        loopIndicator.show()
    }

    private fun initView() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
//        if (isTablet()) {
            val pageMargin = metrics.widthPixels / 4 * 2
            loopViewPager.pageMargin = -pageMargin
//        }
        carouselPagerAdapter = CarouselPagerAdapter(this, supportFragmentManager)
        carouselPagerAdapter.pager = loopViewPager
        carouselPagerAdapter.notifyDataSetChanged()

        loopViewPager.apply {
            adapter = carouselPagerAdapter
            offscreenPageLimit = 3
            visibility = View.INVISIBLE

                val width = Math.min(metrics.widthPixels, metrics.heightPixels)
                val height = Math.round((width / 2) * .5625f)
                layoutParams.height = height

        }

        loopIndicator = LoopPagerIndicator(this, pagesContainer, loopViewPager, R.drawable.indicator_circle)

    }

    private fun setupBannerSingle(banners: List<Banner>) {
        loopViewPagerSingle.addOnPageChangeListener(carouselPagerAdapterSingle)

        carouselPagerAdapterSingle.banners = banners
        carouselPagerAdapterSingle.isSingle = true
        carouselPagerAdapterSingle.notifyDataSetChanged()

        loopViewPagerSingle.apply {
            setCurrentItem(carouselPagerAdapterSingle.getFirstPage(), false)
            visibility = View.VISIBLE
            setOnTouchListener({ _, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> startAutoScroll()
                    MotionEvent.ACTION_MOVE -> stopAutoScroll()
                    MotionEvent.ACTION_DOWN -> stopAutoScroll()
                    else -> stopAutoScroll()

                }
                onTouchEvent(event)
            })
        }

        loopIndicatorSingle.setPageCount(carouselPagerAdapterSingle.banners.size)
        loopIndicatorSingle.show()
    }

    private fun initViewSingle() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        carouselPagerAdapterSingle = CarouselPagerAdapter(this, supportFragmentManager)
        carouselPagerAdapterSingle.pager = loopViewPagerSingle
        carouselPagerAdapterSingle.notifyDataSetChanged()

        loopViewPagerSingle.apply {
            adapter = carouselPagerAdapterSingle
            offscreenPageLimit = 3
            visibility = View.INVISIBLE
            layoutParams.height = resources.getDimension(R.dimen.carousel_view_height).toInt()
        }

        loopIndicatorSingle = LoopPagerIndicator(this, pagesContainerSingle, loopViewPagerSingle, R.drawable.indicator_circle)

    }


    override fun onResume() {
        super.onResume()
        loopViewPager.startAutoScroll()
        loopViewPagerSingle.startAutoScroll()
    }


    override fun onPause() {
        super.onPause()
        loopViewPager.stopAutoScroll()
        loopViewPagerSingle.stopAutoScroll()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::loopIndicator.isInitialized)
            loopIndicator.cleanup()
        if (::loopViewPagerSingle.isInitialized)
            loopIndicatorSingle.cleanup()
    }
}
