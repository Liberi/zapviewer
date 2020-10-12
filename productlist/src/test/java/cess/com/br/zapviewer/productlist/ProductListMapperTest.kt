package cess.com.br.zapviewer.productlist

import cess.com.br.zapviewer.network.model.*
import cess.com.br.zapviewer.productlist.mapper.ProductListMapper
import org.junit.Test

import org.junit.Assert.*

class ProductListMapperTest {
    @Test
    fun whenProductHasNoLatAndLong_shouldReturnEmptyList_ZAP() {
        val list = arrayListOf<ProductSourceResponse>()
        list.add(createInvalidListWithNoCoordinates("SALE"))

        val productList = ProductListMapper.map(list, "ZAP")

        assertEquals(productList.size, 0)
    }

    @Test
    fun whenProductIsCloseToZap_shouldIncreaseMinPrice() {
        val list = arrayListOf<ProductSourceResponse>()
        list.add(createValidListWithNoCoordinates("SALE", -23.553487, -46.657594))

        val productList = ProductListMapper.map(list, "ZAP")

        assertEquals(productList.size, 1)
    }

    @Test
    fun whenProductHasNoLatAndLong_shouldReturnEmptyList_REAL() {
        val list = arrayListOf<ProductSourceResponse>()
        list.add(createInvalidListWithNoCoordinates("SALE"))

        val productList = ProductListMapper.map(list, "REAL")

        assertEquals(productList.size, 0)
    }

    @Test
    fun whenProductHasLatAndLong_shouldReturnList_REAL() {
        val list = arrayListOf<ProductSourceResponse>()
        list.add(createValidListWithNoCoordinates("RENTAL"))

        val productList = ProductListMapper.map(list, "REAL")

        assertEquals(productList.size, 1)
    }

    @Test
    fun whenFarFromToZap_shouldReturnFalse() {
        val result = ProductListMapper.isCloseToZap(-25.553487, -41.657594)

        assertEquals(result, false)
    }

    @Test
    fun whenCloseZap_shouldReturnTrue() {
        val result = ProductListMapper.isCloseToZap(-23.568704, -46.693419)

        assertEquals(result, true)
    }

    private fun createInvalidListWithNoCoordinates(
        type: String,
        lat: Double = 0.0,
        long: Double = 0.0
    ) = ProductSourceResponse(
        usableAreas = 70,
        listingType = "USED",
        createdAt = "2017-04-22T18:39:31.138Z",
        listingStatus = "ACTIVE",
        id = "123123123123",
        parkingSpaces = 1,
        updatedAt = "2017-04-22T18:39:31.138Z",
        owner = false,
        images = emptyList(),
        address = Address("", "", GeoLocation("NO_GEOCODE", Location(long, lat))),
        bathrooms = 1,
        bedrooms = 1,
        pricingInfos = PricingInfos("60", "27600", type, "0")
    )

    private fun createValidListWithNoCoordinates(
        type: String,
        lat: Double = 1.0,
        long: Double = 1.0
    ) = ProductSourceResponse(
        usableAreas = 70,
        listingType = "USED",
        createdAt = "2017-04-22T18:39:31.138Z",
        listingStatus = "ACTIVE",
        id = "123123123123",
        parkingSpaces = 1,
        updatedAt = "2017-04-22T18:39:31.138Z",
        owner = false,
        images = emptyList(),
        address = Address("", "", GeoLocation("NO_GEOCODE", Location(long, lat))),
        bathrooms = 1,
        bedrooms = 1,
        pricingInfos = PricingInfos("60", "2760", type, "200")
    )

}