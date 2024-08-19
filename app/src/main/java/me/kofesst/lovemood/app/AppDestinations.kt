package me.kofesst.lovemood.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import me.kofesst.android.lovemood.navigation.AppDestination
import me.kofesst.android.lovemood.navigation.AppMainDestination
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.android.lovemood.navigation.DestinationArgument
import me.kofesst.android.lovemood.navigation.IntArgument
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.presentation.app.dictionary
import me.kofesst.lovemood.presentation.screens.app.AboutAppScreen
import me.kofesst.lovemood.presentation.screens.app.todos.AppTodosScreen
import me.kofesst.lovemood.presentation.screens.home.HomeScreen
import me.kofesst.lovemood.presentation.screens.memory.calendar.MemoriesCalendarScreen
import me.kofesst.lovemood.presentation.screens.memory.form.MemoryFormScreen
import me.kofesst.lovemood.presentation.screens.memory.list.MemoriesListScreen
import me.kofesst.lovemood.presentation.screens.profile.UserProfileFormScreen
import me.kofesst.lovemood.presentation.screens.relationship.RelationshipFormScreen

object AppDestinations {
    val All
        get() = listOf(
            Home,
            Memories.List, Memories.Calendar,
            App.About, App.Todos,
            Forms.UserProfile, Forms.Relationship, Forms.Memory
        )

    val Main
        get() = All.mapNotNull { it as? AppMainDestination }

    object Home : AppMainDestination(baseRoute = "home") {
        override val screen: AppScreen get() = HomeScreen

        override val icon: ImageVector
            get() = Icons.Rounded.Home

        override val titleProducer: @Composable () -> AppTextHolder
            get() = { dictionary.screens.home.bottomBarTitle }
    }

    object Memories {
        object List : AppDestination(baseRoute = "memories") {
            override val screen: AppScreen get() = MemoriesListScreen
        }

        object Calendar : AppDestination(baseRoute = "memories/calendar") {
            override val screen: AppScreen get() = MemoriesCalendarScreen
        }
    }

    object Forms {
        object UserProfile : AppDestination(baseRoute = "profile/user/form") {
            val editingIdArgument = IntArgument(
                name = "editing_id",
                defaultValue = -1
            )

            override val arguments: List<DestinationArgument<*>>
                get() = listOf(editingIdArgument)

            override val screen: AppScreen get() = UserProfileFormScreen
        }

        object Relationship : AppDestination(baseRoute = "relationship/form") {
            val editingIdArgument = IntArgument(
                name = "editing_id",
                defaultValue = -1
            )

            override val arguments: List<DestinationArgument<*>>
                get() = listOf(UserProfile.editingIdArgument)

            override val screen: AppScreen get() = RelationshipFormScreen
        }

        object Memory : AppDestination(baseRoute = "memories/form") {
            val editingIdArgument = IntArgument(
                name = "editing_id",
                defaultValue = -1
            )

            override val arguments: List<DestinationArgument<*>>
                get() = listOf(UserProfile.editingIdArgument)

            override val screen: AppScreen get() = MemoryFormScreen
        }
    }

    object App {
        object About : AppMainDestination(baseRoute = "app/about") {
            override val screen: AppScreen get() = AboutAppScreen

            override val icon: ImageVector
                get() = Icons.Rounded.PhoneAndroid

            override val titleProducer: @Composable () -> AppTextHolder
                get() = { dictionary.screens.app.bottomBarTitle }
        }

        object Todos : AppDestination(baseRoute = "app/todos") {
            override val screen: AppScreen get() = AppTodosScreen
        }
    }
}