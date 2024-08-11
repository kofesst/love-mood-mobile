package me.kofesst.lovemood.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import me.kofesst.lovemood.presentation.screens.home.HomeScreen
import me.kofesst.lovemood.presentation.screens.profile.UserProfileFormScreen
import me.kofesst.lovemood.presentation.screens.relationship.RelationshipFormScreen

/**
 * Объект навигации приложения.
 */
object AppNavigation {
    /**
     * Возвращает начальный экран приложения в зависимости от того,
     * есть ли у пользователя созданный профиль.
     *
     * [userHasProfile] - есть ли у пользователя созданный профиль.
     *
     * Возвращает:
     *
     * Если профиля нет - [ProfileForm.buildRoute].
     *
     * Если профиль есть - [Home.buildRoute].
     */
    fun getStartDestination(userHasProfile: Boolean) = when (userHasProfile) {
        true -> Home
        false -> ProfileForm
    }.buildRoute()

    /**
     * Список всех экранов приложения
     */
    val AllScreens
        get() = listOf(
            Home,
            ProfileForm,
            RelationshipForm
        )

    /**
     * Главный экран приложения.
     */
    object Home : AppScreen() {
        override val baseRoute: String = "home"

        @Composable
        override fun ComposableContent(
            modifier: Modifier,
            navBackStackEntry: NavBackStackEntry
        ) {
            HomeScreen(modifier)
        }
    }

    /**
     * Экран формы создания/редактирования профиля.
     */
    object ProfileForm : AppScreen() {
        override val baseRoute: String = "profile/form"

        val editingProfileIdArgument = IntArgument(name = "id", defaultValue = -1)
        override val arguments: List<AppScreenArgument<*>> = listOf(
            editingProfileIdArgument
        )

        @Composable
        override fun ComposableContent(
            modifier: Modifier,
            navBackStackEntry: NavBackStackEntry
        ) {
            val editingProfileId = rememberArgument(editingProfileIdArgument, navBackStackEntry)
            UserProfileFormScreen(
                modifier = modifier,
                editingProfileId = editingProfileId
            )
        }
    }

    /**
     * Экран формы создания/редактирования отношений.
     */
    object RelationshipForm : AppScreen() {
        override val baseRoute: String = "relationship/form"

        val editingRelationshipIdArgument = IntArgument(name = "id", defaultValue = -1)
        override val arguments: List<AppScreenArgument<*>> = listOf(
            editingRelationshipIdArgument
        )

        @Composable
        override fun ComposableContent(
            modifier: Modifier,
            navBackStackEntry: NavBackStackEntry
        ) {
            val editingRelationshipId = rememberArgument(
                editingRelationshipIdArgument, navBackStackEntry
            )
            RelationshipFormScreen(
                modifier = modifier,
                editingRelationshipId = editingRelationshipId
            )
        }
    }
}