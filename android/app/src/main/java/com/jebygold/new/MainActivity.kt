package com.jebygold.new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jebygold.new.ui.theme.Black
import com.jebygold.new.ui.theme.JebyGoldTheme
import com.jebygold.new.ui.screens.HomeScreen
import com.jebygold.new.ui.screens.JebyScreen
import com.jebygold.new.ui.components.BottomNavigation
import com.jebygold.new.ui.components.BottomTab
import com.jebygold.new.viewmodel.JebyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JebyGoldTheme {
                var selectedTab by remember { mutableStateOf(BottomTab.HOME) }
                val jebyViewModel = remember { JebyViewModel("jeby-user") }

                Scaffold(
                    containerColor = Black,
                    bottomBar = {
                        BottomNavigation(
                            selectedTab = selectedTab,
                            onTabSelected = { selectedTab = it }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Black)
                            .padding(innerPadding)
                    ) {
                        when (selectedTab) {
                            BottomTab.HOME -> HomeScreen()
                            BottomTab.COFRE -> HomeScreen()
                            BottomTab.JEBY -> JebyScreen(jebyViewModel)
                            BottomTab.CALC -> HomeScreen()
                            BottomTab.VENDAS -> HomeScreen()
                        }
                    }
                }
            }
        }
    }
}
