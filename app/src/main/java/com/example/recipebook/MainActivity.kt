package com.example.recipebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipebook.presentation.screens.FavoritesScreen
import com.example.recipebook.presentation.screens.RecipeDetailScreen
import com.example.recipebook.presentation.screens.RecipeListScreen
import com.example.recipebook.presentation.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val viewModel: RecipeViewModel = hiltViewModel()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(16.dp))
                        Text("Книга рецептов", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineSmall)
                        NavigationDrawerItem(
                            label = { Text("Все рецепты") },
                            icon = { Icon(Icons.Default.Home, contentDescription = null) },
                            selected = false,
                            onClick = {
                                navController.navigate("home")
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(8.dp)
                        )
                        NavigationDrawerItem(
                            label = { Text("Избранное") },
                            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                            selected = false,
                            onClick = {
                                navController.navigate("favorites")
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Книга рецептов") },

                            actions = {

                                IconButton(onClick = { navController.navigate("favorites") }) {
                                    Icon(Icons.Default.Favorite, contentDescription = "Избранное")
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            RecipeListScreen(
                                viewModel = viewModel,
                                onRecipeClick = { id -> navController.navigate("detail/$id") }
                            )
                        }
                        composable("favorites") {
                            FavoritesScreen(
                                viewModel = viewModel,
                                onRecipeClick = { id -> navController.navigate("detail/$id") },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable(
                            "detail/{recipeId}",
                            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
                            RecipeDetailScreen(
                                viewModel = viewModel,
                                recipeId = recipeId,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}