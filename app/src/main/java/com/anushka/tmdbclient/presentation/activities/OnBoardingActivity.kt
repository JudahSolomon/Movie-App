package com.anushka.tmdbclient.presentation.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.anushka.tmdbclient.R
import com.anushka.tmdbclient.databinding.ActivityOnBoardingBinding

class OnBoardingActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    var mSLideViewPager: ViewPager? = null
    var mDotLayout: LinearLayout? = null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
        // making the activity fullscreen
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.backbtn.setOnClickListener {
            if (getItem(0) > 0) {
                mSLideViewPager!!.setCurrentItem(getItem(-1), true)
            }
        }

        binding.nextbtn.setOnClickListener{
            if (getItem(0) < 3) mSLideViewPager!!.setCurrentItem(getItem(1), true)

            else {
                val intent = startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
        binding.skipButton.setOnClickListener {
            val intent = startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        mSLideViewPager = findViewById<View>(R.id.slideViewPager) as ViewPager
        mDotLayout = findViewById<View>(R.id.indicator_layout) as LinearLayout
        val viewPagerAdapter = ViewPagerAdapter(this)
        mSLideViewPager!!.adapter = viewPagerAdapter
        setUpIndicator(0)
        mSLideViewPager!!.addOnPageChangeListener(viewListener)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun setUpIndicator(position: Int) {
        val dots = arrayOfNulls<TextView>(4)
        mDotLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml(".")
            dots[i]!!.text = 35.toString()
            dots[i]!!
                .setTextColor(resources.getColor(R.color.inactive, applicationContext.theme))
            mDotLayout!!.addView(dots[i])
        }
        dots[position]!!
            .setTextColor(resources.getColor(R.color.active, applicationContext.theme))
    }

    private var viewListener: ViewPager.OnPageChangeListener = object :
        ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onPageSelected(position: Int) {
            setUpIndicator(position)
            if (position > 0) {
                binding.backbtn.visibility = View.VISIBLE
            } else {
                binding.backbtn.visibility = View.INVISIBLE
            }
        }
        override fun onPageScrollStateChanged(state: Int) {}
    }

    private fun getItem(i: Int): Int {
        return mSLideViewPager!!.currentItem + i
    }

}