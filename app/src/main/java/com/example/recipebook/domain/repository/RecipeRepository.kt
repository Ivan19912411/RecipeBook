package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun searchRecipes(query: String): List<Recipe>
    suspend fun getRecipeDetails(id: String): Recipe?
    fun getFavorites(): Flow<List<Recipe>>
    suspend fun toggleFavorite(recipe: Recipe)
    suspend fun isFavorite(id: String): Boolean
}