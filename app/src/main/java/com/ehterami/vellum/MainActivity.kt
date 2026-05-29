package com.ehterami.Koda

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ehterami.Koda.data.TaskRepository
import com.ehterami.Koda.data.KodaDatabase
import com.ehterami.Koda.ui.TaskViewModel
import com.ehterami.Koda.ui.TaskViewModelFactory
import com.ehterami.Koda.ui.KodaApp
import com.ehterami.Koda.ui.theme.KodaTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class MainActivity : ComponentActivity() {

    private val database by lazy { KodaDatabase.getDatabase(this) }
    private val repository by lazy { TaskRepository(database.taskDao()) }
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleIntent(intent)
        setContent {
            KodaTheme {
                KodaApp(viewModel = viewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_VIEW -> {
                val name = intent.getStringExtra("name")
                val description = intent.getStringExtra("description") ?: ""
                val dueDateStr = intent.getStringExtra("dueDate")
                var dueDate: Long? = null

                if (!dueDateStr.isNullOrBlank()) {
                    dueDate = try {
                        ZonedDateTime.parse(dueDateStr, DateTimeFormatter.ISO_DATE_TIME)
                            .toInstant()
                            .toEpochMilli()
                    } catch (e: DateTimeParseException) {
                        null
                    }
                }

                if (!name.isNullOrBlank()) {
                    viewModel.addTask(name, description, dueDate)
                }
            }
            Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
                    if (!sharedText.isNullOrBlank()) {
                        viewModel.addTask(sharedText)
                    }
                }
            }
            "com.google.android.gms.actions.CREATE_NOTE" -> {
                val text = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (!text.isNullOrBlank()) {
                    viewModel.addTask(text)
                }
            }
        }
    }
}
