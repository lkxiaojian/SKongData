package com.zky.basics.common.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.zky.basics.common.R


/**
 *create_time : 21-3-2 下午4:21
 *author: lk
 *description： RightPicClickEditText
 */
class RightPicClickEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    private var drawableHint = ContextCompat.getDrawable(context, R.drawable.paw_hint)
    private val drawableLeft = ContextCompat.getDrawable(context, R.drawable.login_password)

    private var firstMesage = "......."
    private var imageOnCLick: OnClickRightOrLeftImage? = null
    private var hintInit = true

    fun setOnClickRightOrLeftImage(_imageOnCLick: OnClickRightOrLeftImage) {
        this.imageOnCLick = _imageOnCLick
    }

    companion object {
        private var isFirstIndex = 1
    }


    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        //防止未初始化，就进行加载
        if(firstMesage==null){
            return
        }
        if (text.isNullOrEmpty()) {
            drawableLeft?.setBounds(0, 0, drawableLeft.intrinsicWidth, drawableLeft.intrinsicHeight)
            setCompoundDrawables(drawableLeft, null, null, null)
        } else {
            drawableHint = if (hintInit) {
                ContextCompat.getDrawable(context, R.drawable.paw_hint)
            } else {
                ContextCompat.getDrawable(context, R.drawable.nohint)
            }
            drawableLeft?.setBounds(0, 0, drawableLeft.intrinsicWidth, drawableLeft.intrinsicHeight);
            drawableHint?.setBounds(0, 0, drawableHint!!.intrinsicWidth, drawableHint!!.intrinsicHeight);
            setCompoundDrawables(drawableLeft, null, drawableHint, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 触摸状态
        if (event!!.action == MotionEvent.ACTION_UP) {
            val drawableRight = compoundDrawables[2]
            val rawX = event.rawX
            if (drawableRight != null && rawX >= (right - drawableRight.bounds.width())) {
                //点击了右侧图标
                imageOnCLick?.onClickRightOrLeft(0)
                if (hintInit) {
                    inputType = InputType.TYPE_NULL
                    drawableHint = ContextCompat.getDrawable(context, R.drawable.nohint)
                } else {
                    inputType =  InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                    drawableHint = ContextCompat.getDrawable(context, R.drawable.paw_hint)
                }
                if(text!=null){
                    text?.toString()?.length?.let { setSelection( it) }
                }
                drawableLeft?.setBounds(0, 0, drawableLeft.intrinsicWidth, drawableLeft.intrinsicHeight);
                drawableHint?.setBounds(0, 0, drawableHint!!.intrinsicWidth, drawableHint!!.intrinsicHeight);
                setCompoundDrawables(drawableLeft, null, drawableHint, null)
                hintInit = !hintInit

                return true
            }
            val drawableLeft = compoundDrawables[0]
            if (drawableLeft != null && rawX <= (left + drawableLeft.bounds.width())) {
                //点击了左侧监听
                imageOnCLick?.onClickRightOrLeft(1)
                return true
            }
        }
        return super.onTouchEvent(event)
    }
    /**
     * TODO 0 right 1 left
     *
     */
    interface OnClickRightOrLeftImage {
        fun onClickRightOrLeft(index: Int)
    }
}