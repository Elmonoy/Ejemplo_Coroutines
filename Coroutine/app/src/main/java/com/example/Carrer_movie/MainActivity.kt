
package com.example.RaceTracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.RaceTracker.ui.AppCarrera
import com.example.RaceTracker.ui.theme.RaceTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            RaceTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppComienzo()
                }
            }
        }
    }
}
