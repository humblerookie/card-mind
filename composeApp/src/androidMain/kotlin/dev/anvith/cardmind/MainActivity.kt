package dev.anvith.cardmind

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.anvith.cardmind.screens.DetailScreen
import dev.anvith.cardmind.screens.ListScreen
import dev.anvith.cardmind.ui.res.AppTypography
import dev.anvith.cardmind.ui.res.appDarkScheme
import dev.anvith.cardmind.ui.res.appLightScheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Remove when https://issuetracker.google.com/issues/364713509 is fixed
            LaunchedEffect(isSystemInDarkTheme()) {
                enableEdgeToEdge()
            }
            AppTheme {
                App()
            }

        }
    }
}

@Composable
fun App() {
    Surface {
        NavigationContent()
    }
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> appDarkScheme
        else -> appLightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable
fun NavigationContent() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(navigateToDetails = { objectId ->
                navController.navigate("details/$objectId")
            })
        }
        composable(
            "details/{objectId}",
            arguments = listOf(navArgument("objectId") { type = NavType.IntType })
        ) { backstack ->
            DetailScreen(
                objectId = backstack.arguments?.getInt("objectId")!!,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
