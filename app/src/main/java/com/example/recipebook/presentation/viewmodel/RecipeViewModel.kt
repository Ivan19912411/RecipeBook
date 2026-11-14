package com.example.recipebook.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.model.Recipe
import com.example.recipebook.domain.repository.RecipeRepository
import com.example.recipebook.domain.usecase.GetRecipeDetailsUseCase
import com.example.recipebook.domain.usecase.GetRecipesUseCase
import com.example.recipebook.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val repository: RecipeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()

    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe = _selectedRecipe.asStateFlow()

    val favorites = repository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun searchRecipes() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _recipes.value = getRecipesUseCase(_searchQuery.value)
            } catch (e: Exception) {
                _error.value = "Ошибка сети: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadRecipeDetails(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _selectedRecipe.value = getRecipeDetailsUseCase(id)
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            toggleFavoriteUseCase(recipe)
            if (_selectedRecipe.value?.id == recipe.id) {
                _selectedRecipe.value = _selectedRecipe.value?.copy(isFavorite = !recipe.isFavorite)
            }
        }
    }
}