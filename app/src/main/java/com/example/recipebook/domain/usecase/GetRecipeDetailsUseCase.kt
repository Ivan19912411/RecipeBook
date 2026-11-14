package com.example.recipebook.domain.usecase

import com.example.recipebook.domain.model.Recipe
import com.example.recipebook.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id: String): Recipe? {
        return repository.getRecipeDetails(id)
    }
}