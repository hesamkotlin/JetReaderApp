package com.example.jetreaderapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetreaderapp.ui.theme.JetReaderAppTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetReaderAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    val db = FirebaseFirestore.getInstance()
    val user : MutableMap<String , Any> = HashMap()
    user["firstName"] = " Hesam"
    user["lastName"] = " Naderi"

    db.collection("Users").add(user).addOnSuccessListener {
        Log.d("FB", "Greeting: ${it.id}")
    }.addOnFailureListener{
        Log.d("FB", "Greeting: $it")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetReaderAppTheme {
        Greeting("Android")
    }
}