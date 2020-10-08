package cess.com.br.zapviewer.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class ProductCarouselView : ConstraintLayout {

    private var carouselView: CarouselView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        LinearLayoutCompat.inflate(context, R.layout.component_product_carousel, this)

        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.ProductCarouselView,
            defStyleAttr = 0,
            defStyleRes = 0
        ) {
        }
        carouselView = findViewById(R.id.carousel_view);
    }

    fun setupView(imageList: List<Int>) {
        carouselView.pageCount = imageList.size;

        val imageListener = ImageListener { position, imageView ->
            imageView.setImageResource(imageList[position])
        }

        carouselView.setImageListener(imageListener)
    }
}