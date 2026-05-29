package com.ehterami.Koda.ui

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {
    @Serializable
    data object TaskList : Destination

    @Serializable
    data class TaskDetail(val taskId: Long? = null) : Destination

    @Serializable
    data object PrivacyPolicy : Destination
}
