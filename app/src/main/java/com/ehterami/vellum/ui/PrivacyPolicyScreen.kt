package com.ehterami.vellum.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Privacy Policy",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                Text(
                    "This privacy policy applies to the Vellum app (herein referred to as \"Application\") for mobile devices that was created by (Your Name/Company) as a Free service."
                )
            }
            item {
                PolicySection(
                    title = "1. Data Collection and Use",
                    content = "The Application collects and stores tasks, descriptions, and due dates locally on your device using a Room database. This data is not transmitted to any external servers unless explicitly initiated by the user."
                )
            }
            item {
                PolicySection(
                    title = "2. Camera and Media Access",
                    content = "The Application may request access to your device's camera to allow you to attach images to your tasks. These images are stored locally on your device."
                )
            }
            item {
                PolicySection(
                    title = "3. Voice and App Actions",
                    content = "The Application integrates with Google Assistant (App Actions). When you use voice commands to create tasks, Google processes your voice input and sends the interpreted text to the Application."
                )
            }
            item {
                PolicySection(
                    title = "4. Third Party Access",
                    content = "The Application uses third party services that have their own Privacy Policy. For example: Google Play Services."
                )
            }
            item {
                PolicySection(
                    title = "5. Security",
                    content = "We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it."
                )
            }
            item {
                PolicySection(
                    title = "6. Contact Us",
                    content = "If you have any questions about our Privacy Policy, do not hesitate to contact us at: [Your Email Address]"
                )
            }
        }
    }
}

@Composable
fun PolicySection(title: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            content,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
