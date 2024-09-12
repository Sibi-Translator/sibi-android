package com.example.signlanguagedetector

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.signlanguagedetector.data.AppDatabase
import com.example.signlanguagedetector.ui.screen.Intro
import com.example.signlanguagedetector.ui.screen.Login
import com.example.signlanguagedetector.ui.screen.MainMenu
import com.example.signlanguagedetector.ui.screen.QDictionary
import com.example.signlanguagedetector.ui.screen.Register
import com.example.signlanguagedetector.ui.screen.Screen
import com.example.signlanguagedetector.ui.screen.SignToText
import com.example.signlanguagedetector.ui.screen.Translation
import com.example.signlanguagedetector.ui.screen.WebViews
import com.example.signlanguagedetector.ui.theme.SignLanguageDetectorTheme
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in listOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO) && it.value == false)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT).show()
            } else {

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityResultLauncher.launch(
            arrayOf(Manifest.permission.CAMERA)
        )

        runBlocking {
            Global.init(context = this@MainActivity, activity = this@MainActivity)
        }

        setContent {
            SignLanguageDetectorTheme {
                val navController = rememberNavController()
                Surface(color = Color(0xffF7EDEC), modifier = Modifier.fillMaxSize()) {
                    NavHost(navController = navController, startDestination = Intro.pageTitle) {
                        for (screen in screenList) {
                            composable(screen.pageTitle) {
                                screen.ShowScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

val screenList = listOf<Screen>(
    Intro,
    MainMenu,
    Translation,
    QDictionary,
    WebViews,
    Login,
    Register
)