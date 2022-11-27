package cess.com.br.zapviewer.feature.productdetail

import android.app.Activity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import cess.com.br.zapviewer.component.ProductCarouselView

internal class ResourceProvider(activity: Activity?) {
    val toolbar: Toolbar? by lazy {
        activity?.findViewById(R.id.toolbar)
    }
    val carousel: ProductCarouselView? by lazy {
        activity?.findViewById(R.id.carousel_view_product_detail)
    }
    val productTitle: AppCompatTextView? by lazy {
        activity?.findViewById(R.id.text_view_product_title)
    }
    val productPrice: AppCompatTextView? by lazy {
        activity?.findViewById(R.id.text_view_price_value)
    }
    val productIPTUValue: AppCompatTextView? by lazy {
        activity?.findViewById(R.id.text_view_iptu_value)
    }
    val productCondoValue: AppCompatTextView? by lazy {
        activity?.findViewById(R.id.text_view_condo_value)
    }
    val productTotalPrice: AppCompatTextView? by lazy {
        activity?.findViewById(R.id.text_view_total_price_value)
    }
}