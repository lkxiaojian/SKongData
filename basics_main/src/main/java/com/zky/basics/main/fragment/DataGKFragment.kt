package com.zky.basics.main.fragment

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zky.basics.api.RetrofitManager
import com.zky.basics.api.config.API
import com.zky.basics.common.mvvm.BaseFragment
import com.zky.basics.main.R


/**
 * @Description:    数据概况
 * @Author:         lk
 * @CreateDate:     2021/8/17 14:48
 */
class DataGKFragment : BaseFragment() {
    override fun onBindLayout() = R.layout.data_gk_fragment

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(view: View?) {
        val webView = view?.findViewById<WebView>(R.id.wv)
        val url =
            "${API.WEB_URL_HOST}index.html?token=${RetrofitManager.TOKEN}#/singleChartPage"
        webView?.let {
            val settings = it.settings
            // 设置WebView支持JavaScript
            settings.javaScriptEnabled = true
            //支持自动适配
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.setSupportZoom(false) //支持放大缩小
            settings.builtInZoomControls = false //显示缩放按钮
            settings.blockNetworkImage = true // 把图片加载放在最后来加载渲染
            settings.allowFileAccess = false
//            settings.saveFormData = false
//            settings.domStorageEnabled = true
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            //设置不让其跳转浏览器
            it.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }
            }
            it.webChromeClient = WebChromeClient()
            it.loadUrl(url)
        }
    }


    override fun initData() {
    }

}