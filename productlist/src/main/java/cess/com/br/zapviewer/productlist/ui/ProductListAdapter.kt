package cess.com.br.zapviewer.productlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cess.com.br.zapviewer.common.model.Product
import cess.com.br.zapviewer.common.model.ProductAddress
import cess.com.br.zapviewer.component.ProductCarouselView
import cess.com.br.zapviewer.productlist.R
import coil.transform.CircleCropTransformation

class ProductListAdapter(
    private val productList: List<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_row, parent, false)
        return ProductListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(productList[position]) {
            (holder as ProductListViewHolder).setup(
                type.typeName,
                totalPrice.toString(),
                "$bedrooms Quarto(s), $bathrooms Banheiro(s), $parkingSpaces Vaga(s)",
                images,
                "${address.neighborhood} - ${address.city}"
            )
        }

    }

    inner class ProductListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var productType: TextView? = null
        private var productPrice: TextView? = null
        private var productAddress: TextView? = null
        private var productDescription: TextView? = null
        private var productCarousel: ProductCarouselView? = null

        init {
            productType = view.findViewById(R.id.text_view_product_type)
            productPrice = view.findViewById(R.id.text_view_product_price)
            productAddress = view.findViewById(R.id.text_view_product_address)
            productCarousel = view.findViewById(R.id.carousel_view_product_row)
            productDescription = view.findViewById(R.id.text_view_product_description)
        }

        fun setup(
            type: String,
            price: String,
            description: String,
            imageList: List<String>,
            address: String
        ) {
            productType?.text = type
            productPrice?.text = price
            productDescription?.text = description
            productAddress?.text = address

            productCarousel?.setupView(imageList)
        }
    }
}
