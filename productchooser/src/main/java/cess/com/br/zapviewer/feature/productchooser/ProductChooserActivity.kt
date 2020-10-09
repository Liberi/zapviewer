package cess.com.br.zapviewer.feature.productchooser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cess.com.br.zapviewer.common.action.Actions
import cess.com.br.zapviewer.common.action.PRODUCT_LIST_ACTIVITY
import kotlinx.android.synthetic.main.activity_product_chooser.*

class ProductChooserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_chooser)

        button_select_zap.setOnClickListener {
            redirectToProductList()
        }

        button_select_viva.setOnClickListener {
            redirectToProductList()
        }
    }

    private fun redirectToProductList() {
        Actions().selectAction(PRODUCT_LIST_ACTIVITY, this@ProductChooserActivity, Bundle())
    }
}