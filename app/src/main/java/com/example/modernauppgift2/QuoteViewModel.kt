package com.example.modernauppgift2

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class QuoteViewModel: ViewModel() {

    private val _currentQuote = MutableStateFlow<Quote?>(null)
    val currentQuote: StateFlow<Quote?> = _currentQuote.asStateFlow()

    fun loadQuote(){
        Thread {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://dummyjson.com/quotes/random")
                .build()

            client.newCall(request).execute().use { response ->
                val jsonstring = response.body!!.string()
                Log.i("debug", jsonstring)
                val theQuote = Json { ignoreUnknownKeys = true}.decodeFromString<Quote>(jsonstring)
                _currentQuote.value = theQuote
            }
        }.start()
    }
}