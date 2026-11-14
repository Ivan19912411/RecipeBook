package com.example.recipebook.data.repository

import com.example.recipebook.data.local.RecipeDao
import com.example.recipebook.data.local.RecipeEntity
import com.example.recipebook.data.remote.RecipeApi
import com.example.recipebook.domain.model.Recipe
import com.example.recipebook.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val dao: RecipeDao
) : RecipeRepository {

    override suspend fun searchRecipes(query: String): List<Recipe> {
        return api.searchRecipes(query).meals?.map {
            Recipe(it.id, it.name, it.category ?: "", it.area ?: "", it.instructions ?: "", it.imageUrl ?: "", isFavorite(it.id))
        } ?: emptyList()
    }

    override suspend fun getRecipeDetails(id: String): Recipe? {
        val dto = api.getRecipeDetails(id).meals?.firstOrNull() ?: return null
        return Recipe(dto.id, dto.name, dto.category ?: "", dto.area ?: "", dto.instructions ?: "", dto.imageUrl ?: "", isFavorite(id))
    }

    override fun getFavorites(): Flow<List<Recipe>> = dao.getFavorites().map { list ->
        list.map { Recipe(it.id, it.name, it.category ?: "", it.area ?: "", it.instructions ?: "", it.imageUrl ?: "", true) }
    }

    override suspend fun toggleFavorite(recipe: Recipe) {
        if (isFavorite(recipe.id)) dao.deleteFavorite(recipe.id)
        else dao.insertFavorite(RecipeEntity(recipe.id, recipe.name, recipe.category, recipe.area, recipe.instructions, recipe.imageUrl))
    }

    override suspend fun isFavorite(id: String): Boolean = dao.getRecipeById(id) != null
}