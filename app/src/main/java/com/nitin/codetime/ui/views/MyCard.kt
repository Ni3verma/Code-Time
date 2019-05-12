package com.nitin.codetime.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.nitin.codetime.R
import kotlinx.android.synthetic.main.row_github_fragment_item.view.*

class MyCard : LinearLayout {
    lateinit var textView: AppCompatTextView

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(attributeSet)
    }

    private fun init() {
        View.inflate(context, R.layout.row_github_fragment_item, this)
        textView = card_text_view
    }

    private fun init(attrs: AttributeSet) {
        View.inflate(context, R.layout.row_github_fragment_item, this)
        textView = card_text_view
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCard)
        textView.text = typedArray.getString(R.styleable.MyCard_option_text)

        textView.setCompoundDrawablesWithIntrinsicBounds(
            typedArray.getDrawable(R.styleable.MyCard_option_icon), null, null, null
        )

        typedArray.recycle()
    }

}