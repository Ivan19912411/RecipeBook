package com.example.recipebook.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealResponse(
    @Json(name = "meals") val meals: List<MealDto>? = null
)

@JsonClass(generateAdapter = true)
data class MealDto(
    @Json(name = "idMeal") val id: String,
    @Json(name = "strMeal") val name: String,
    @Json(name = "strCategory") val category: String?,
    @Json(name = "strArea") val area: String?,
    @Json(name = "strInstructions") val instructions: String?,
    @Json(name = "strMealThumb") val imageUrl: String?
)