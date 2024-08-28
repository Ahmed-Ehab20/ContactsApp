package com.example.contactsapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.contactsapp.ui.theme.ContactsAppTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.contactsapp.model.contacts

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactsAppTheme {
                val context = LocalContext.current
                Scaffold(topBar = {
                    TopAppBar(title = { Text(text = "Contacts App") }, actions = {
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:+1234567890")
                            }
                            context.startActivity(intent)
                        }) {
                            Icon(Icons.Default.Home, contentDescription = "Home Icon")
                        }
                    })
                }) { innerPadding ->
                    ContactsScreen(innerPadding, context)
                }
            }
        }
    }
}

@Composable
fun ContactItem(name: String, phoneNumber: String, image: Painter, onClick: (String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(phoneNumber) }
        .background(Color.LightGray)
        .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = image,
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        SelectablePhoneNumber(phoneNumber)
    }
}

@Composable
fun SelectablePhoneNumber(phoneNumber: String) {
    val context = LocalContext.current
    BasicText(text = phoneNumber,
        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
        modifier = Modifier
            .background(Color.Transparent)
            .clickable {
                // Copy to clipboard functionality
                val clipboardManager =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("phoneNumber", phoneNumber)
                clipboardManager.setPrimaryClip(clip)
                Toast
                    .makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT)
                    .show()
            }
            .padding(horizontal = 8.dp))
}

@Composable
fun ContactsScreen(paddingValues: PaddingValues, context: Context) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(contacts.size) { index ->
            val contact = contacts[index]
            ContactItem(name = contact.name,
                phoneNumber = contact.phoneNumber,
                image = painterResource(id = contact.imageResId),
                onClick = { number ->
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$number")
                    }
                    context.startActivity(intent)
                })
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ContactScreenPreview() {
    ContactsScreen(paddingValues = PaddingValues(), context = LocalContext.current)
}