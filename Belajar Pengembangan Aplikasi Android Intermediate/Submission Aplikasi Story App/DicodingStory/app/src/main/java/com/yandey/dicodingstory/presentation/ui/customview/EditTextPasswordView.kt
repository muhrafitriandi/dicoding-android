package com.yandey.dicodingstory.presentation.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.yandey.dicodingstory.R

class EditTextPasswordView : AppCompatEditText, View.OnTouchListener {

    private lateinit var visibilityPassword: Drawable
    private lateinit var visibilityOffPassword: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = ContextCompat.getDrawable(context, R.drawable.bg_edittext) as Drawable
        hint = resources.getString(R.string.password)
        setPadding(48, 0, 48, 0)
    }

    private fun init() {
        visibilityPassword =
            ContextCompat.getDrawable(context, R.drawable.ic_visibility) as Drawable
        visibilityOffPassword =
            ContextCompat.getDrawable(context, R.drawable.ic_visibility_off) as Drawable
        setOnTouchListener(this)
        visibilityButton()
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(e: Editable) {
                if (e.length < 6) setError(
                    resources.getString(R.string.password_warning_message),
                    null
                )
                if (e.toString().isEmpty()) error = null
            }
        })
    }

    private fun visibilityButton() {
        setButtonDrawables(endOfTheText = visibilityPassword)
    }

    private fun visibilityOffButton() {
        setButtonDrawables(endOfTheText = visibilityOffPassword)
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (visibilityPassword.intrinsicWidth + paddingStart).toFloat()
                when {
                    motionEvent.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart =
                    (width - paddingStart - visibilityPassword.intrinsicWidth).toFloat()
                when {
                    motionEvent.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        visibilityButton()
                        return false
                    }
                    MotionEvent.ACTION_UP -> {
                        if (transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                            transformationMethod = HideReturnsTransformationMethod.getInstance()
                            visibilityOffButton()
                        } else {
                            transformationMethod = PasswordTransformationMethod.getInstance()
                            visibilityButton()
                        }
                        return false
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }
}