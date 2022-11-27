package cess.com.br.zapviewer.productlist.ui

import android.app.Activity
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import cess.com.br.zapviewer.productlist.R

internal class ResourceProvider(activity: Activity?) {
    val toolbar: Toolbar? by lazy {
        activity?.findViewById(R.id.toolbar)
    }
    val progressIndicator: ProgressBar? by lazy {
        activity?.findViewById(R.id.product_progress_bar)
    }
    val productList: RecyclerView? by lazy {
        activity?.findViewById(R.id.product_recycler_view)
    }
}