package com.code.codingchallengeandroid.customUIButton

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.code.codingchallengeandroid.R

@SuppressLint("CustomViewStyleable", "CutPasteId")
class CustomUIButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
    private val mValue: View
    fun setValueColor(color: Int) {
        mValue.setBackgroundColor(color)
    }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.ColorOptionsView, 0, 0
        )
        val buttonTypeText = a.getInt(R.styleable.ColorOptionsView_buttonType, 1)
        val titleText = a.getString(R.styleable.ColorOptionsView_titleText)
        val subtitleText = a.getString(R.styleable.ColorOptionsView_subtitleText)
        val resourceId = a.getResourceId(
            R.styleable.ColorOptionsView_resourceId,
            R.drawable.customize
        )
        a.recycle()
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_ui_button, this, true)
        val title = getChildAt(0).findViewById<View>(R.id.llcontainer)
            .findViewById<View>(R.id.butTitle) as TextView
        title.text = titleText
        val subtitle = getChildAt(0).findViewById<View>(R.id.llcontainer)
            .findViewById<View>(R.id.butSubTitle) as TextView
        subtitle.text = subtitleText
        mValue = getChildAt(0).findViewById<View>(R.id.llcontainer).findViewById(R.id.butIcon)
        mValue.setBackgroundResource(resourceId)
        when (buttonTypeText) {
            1 -> {
                title.visibility = VISIBLE
                subtitle.visibility = GONE
                mValue.visibility = GONE
            }
            2 -> {
                title.visibility = VISIBLE
                subtitle.visibility = VISIBLE
                mValue.visibility = GONE
            }
            3 -> {
                title.visibility = VISIBLE
                subtitle.visibility = VISIBLE
                mValue.visibility = VISIBLE
            }
            else -> {
                title.visibility = GONE
                subtitle.visibility = GONE
                mValue.visibility = GONE
            }
        }
    }
}