package cess.com.br.zapviewer.productlist.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cess.com.br.zapviewer.common.action.PORTAL_NAME_PARAM
import cess.com.br.zapviewer.common.model.Product
import cess.com.br.zapviewer.productlist.R
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListActivity : AppCompatActivity() {

    private val pageSize = 20
    private val viewModel: ProductListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        initViewModel()
    }

    private fun initViewModel() {
        with(viewModel) {
            getProductList(getPortalName().orEmpty())

            loading.observe(this@ProductListActivity,
                Observer {
                    if (it.not()) product_progress_bar.visibility = View.GONE else View.VISIBLE
                })

            result.observe(this@ProductListActivity,
                Observer {
                    setupRecyclerView(it)
                })
        }
    }

    private fun setupRecyclerView(result: List<Product>) {

        val linearLayoutManager = LinearLayoutManager(this@ProductListActivity)
        val products = ArrayList(result.subList(0, 20))

        product_recycler_view.apply {
            layoutManager = linearLayoutManager
            adapter = ProductListAdapter(products)
        }

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(product_recycler_view, newState)
                val lastVisibleItemPosition: Int = linearLayoutManager.findLastVisibleItemPosition()
                val totalItemCount = recyclerView.layoutManager?.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {

                    val fromIndex = products.lastIndex + 1
                    val maxIndex = products.lastIndex + pageSize
                    val toIndex = if (maxIndex > result.lastIndex) result.lastIndex else maxIndex

                    products.addAll(result.subList(fromIndex, toIndex))
                    product_recycler_view.adapter?.notifyDataSetChanged()
                }
            }
        }

        product_recycler_view.addOnScrollListener(scrollListener)
    }

    private fun getPortalName() = intent.getStringExtra(PORTAL_NAME_PARAM)

}