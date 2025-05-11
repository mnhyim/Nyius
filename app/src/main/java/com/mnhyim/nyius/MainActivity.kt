package com.mnhyim.nyius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mnhyim.nyius.ui.feature.home.HomeScreen
import com.mnhyim.nyius.ui.navigation.MainNavHost
import com.mnhyim.nyius.ui.theme.NyiusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NyiusTheme {
                MainNavHost(navController)
            }
        }
    }
}
