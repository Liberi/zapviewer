package cess.com.br.zapviewer.network.model

import com.google.gson.annotations.SerializedName

class ProductSourceResponse (
	@SerializedName("usableAreas") val usableAreas : Int,
	@SerializedName("listingType") val listingType : String,
	@SerializedName("createdAt") val createdAt : String,
	@SerializedName("listingStatus") val listingStatus : String,
	@SerializedName("id") val id : String,
	@SerializedName("parkingSpaces") val parkingSpaces : Int,
	@SerializedName("updatedAt") val updatedAt : String,
	@SerializedName("owner") val owner : Boolean,
	@SerializedName("images") val images : List<String>,
	@SerializedName("address") val address : Address,
	@SerializedName("bathrooms") val bathrooms : Int,
	@SerializedName("bedrooms") val bedrooms : Int,
	@SerializedName("pricingInfos") val pricingInfos : PricingInfos
)

class Address (
	@SerializedName("city") val city : String,
	@SerializedName("neighborhood") val neighborhood : String,
	@SerializedName("geoLocation") val geoLocation : GeoLocation
)

data class GeoLocation (
	@SerializedName("precision") val precision : String,
	@SerializedName("location") val location : Location
)

data class Location (
	@SerializedName("lon") val lon : Int,
	@SerializedName("lat") val lat : Int
)

data class PricingInfos (
	@SerializedName("yearlyIptu") val yearlyIptu : Int,
	@SerializedName("price") val price : Int,
	@SerializedName("businessType") val businessType : String,
	@SerializedName("monthlyCondoFee") val monthlyCondoFee : Int
)