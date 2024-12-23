package com.example.modernauppgift2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modernauppgift2.ui.theme.Modernauppgift2Theme
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Modernauppgift2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun App(name: String, modifier: Modifier = Modifier) {
    val viewModel: QuoteViewModel = viewModel()
    val currentQuote by viewModel.currentQuote.collectAsState()
    var currentScreen by remember { mutableStateOf("quote") }

    Box(modifier = Modifier.fillMaxSize()) {
        when (currentScreen) {
            "quote" -> QuoteScreen(currentQuote, { currentScreen = "author" })
            "author" -> AuthorScreen(currentQuote, { currentScreen = "quote" })
        }
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 50.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    viewModel.loadQuote()
                    currentScreen = "quote"
                },
                enabled = currentScreen != "author",
                modifier = Modifier
                    .padding(bottom = 300.dp)
            ) {
                Text(text = "New quote")
            }
        }
    }
}

@Composable
fun QuoteScreen(currentQuote: Quote?, onShowAuthor: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentQuote != null) {
            Text(text = currentQuote.quote)
            Button(
                onClick = onShowAuthor,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Show Author")
            }
        } else {
            Text("Get a quote!")
        }
    }
}

@Composable
fun AuthorScreen(currentQuote: Quote?, onShowQuote: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentQuote != null) {
            Text(text = " -  ${currentQuote.author}")
        }
            Button(
                onClick = onShowQuote,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Show Quote")
            }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Modernauppgift2Theme {
        App("Android")
    }
}