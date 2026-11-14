package com.example.recipebook.domain.usecase

import com.example.recipebook.domain.model.Recipe
import com.example.recipebook.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(query: String): List<Recipe> {
        if (query.isBlank()) return emptyList()
        return repository.searchRecipes(query)
    }
}