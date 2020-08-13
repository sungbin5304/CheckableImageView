package com.sungbin.checkableimageview.library

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorInt


/**
 * Created by SungBin on 2020-07-26.
 */

class CheckableImageView : FrameLayout {

    private lateinit var flLayout: FrameLayout
    private lateinit var ivImage: ImageView
    private lateinit var llCheckBoxLiner: LinearLayout
    private lateinit var ivCheckBoxInner: ImageView

    private val initColor by lazy {
        Color.CYAN
    }

    constructor(context: Context) : super(context) {
        CheckableImageView(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_checkable_imageview, this, false)

        flLayout = view.findViewById(R.id.fl_container)
        ivImage = view.findViewById(R.id.iv_image)
        llCheckBoxLiner = view.findViewById(R.id.ll_checkbox_liner)
        ivCheckBoxInner = view.findViewById(R.id.iv_checkbox)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CheckableImageView,
            0,
            0
        )
        val innerColor = typedArray.getColor(
            R.styleable.CheckableImageView_civ_innerColor, initColor
        )

        val linerColor = typedArray.getColor(
            R.styleable.CheckableImageView_civ_linerColor, initColor
        )

        val checkboxSize = typedArray.getDimension(
            R.styleable.CheckableImageView_civ_checkboxSize,
            dp2px(view.context, 15)
        ).toInt()

        val imageSrc = typedArray.getDrawable(
            R.styleable.CheckableImageView_civ_imageSrc
        )

        ivImage.setImageDrawable(imageSrc)
        llCheckBoxLiner.apply {
            setTint(linerColor)
            layoutParams = LayoutParams(checkboxSize, checkboxSize).apply {
                gravity = Gravity.END
                setMargins(30, 30, 30, 30)
                setPadding(3, 3, 3, 3)
            }
        }

        ivCheckBoxInner.setOnClickListener {
            try { // 한 번도 클릭된 적이 없음
                if (ivCheckBoxInner.tag.toString().toBoolean()) { //만약 클릭 됬었을때 -> 클릭 해제
                    ivCheckBoxInner.setTint(Color.parseColor("#00000000"))
                } else { // 클릭이 안되있을 때 -> 클릭 실행
                    ivCheckBoxInner.setTint(innerColor)
                }
                ivCheckBoxInner.tag = !ivCheckBoxInner.tag.toString().toBoolean()
            } catch (ignored: Exception) { // 한 번 도 클릭 된 적이 없고, 클릭했을 때 클릭된 상태로 변경
                ivCheckBoxInner.tag = "true"
                ivCheckBoxInner.setTint(innerColor)
            }
        }

        typedArray.recycle()
        addView(view)
        invalidate()
    }

    private fun View.setTint(@ColorInt color: Int) {
        this.backgroundTintList = ColorStateList.valueOf(color)
    }

    private fun dp2px(context: Context, dp: Int): Float {
        val scale: Float = context.resources.displayMetrics.density
        return (dp * scale + 0.5f)
    }


}