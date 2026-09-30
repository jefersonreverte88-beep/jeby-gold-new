package com.jebygold.new.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jebygold.new.ui.theme.*

data class MetricCard(
    val title: String,
    val value: String,
    val delta: String,
    val color: Color
)

data class QuickAction(
    val title: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen() {
    val metrics = listOf(
        MetricCard("Preço do Ouro", "R$ 358,70/g", "+1,42%", Green),
        MetricCard("Cofre", "18 itens", "+3 hoje", Gold),
        MetricCard("Faturamento", "R$ 148.520", "+12,5%", Green),
        MetricCard("PIX", "R$ 68.480", "ativo", Gold)
    )

    val actions = listOf(
        QuickAction("Cotação", Icons.Default.TrendingUp),
        QuickAction("Comprar", Icons.Default.ShoppingCart),
        QuickAction("Jeby", Icons.Default.Star),
        QuickAction("Frete", Icons.Default.LocalShipping),
        QuickAction("Faturamento", Icons.Default.PointOfSale),
        QuickAction("Cofre", Icons.Default.Security)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(16.dp)
    ) {
        // Cabeçalho
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "JEBY",
                    color = Gold,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    fontSize = 24.sp
                )
                Text(
                    text = "GOLD NEW",
                    color = TextGray,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
            }

            Surface(
                color = BlackSurfaceElevated,
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(10.dp).background(Green, shape = CircleShape))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Online", color = TextWhite, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card principal de preço
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = BlackSurface,
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text("Preço do Ouro", color = TextGray, fontSize = 12.sp)

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "R$ 358,70",
                        color = Gold,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("/g", color = TextGray, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Alta de 1,42% hoje",
                        color = Green,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text("24h", color = TextGray, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Cards de métricas
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(260.dp)
        ) {
            items(metrics) { item ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    color = BlackSurface,
                    shape = RoundedCornerShape(18.dp),
                    tonalElevation = 3.dp
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.title, color = TextGray, fontSize = 12.sp)
                        Text(
                            item.value,
                            color = TextWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Text(
                            item.delta,
                            color = item.color,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text("Ações rápidas", color = TextWhite, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        // Botões rápidos
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.height(200.dp)
        ) {
            items(actions) { action ->
                Surface(
                    color = BlackSurface,
                    shape = RoundedCornerShape(18.dp),
                    tonalElevation = 3.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = action.icon,
                            contentDescription = action.title,
                            tint = if (action.title == "Jeby") Gold else TextWhite,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = action.title,
                            color = if (action.title == "Jeby") Gold else TextWhite,
                            fontSize = 11.sp,
                            fontWeight = if (action.title == "Jeby") FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}
