package com.example.nanopost.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.example.nanopost.R
import com.example.nanopost.databinding.ViewCounterBinding

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
): LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewCounterBinding.inflate(
        LayoutInflater.from(this.context),
        this,
    )

    var countText: CharSequence by binding.count::text
    var descriptionText: CharSequence by binding.description::text

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.CounterView,
            defStyleRes = defStyleAttr,
        ){
            countText = getText(R.styleable.CounterView_countText)
            descriptionText = getText(R.styleable.CounterView_descriptionText)
        }
    }

}