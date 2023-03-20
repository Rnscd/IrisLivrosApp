package com.rnscd.irislivros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rnscd.irislivros.ui.BookshelfApp
import com.rnscd.irislivros.ui.theme.BookshelfAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookshelfAppTheme {
                BookshelfApp()
            }
        }
    }
}