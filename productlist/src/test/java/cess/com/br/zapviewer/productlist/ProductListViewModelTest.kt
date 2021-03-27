package cess.com.br.zapviewer.productlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cess.com.br.zapviewer.common.model.BusinessType
import cess.com.br.zapviewer.common.model.GeoLocation
import cess.com.br.zapviewer.common.model.Product
import cess.com.br.zapviewer.common.model.ProductAddress
import cess.com.br.zapviewer.network.BaseApi
import cess.com.br.zapviewer.network.model.Address
import cess.com.br.zapviewer.network.model.Location
import cess.com.br.zapviewer.network.model.PricingInfos
import cess.com.br.zapviewer.network.model.ProductSourceResponse
import cess.com.br.zapviewer.productlist.repository.ProductListRepository
import cess.com.br.zapviewer.productlist.ui.ProductListViewModel
import io.reactivex.Observer
import io.reactivex.Single
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProductListViewModelTest {
    @get:Rule
    open var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ProductListRepository

    @Mock
    private lateinit var baseApi: BaseApi

    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = ProductListRepository(baseApi)
        viewModel = ProductListViewModel(repository)
    }

    @Test
    @Ignore("")
    fun whenPullRequestEmitsResponse_shouldPostResponse() {
        Mockito.`when`(
            baseApi.fetchProducts()
        ).thenReturn(
            Single.just(
                listOf(getSourceProduct())
            )
        )

        Mockito.`when`(
            repository.getProductList("")
        ).thenReturn(
            Single.just(
                listOf(getProduct())
            )
        )

        viewModel.getProductList("")
        Assert.assertEquals(viewModel.result.value, listOf(getProduct()))
    }

    @Test
    @Ignore("")
    fun whenPullRequestEmitsError_shouldPostError() {
        Mockito.`when`(
            baseApi.fetchProducts()
        ).thenReturn(
            Single.just(
                listOf(getSourceProduct())
            )
        )

        Mockito.`when`(
            repository.getProductList(
                ""
            )
        ).thenReturn(
            Single.error(Exception("error"))
        )

        viewModel.getProductList("")
        Assert.assertEquals(viewModel.result.value, "error")
    }

    private fun getProduct() = Product(
        parkingSpaces = 0,
        bathrooms = 0,
        bedrooms = 0,
        images = emptyList(),
        address = ProductAddress("", "", GeoLocation("", 0.0, 0.0)),
        yearlyIptu = 0.0,
        condoFee = 0.0,
        totalPrice = 0.0,
        price = 0.0,
        type = BusinessType.RENTAL,
        usableAreas = 0
    )

    private fun getSourceProduct() = ProductSourceResponse(
        usableAreas = 70,
        listingType = "USED",
        createdAt = "2017-04-22T18:39:31.138Z",
        listingStatus = "ACTIVE",
        id = "123123123123",
        parkingSpaces = 1,
        updatedAt = "2017-04-22T18:39:31.138Z",
        owner = false,
        images = emptyList(),
        address = Address("", "", cess.com.br.zapviewer.network.model.GeoLocation("NO_GEOCODE", Location(0.0, 0.0))),
        bathrooms = 1,
        bedrooms = 1,
        pricingInfos = PricingInfos("60", "27600", "SALE", "0")
    )


}