package cess.com.br.zapviewer.feature.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cess.com.br.zapviewer.common.action.PRODUCT_DETAILS_PARAM
import cess.com.br.zapviewer.common.model.BusinessType
import cess.com.br.zapviewer.common.model.Product

class ProductDetailActivity : AppCompatActivity() {

    private val resourceProvider: ResourceProvider by lazy {
        ResourceProvider(this@ProductDetailActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = getProductDetail()
        product?.let {
            setupUi(it)
        }

        setSupportActionBar(resourceProvider.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupUi(product: Product) {
        resourceProvider.carousel?.setupView(product.images)
        resourceProvider.productTitle?.text = getString(
            R.string.product_title_description,
            getBusinessType(product.type),
            product.bedrooms,
            product.usableAreas
        )
        resourceProvider.productPrice?.text =
            getString(R.string.product_price_info, product.price.toString())
        resourceProvider.productIPTUValue?.text =
            getString(R.string.product_price_info, product.yearlyIptu.toString())
        resourceProvider.productCondoValue?.text =
            getString(R.string.product_price_info, product.condoFee.toString())
        resourceProvider.productTotalPrice?.text =
            getString(R.string.product_price_info, product.totalPrice.toString())
    }

    private fun getBusinessType(type: BusinessType) = if (type == BusinessType.RENTAL)
        getString(R.string.rental)
    else
        getString(R.string.sale)


    private fun getProductDetail() = intent.getParcelableExtra<Product>(PRODUCT_DETAILS_PARAM)
}