package com.jebygold.new.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jebygold.new.data.service.JebyChatRequest
import com.jebygold.new.data.service.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String,
    val sender: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

class JebyViewModel(private val userId: String = "jeby-user") : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            _messages.value = _messages.value + ChatMessage(
                id = System.currentTimeMillis().toString(),
                sender = "user",
                message = text
            )

            _isLoading.value = true
            _error.value = null

            try {
                val response = RetrofitClient.jebyApi.sendMessage(
                    JebyChatRequest(
                        user_id = userId,
                        message = text
                    )
                )

                if (response.success && !response.message.isNullOrBlank()) {
                    _messages.value = _messages.value + ChatMessage(
                        id = (System.currentTimeMillis() + 1).toString(),
                        sender = "jeby",
                        message = response.message
                    )
                } else {
                    _error.value = response.error ?: "Erro desconhecido"
                }
            } catch (e: Exception) {
                _error.value = "Erro ao conectar com Jeby: ${e.message}"
                _messages.value = _messages.value + ChatMessage(
                    id = (System.currentTimeMillis() + 2).toString(),
                    sender = "jeby",
                    message = "Desculpe, Jeby não conseguiu responder agora."
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            try {
                RetrofitClient.jebyApi.clearChat(mapOf("user_id" to userId))
                _messages.value = emptyList()
            } catch (e: Exception) {
                _error.value = "Erro ao limpar conversa"
            }
        }
    }
}
