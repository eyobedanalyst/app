package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.data.database.EduDatabase
import com.example.data.repository.EduRepository
import com.example.ui.EduViewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Initialize Room Database and Repository
        val database = EduDatabase.getDatabase(this)
        val eduDao = database.eduDao()
        val repository = EduRepository(eduDao)

        setContent {
            // Keep theme state reactive dynamically
            var darkThemeEnabled by remember { mutableStateOf(false) }
            val systemInDarkTheme = isSystemInDarkTheme()

            // Initialize default preferences
            LaunchedEffect(systemInDarkTheme) {
                darkThemeEnabled = systemInDarkTheme
            }

            MyApplicationTheme(darkTheme = darkThemeEnabled) {
                val navController = rememberNavController()
                val viewModelFactory = remember { EduViewModel.provideFactory(repository) }
                val eduViewModel: EduViewModel = viewModel(factory = viewModelFactory)

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Show Bottom Nav on secondary branches as well
                val shouldShowBottomBar = currentRoute in listOf(
                    "home",
                    "academy?tab={tab}",
                    "courses",
                    "notes",
                    "more"
                ) || currentRoute == null

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentRoute == "home",
                                    onClick = {
                                        navController.navigate("home") {
                                            popUpTo("home") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (currentRoute == "home") Icons.Default.Home else Icons.Outlined.Home,
                                            contentDescription = "Dashboard"
                                        )
                                    },
                                    label = { Text("Dashboard") }
                                )

                                NavigationBarItem(
                                    selected = currentRoute?.startsWith("academy") == true,
                                    onClick = {
                                        navController.navigate("academy?tab=0") {
                                            popUpTo("home") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (currentRoute?.startsWith("academy") == true) Icons.Default.School else Icons.Outlined.School,
                                            contentDescription = "Academy"
                                        )
                                    },
                                    label = { Text("Academy") }
                                )

                                NavigationBarItem(
                                    selected = currentRoute == "courses",
                                    onClick = {
                                        navController.navigate("courses") {
                                            popUpTo("home") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (currentRoute == "courses") Icons.Default.RocketLaunch else Icons.Outlined.RocketLaunch,
                                            contentDescription = "Explore"
                                        )
                                    },
                                    label = { Text("Explore") }
                                )

                                NavigationBarItem(
                                    selected = currentRoute == "notes",
                                    onClick = {
                                        navController.navigate("notes") {
                                            popUpTo("home") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (currentRoute == "notes") Icons.Default.EditNote else Icons.Outlined.EditNote,
                                            contentDescription = "Study Desk"
                                        )
                                    },
                                    label = { Text("Workspace") }
                                )

                                NavigationBarItem(
                                    selected = currentRoute == "more",
                                    onClick = {
                                        navController.navigate("more") {
                                            popUpTo("home") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (currentRoute == "more") Icons.Default.Face else Icons.Outlined.Face,
                                            contentDescription = "Profile Hub"
                                        )
                                    },
                                    label = { Text("Profile") }
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                viewModel = eduViewModel,
                                onNavigateToGrade11 = { navController.navigate("academy?tab=0") },
                                onNavigateToGrade12 = { navController.navigate("academy?tab=1") },
                                onNavigateToCourses = { navController.navigate("courses") },
                                onNavigateToNotes = { navController.navigate("notes") },
                                onNavigateToLesson = { lessonId -> navController.navigate("lesson/$lessonId") },
                                onNavigateToAbout = { navController.navigate("about") }
                            )
                        }

                        composable(
                            route = "academy?tab={tab}",
                            arguments = listOf(navArgument("tab") { type = NavType.IntType; defaultValue = 0 })
                        ) { backStackEntry ->
                            val tab = backStackEntry.arguments?.getInt("tab") ?: 0
                            GradeScreens(
                                viewModel = eduViewModel,
                                initialTab = tab,
                                onNavigateToLesson = { lessonId -> navController.navigate("lesson/$lessonId") },
                                onNavigateToQuiz = { lessonId -> navController.navigate("quiz/$lessonId") },
                                onNavigateToPromo = { navController.navigate("courses") }
                            )
                        }

                        composable("courses") {
                            ExploreSmartTutorialScreen(viewModel = eduViewModel)
                        }

                        composable("notes") {
                            NotesScreen(
                                viewModel = eduViewModel,
                                onNavigateToLesson = { lessonId -> navController.navigate("lesson/$lessonId") }
                            )
                        }

                        composable("more") {
                            ProfileScreen(
                                viewModel = eduViewModel,
                                onNavigateToNotes = { navController.navigate("notes") },
                                onNavigateToSettings = { navController.navigate("settings") },
                                onNavigateToNotifications = { navController.navigate("notifications") },
                                onNavigateToHelp = { navController.navigate("help") },
                                onNavigateToAbout = { navController.navigate("about") }
                            )
                        }

                        composable(
                            route = "lesson/{lessonId}",
                            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""
                            LessonDetailScreen(
                                viewModel = eduViewModel,
                                lessonId = lessonId,
                                onNavigateBack = { navController.navigateUp() },
                                onNavigateToQuiz = { lid -> navController.navigate("quiz/$lid") }
                            )
                        }

                        composable(
                            route = "quiz/{lessonId}",
                            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""
                            QuizScreen(
                                viewModel = eduViewModel,
                                lessonId = lessonId,
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }

                        composable("settings") {
                            SettingsScreen(
                                viewModel = eduViewModel,
                                darkTheme = darkThemeEnabled,
                                onToggleTheme = { darkThemeEnabled = it },
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }

                        composable("notifications") {
                            NotificationsScreen(
                                viewModel = eduViewModel,
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }

                        composable("about") {
                            AboutScreen(
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }

                        composable("help") {
                            HelpSupportScreen(
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}
