package me.kofesst.lovemood.presentation

import me.kofesst.android.lovemood.navigation.AppDestination
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.android.lovemood.navigation.BoolArgument
import me.kofesst.android.lovemood.navigation.DestinationArgument
import me.kofesst.android.lovemood.navigation.IntArgument
import me.kofesst.lovemood.presentation.screens.home.HomeScreen
import me.kofesst.lovemood.presentation.screens.memory.form.MemoryFormScreen
import me.kofesst.lovemood.presentation.screens.memory.list.MemoriesOverviewScreen
import me.kofesst.lovemood.presentation.screens.profile.UserProfileFormScreen
import me.kofesst.lovemood.presentation.screens.relationship.RelationshipFormScreen

/**
 * Пункты назначения приложения.
 */
object Destinations {
    /**
     * Возвращает список всех пунктов назначения приложения.
     */
    fun getAllDestinations(): List<AppDestination> {
        return listOf(
            Home,
            ProfileForm,
            RelationshipForm
        )
    }

    /**
     * Возвращает начальный пункт назначения в зависимости от того,
     * создан ли профиль пользователя ([userHasProfile]).
     */
    fun getInitialDestination(userHasProfile: Boolean): AppDestination {
        return when (userHasProfile) {
            true -> Home
            false -> ProfileForm
        }
    }

    internal object Home : AppDestination(baseRoute = "home") {
        override val screen: AppScreen = HomeScreen
    }

    internal object ProfileForm : AppDestination(baseRoute = "profile/form") {
        val isEditingArgument = BoolArgument(
            name = "isEditing",
            defaultValue = false
        )

        override val arguments: List<DestinationArgument<*>> = listOf(
            isEditingArgument
        )

        override val screen: AppScreen = UserProfileFormScreen
    }

    internal object RelationshipForm : AppDestination(baseRoute = "relationship/form") {
        val isEditingArgument = BoolArgument(
            name = "isEditing",
            defaultValue = false
        )

        override val arguments: List<DestinationArgument<*>> = listOf(
            isEditingArgument
        )

        override val screen: AppScreen = RelationshipFormScreen
    }

    internal object MemoriesOverview : AppDestination(baseRoute = "memories") {
        override val screen: AppScreen = MemoriesOverviewScreen
    }

    internal object MemoryForm : AppDestination(baseRoute = "memories/form") {
        val editingIdArgument = IntArgument(
            name = "id",
            defaultValue = -1
        )

        override val arguments: List<DestinationArgument<*>> = listOf(
            editingIdArgument
        )

        override val screen: AppScreen = MemoryFormScreen
    }
}