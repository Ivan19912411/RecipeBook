package com.example.recipebook.data.remote

import com.example.recipebook.data.remote.dto.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {
    @GET("search.php")
    suspend fun searchRecipes(@Query("s") query: String): MealResponse


    @GET("search.php")
    suspend fun searchRecipesByLetter(@Query("f") letter: String): MealResponse

    @GET("lookup.php")
    suspend fun getRecipeDetails(@Query("i") id: String): MealResponse
}