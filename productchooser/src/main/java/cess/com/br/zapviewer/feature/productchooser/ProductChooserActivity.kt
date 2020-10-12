package cess.com.br.zapviewer.feature.productchooser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cess.com.br.zapviewer.common.action.PORTAL_NAME_PARAM
import cess.com.br.zapviewer.common.action.PRODUCT_LIST_ACTIVITY
import cess.com.br.zapviewer.common.action.REAL
import cess.com.br.zapviewer.common.action.ZAP
import kotlinx.android.synthetic.main.activity_product_chooser.*

class ProductChooserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_chooser)

        button_select_zap.setOnClickListener {
            redirectToProductList(ZAP)
        }

        button_select_viva.setOnClickListener {
            redirectToProductList(REAL)
        }
    }

    private fun redirectToProductList(portalName: String) {
        startActivity(Intent(PRODUCT_LIST_ACTIVITY).putExtra(PORTAL_NAME_PARAM, portalName))
    }
}