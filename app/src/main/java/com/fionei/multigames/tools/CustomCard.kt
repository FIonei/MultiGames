package com.fionei.multigames.tools

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.fionei.multigames.R
import com.fionei.multigames.databinding.CustomCardViewBinding

class CustomCard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var binding: CustomCardViewBinding =
        CustomCardViewBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCard, 0, 0
        )

        val density = Resources.getSystem().displayMetrics.density
        binding.apply {
            cardName.text = typedArray.getString(R.styleable.CustomCard_card_name)
            cardName.textSize = typedArray.getDimension(R.styleable.CustomCard_android_textSize, 12f * density) / density
            cardImage.radius = typedArray.getDimension(R.styleable.CustomCard_cardCornerRadius, 0f) / density
            cardImage.foreground = AppCompatResources.getDrawable(context, typedArray.getResourceId(R.styleable.CustomCard_android_src, 0))
        }
    }

}