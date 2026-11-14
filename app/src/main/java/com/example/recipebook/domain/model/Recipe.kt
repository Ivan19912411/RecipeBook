package com.example.recipebook.domain.model

data class Recipe(
    val id: String,
    val name: String,
    val category: String = "",
    val area: String = "",
    val instructions: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false
)