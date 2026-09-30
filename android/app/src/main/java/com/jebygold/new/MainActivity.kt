package com.jebygold.new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import com.jebygold.new.screens.JebyScreen
import com.jebygold.new.viewmodel.JebyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val viewModel = remember { JebyViewModel("jeby-user") }
                    JebyScreen(viewModel)
                }
            }
        }
    }
}
