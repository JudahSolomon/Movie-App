package com.anushka.tmdbclient.presentation.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.anushka.tmdbclient.R

class ViewPagerAdapter(var context: Context) : PagerAdapter() {
    var viewPagerAdapter: ViewPagerAdapter? = null
    private lateinit var dots: Array<TextView?>
    private var images = intArrayOf(
        R.drawable.troy_movie,
        R.drawable.tv_shows,
        R.drawable.tom_and_zendaya,)


    private var headings = intArrayOf(
        R.string.heading_one,
        R.string.heading_two,
        R.string.heading_three,

    )
    private var description = intArrayOf(

        R.string.desc_three,
        R.string.desc_fourth,
        R.string.desc_fourth
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)
        val slidetitleimage = view.findViewById<View>(R.id.titleImage) as ImageView
        val slideHeading = view.findViewById<View>(R.id.texttitle) as TextView
        slidetitleimage.setImageResource(images[position])
        slideHeading.setText(headings[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}