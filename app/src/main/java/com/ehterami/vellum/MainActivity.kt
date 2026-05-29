package com.ehterami.vellum

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.ehterami.vellum.data.TaskRepository
import com.ehterami.vellum.data.VellumDatabase
import com.ehterami.vellum.ui.TaskViewModel
import com.ehterami.vellum.ui.TaskViewModelFactory
import com.ehterami.vellum.ui.VellumApp
import com.ehterami.vellum.ui.theme.VellumTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class MainActivity : ComponentActivity() {

    private val database by lazy { VellumDatabase.getDatabase(this) }
    private val repository by lazy { TaskRepository(database.taskDao()) }
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleIntent(intent)
        setContent {
            VellumTheme {
                VellumApp(viewModel = viewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            val name = intent.getStringExtra("name")
            val query = intent.getStringExtra("q")
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
            } else if (!query.isNullOrBlank()) {
                // You could implement a search/filter logic here in the future
                // For now, it just opens the app to the task list
            }
        }
    }
}
