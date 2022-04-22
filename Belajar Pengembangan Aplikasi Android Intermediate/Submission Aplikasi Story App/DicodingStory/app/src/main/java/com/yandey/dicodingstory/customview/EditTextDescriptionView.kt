package com.yandey.dicodingstory.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.yandey.dicodingstory.R

class EditTextDescriptionView : AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = ContextCompat.getDrawable(context, R.drawable.bg_edittext) as Drawable
        setPadding(48, 48, 48, 48)
        hint = resources.getString(R.string.tell)
    }
}