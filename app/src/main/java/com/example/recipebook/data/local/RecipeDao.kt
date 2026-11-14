package com.example.recipebook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM favorite_recipes")
    fun getFavorites(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM favorite_recipes WHERE id = :id")
    suspend fun getRecipeById(id: String): RecipeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(recipe: RecipeEntity)

    @Query("DELETE FROM favorite_recipes WHERE id = :id")
    suspend fun deleteFavorite(id: String)
}