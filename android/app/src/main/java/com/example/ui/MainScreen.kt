package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceElevated
import com.example.ui.theme.ObsidianSurfaceHighlight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.screens.JebyScreen
import com.example.viewmodel.JebyViewModel

enum class JebyNavTab(val title: String, val icon: ImageVector) {
    MERCADO("Mercado", Icons.Default.TrendingUp),
    COFRE("Cofre", Icons.Default.Security),
    JEBY_VIP("JEBY", Icons.Default.Star),
    CALCULADORA("Calculadora", Icons.Default.Calculate),
    FATURAMENTO("Vendas", Icons.Default.PointOfSale)
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(JebyNavTab.MERCADO) }
    val jebyViewModel = remember { JebyViewModel("jeby-user") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ObsidianBg,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "JEBY",
                            fontWeight = FontWeight.Black,
                            color = GoldPrimary,
                            letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "GOLD",
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            letterSpacing = 2.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ObsidianSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = ObsidianSurfaceElevated,
                tonalElevation = 8.dp
            ) {
                JebyNavTab.values().forEach { tab ->
                    val isSelected = selectedTab == tab
                    val isCenterJeby = tab == JebyNavTab.JEBY_VIP

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedTab = tab },
                        icon = {
                            if (isCenterJeby) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) GoldPrimary else ObsidianSurfaceHighlight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = tab.title,
                                        tint = if (isSelected) ObsidianBg else GoldPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title
                                )
                            }
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontWeight = if (isCenterJeby || isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 10.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ObsidianBg,
                            selectedTextColor = GoldPrimary,
                            indicatorColor = if (isCenterJeby) GoldPrimary else GoldAccent.copy(alpha = 0.2f),
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ObsidianBg),
            contentAlignment = Alignment.Center
        ) {
            when (selectedTab) {
                JebyNavTab.MERCADO -> Text("Painel de Cotação ao Vivo", color = TextPrimary)
                JebyNavTab.COFRE -> Text("Cofre Particular de Barras e Joias", color = TextPrimary)
                JebyNavTab.JEBY_VIP -> JebyScreen(jebyViewModel)
                JebyNavTab.CALCULADORA -> Text("Calculadora de Quilates e Avaliação", color = TextPrimary)
                JebyNavTab.FATURAMENTO -> Text("Mesa de Faturamento PIX & WhatsApp", color = TextPrimary)
            }
        }
    }
}
