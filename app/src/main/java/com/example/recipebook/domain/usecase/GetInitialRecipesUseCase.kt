package com.example.recipebook.domain.usecase

import com.example.recipebook.domain.model.Recipe
import com.example.recipebook.domain.repository.RecipeRepository
import javax.inject.Inject

class GetInitialRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): List<Recipe> {
        return repository.getInitialRecipes()
    }
}