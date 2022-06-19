package com.example.grandtask.profile.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserModel(

	@field:SerializedName("website")
	@Ignore
	var website: String? = null,

	@field:SerializedName("address")
	var address: Address? = null,

	@field:SerializedName("phone")
	@Ignore
	var phone: String? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("company")
	@Ignore
	var company: Company? = null,

	@field:SerializedName("id")
	@PrimaryKey
	var id: Int? = null,

	@field:SerializedName("email")
	@Ignore
	var email: String? = null,

	@field:SerializedName("username")
	var username: String? = null,
)

data class Geo(

	@field:SerializedName("lng")
	var lng: String? = null,

	@field:SerializedName("lat")
	var lat: String? = null
)

data class Address(

	@field:SerializedName("zipcode")
	var zipcode: String? = null,

	@field:SerializedName("geo")
	var geo: Geo? = null,

	@field:SerializedName("suite")
	var suite: String? = null,

	@field:SerializedName("city")
	var city: String? = null,

	@field:SerializedName("street")
	var street: String? = null
)

data class Company(

	@field:SerializedName("bs")
	var bs: String? = null,

	@field:SerializedName("catchPhrase")
	var catchPhrase: String? = null,

	@field:SerializedName("name")
	var name: String? = null
)
