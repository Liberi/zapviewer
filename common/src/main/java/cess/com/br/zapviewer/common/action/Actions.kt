package cess.com.br.zapviewer.common.action

import android.content.Context
import android.content.Intent
import android.os.Bundle

const val PORTAL_SELECTION_ACTIVITY = "1"
const val PRODUCT_LIST_ACTIVITY = "2"
const val PRODUCT_DETAIL_ACTIVITY = "3"

class Actions {
    fun selectAction(action: String, context: Context, extras: Bundle) {
        val intent = Intent()
        intent.putExtras(extras)
        intent.action = action

        context.startActivity(intent)
    }
}