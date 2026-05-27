package com.ehterami.vellum.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun VellumApp(
    viewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    val backStack = remember { mutableStateListOf<NavKey>(Destination.TaskList) }

    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val directive = remember(windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo)
            .copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(directive = directive)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        sceneStrategy = listDetailStrategy,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Destination.TaskList>(
                metadata = ListDetailSceneStrategy.listPane()
            ) {
                TaskListScreen(
                    viewModel = viewModel,
                    onTaskClick = { task ->
                        backStack.add(Destination.TaskDetail(task.id))
                    },
                    onAddTaskClick = {
                        backStack.add(Destination.TaskDetail())
                    }
                )
            }
            entry<Destination.TaskDetail>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) { destination ->
                TaskDetailScreen(
                    viewModel = viewModel,
                    taskId = destination.taskId,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
