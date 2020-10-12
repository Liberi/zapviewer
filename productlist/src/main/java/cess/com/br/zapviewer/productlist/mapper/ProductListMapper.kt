package cess.com.br.zapviewer.productlist.mapper

import cess.com.br.zapviewer.common.extension.toDoubleOrZero
import cess.com.br.zapviewer.common.model.BusinessType
import cess.com.br.zapviewer.common.model.GeoLocation
import cess.com.br.zapviewer.common.model.Product
import cess.com.br.zapviewer.common.model.ProductAddress
import cess.com.br.zapviewer.network.model.PricingInfos
import cess.com.br.zapviewer.network.model.ProductSourceResponse

object ProductListMapper {
    fun map(response: List<ProductSourceResponse>, portalFlavor: String): List<Product> {
        val listOfProduct = arrayListOf<Product>()

        response.forEach {

            val productAddress = ProductAddress(
                city = it.address.city,
                neighborhood = it.address.neighborhood,
                geoLocation = GeoLocation(
                    precision = it.address.geoLocation.precision,
                    lon = it.address.geoLocation.location.lon,
                    lat = it.address.geoLocation.location.lat
                )
            )

            val product = Product(
                parkingSpaces = it.parkingSpaces,
                bathrooms = it.bathrooms,
                bedrooms = it.bedrooms,
                images = it.images,
                address = productAddress,
                yearlyIptu = it.pricingInfos.yearlyIptu.toDoubleOrZero(),
                condoFee = it.pricingInfos.monthlyCondoFee.toDoubleOrZero(),
                price = it.pricingInfos.price.toDoubleOrZero(),
                totalPrice = getTotalPrice(it.pricingInfos),
                type = BusinessType.valueOf(it.pricingInfos.businessType),
                usableAreas = it.usableAreas
            )

            listOfProduct.add(product)
        }


        return applyRules(listOfProduct, portalFlavor)
    }

    private fun applyRules(productList: List<Product>, productFlavor: String): List<Product> {
        return if (productFlavor == "ZAP") {
            productList.filter {
                it.address.geoLocation.lat != 0.0 &&
                        it.address.geoLocation.lon != 0.0 &&
                        it.usableAreas > 0 &&
                        isSquareFeetPriceOverMinimum(
                            it.usableAreas,
                            it.price,
                            it.type,
                            it.address.geoLocation
                        )
            }
        } else {
            productList.filter {
                it.address.geoLocation.lat != 0.0 &&
                        it.address.geoLocation.lon != 0.0 &&
                        it.condoFee > 0 &&
                        isCondoFeeOverMinimum(
                            it.condoFee,
                            it.price,
                            it.type,
                            it.address.geoLocation
                        )
            }
        }
    }

    private fun isSquareFeetPriceOverMinimum(
        usableArea: Int,
        price: Double,
        type: BusinessType,
        geo: GeoLocation
    ): Boolean {
        if (type == BusinessType.RENTAL)
            return true

        val minPrice = 3500

        val minimumPrice = if (isCloseToZap(geo.lat, geo.lon)) {
            minPrice - (minPrice * 0.1)
        } else {
            price
        }

        val squareFeetValue = price / usableArea.toDouble()
        return squareFeetValue < minimumPrice
    }

    private fun isCondoFeeOverMinimum(
        condoPrice: Double,
        rentPrice: Double,
        type: BusinessType,
        geo: GeoLocation
    ): Boolean {
        if (type == BusinessType.SALE)
            return true

        val maxPercentage = if (isCloseToZap(geo.lat, geo.lon)) {
            0.5
        } else {
            0.3
        }

        val condoMaxFee = rentPrice * maxPercentage
        return condoPrice < condoMaxFee
    }

    fun isCloseToZap(lat: Double, long: Double): Boolean {
        val minLon = -46.693419
        val minLat = -23.568704
        val maxLon = -46.641146
        val maxLat = -23.546686

        return lat in minLat..maxLat && long in minLon..maxLon
    }

    private fun getTotalPrice(priceInfo: PricingInfos) =
        priceInfo.price.toDoubleOrZero() +
                priceInfo.monthlyCondoFee.toDoubleOrZero() +
                priceInfo.yearlyIptu.toDoubleOrZero()


}