package cess.com.br.zapviewer.common.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class Product(
    val parkingSpaces: Int,
    val bathrooms: Int,
    val bedrooms: Int,
    val images: List<String>,
    val address: ProductAddress,
    val yearlyIptu: Double,
    val condoFee: Double,
    val totalPrice: Double,
    val price: Double,
    val type: BusinessType,
    val usableAreas: Int
) : Parcelable

@Parcelize
class ProductAddress(
    val city: String,
    val neighborhood: String,
    val geoLocation: GeoLocation
) : Parcelable

@Parcelize
class GeoLocation(
    val precision: String,
    val lon: Double,
    val lat: Double
) : Parcelable

enum class BusinessType(val typeName: String) {
    SALE("SALE"),
    RENTAL("RENTAL");
}
