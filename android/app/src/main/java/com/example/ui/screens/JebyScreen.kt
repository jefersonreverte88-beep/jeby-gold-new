package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceHighlight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.ChatMessage
import com.example.viewmodel.JebyViewModel

@Composable
fun JebyScreen(viewModel: JebyViewModel) {
    var input by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "✨ Jeby",
                            fontWeight = FontWeight.Black,
                            color = GoldPrimary,
                            letterSpacing = 2.sp,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Assistente de Ouro",
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.clearChat() }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Limpar",
                            tint = GoldPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ObsidianSurface
                )
            )

            if (!error.isNullOrBlank()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        text = error ?: "",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        fontSize = 12.sp
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                if (messages.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.layout.Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "✨",
                                    fontSize = 56.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Olá! Sou a Jeby",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = GoldPrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Pergunte-me sobre ouro, investimentos ou qualquer coisa sobre JEBY Gold",
                                    color = TextSecondary,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }

                items(messages) { message ->
                    ChatBubble(message)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (loading) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = GoldPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Jeby está respondendo...",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    placeholder = { Text("Pergunte à Jeby...", fontSize = 12.sp) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = ObsidianSurfaceHighlight,
                        unfocusedContainerColor = ObsidianSurface,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                IconButton(
                    onClick = {
                        if (input.isNotBlank()) {
                            viewModel.sendMessage(input)
                            input = ""
                        }
                    },
                    enabled = !loading && input.isNotBlank(),
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = GoldPrimary,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = ObsidianBg,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.sender == "user") Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (message.sender == "user") GoldPrimary else ObsidianSurfaceHighlight,
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 2.dp
        ) {
            Text(
                text = message.message,
                modifier = Modifier.padding(12.dp),
                color = if (message.sender == "user") ObsidianBg else TextPrimary,
                fontSize = 13.sp
            )
        }
    }
}
