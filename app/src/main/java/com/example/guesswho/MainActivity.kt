package com.example.guesswho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guesswho.view.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint                 // Hilt injection
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessWhoApp()
        }
    }
}

/* ----------  Navigation graph (minimal)  ---------- */
@Composable
private fun GuessWhoApp() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "main") {
        composable("main") {
            MainScreen(
                onNavigateCreate = { nav.navigate("create") },
                onNavigateJoin  = { nav.navigate("join") }
            )
        }
        /* placeholders so NavHost doesn't crash */
        composable("create") { Text("CREATE screen coming soon") }
        composable("join")   { Text("JOIN screen coming soon") }
    }
}