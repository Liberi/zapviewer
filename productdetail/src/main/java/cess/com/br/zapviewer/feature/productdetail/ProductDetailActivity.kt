package cess.com.br.zapviewer.feature.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cess.com.br.zapviewer.common.action.PRODUCT_DETAILS_PARAM
import cess.com.br.zapviewer.common.model.BusinessType
import cess.com.br.zapviewer.common.model.Product
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = getProductDetail()
        product?.let {
            setupUi(it)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupUi(product: Product) {
        carousel_view_product_detail.setupView(product.images)
        text_view_product_title.text = getString(
            R.string.product_title_description,
            getBusinessType(product.type),
            product.bedrooms,
            product.usableAreas
        )
        text_view_price_value.text =
            getString(R.string.product_price_info, product.price.toString())
        text_view_iptu_value.text =
            getString(R.string.product_price_info, product.yearlyIptu.toString())
        text_view_condo_value.text =
            getString(R.string.product_price_info, product.condoFee.toString())
        text_view_total_price_value.text =
            getString(R.string.product_price_info, product.totalPrice.toString())
    }

    private fun getBusinessType(type: BusinessType) = if (type == BusinessType.RENTAL)
        getString(R.string.rental)
    else
        getString(R.string.sale)


    private fun getProductDetail() = intent.getParcelableExtra<Product>(PRODUCT_DETAILS_PARAM)
}