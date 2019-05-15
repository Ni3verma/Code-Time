package com.nitin.codetime.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.getStringOrThrow
import com.nitin.codetime.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.row_credits_fragment_item.view.*

class CreditsRow : ConstraintLayout {
    private lateinit var logoImageView: CircleImageView
    private lateinit var nameTextView: TextView
    private lateinit var descriptionTextView: TextView

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(attributeSet)
    }

    private fun init() {
        View.inflate(context, R.layout.row_credits_fragment_item, this)
        initView()
    }

    private fun init(attrs: AttributeSet) {
        View.inflate(context, R.layout.row_credits_fragment_item, this)
        initView()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CreditsRow)

        logoImageView.setImageResource(typedArray.getResourceIdOrThrow(R.styleable.CreditsRow_option_logo))
        nameTextView.text = typedArray.getStringOrThrow(R.styleable.CreditsRow_option_name)
        descriptionTextView.text = typedArray.getStringOrThrow(R.styleable.CreditsRow_option_description)

        typedArray.recycle()
    }

    private fun initView() {
        logoImageView = logo
        nameTextView = name
        descriptionTextView = description
    }
}