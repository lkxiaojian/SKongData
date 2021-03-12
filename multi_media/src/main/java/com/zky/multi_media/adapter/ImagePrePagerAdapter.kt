package com.zky.multi_media.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.zky.basics.api.room.bean.MediaBean
import com.zky.multi_media.R
import com.zky.multi_media.databinding.ItemPreImageBinding
import java.util.*

/**
 * Created by lk
 * Date 2019-11-21
 * Time 11:15
 * Detail:
 */
class ImagePrePagerAdapter(val context: Context) : PagerAdapter() {

    private val list = arrayListOf<MediaBean>()
    private val layoutInflater = LayoutInflater.from(context)
    private var defaultImageView: ImageView = ImageView(context)
    fun addAll(list: List<MediaBean>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    private val cacheViews = LinkedList<View>()
    var startIndex = 0
        set(value) {
            if (value >= 1) {
                field = value - 1
            } else {
                field = value
            }
        }
    private var start = false

    init {
        (0..4).forEach { _ ->
            cacheViews.add(getView())
        }
    }

    private fun getView(): View {
        val preImageBinding =
            DataBindingUtil.inflate<ItemPreImageBinding>(layoutInflater, R.layout.item_pre_image, null, false)
        val view = preImageBinding.root
        view.tag = preImageBinding
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        if (obj is View) {
            container.removeView(obj)
            cacheViews.add(obj)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (startIndex!=0 && startIndex == position && !start) {
            return defaultImageView
        }
        start = true
        val albumFile = list[position]
        val view = if (cacheViews.size > 0) {
            cacheViews.pop()
        } else {
            getView()
        }
        val preImageBinding = view.tag as ItemPreImageBinding?
        preImageBinding?.let {
            it.item = albumFile
            it.executePendingBindings()
        }
        val parent = view.parent
        if (parent is ViewGroup) {
            parent.removeView(view)
        }
        container.addView(view)
        return view
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean = p0 == p1

    override fun getCount(): Int = list.size
}