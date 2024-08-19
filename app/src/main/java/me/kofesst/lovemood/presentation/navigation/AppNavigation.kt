package me.kofesst.lovemood.presentation.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.presentation.screens.app.AboutAppScreen
import me.kofesst.lovemood.presentation.screens.app.todos.AppTodosScreen
import me.kofesst.lovemood.presentation.screens.home.HomeScreen
import me.kofesst.lovemood.presentation.screens.memory.calendar.MemoriesCalendarScreen
import me.kofesst.lovemood.presentation.screens.memory.form.MemoryFormScreen
import me.kofesst.lovemood.presentation.screens.memory.list.MemoriesScreen
import me.kofesst.lovemood.presentation.screens.profile.UserProfileFormScreen
import me.kofesst.lovemood.presentation.screens.relationship.RelationshipFormScreen
import me.kofesst.lovemood.ui.text.ResourceText

/**
 * Объект навигации приложения.
 */
object AppNavigation {
    /**
     * Главный экран приложения.
     */
    object Home : AppMainScreen(baseRoute = "home") {
        override val bottomBarIcon: ImageVector
            get() = Icons.Outlined.Home

        override val bottomBarTitleProducer: (Context) -> AppTextHolder
            get() = { context -> ResourceText(R.string.bottom_bar__home_title, context) }

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
    object ProfileForm : AppScreen(baseRoute = "profile/form") {
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
    object RelationshipForm : AppScreen(baseRoute = "relationship/form") {
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

    object MemoryForm : AppScreen(baseRoute = "memories/form") {
        val editingMemoryIdArgument = IntArgument(name = "id", defaultValue = -1)
        override val arguments: List<AppScreenArgument<*>> = listOf(
            editingMemoryIdArgument
        )

        @Composable
        override fun ComposableContent(
            modifier: Modifier,
            navBackStackEntry: NavBackStackEntry
        ) {
            val editingMemoryId = rememberArgument(
                editingMemoryIdArgument, navBackStackEntry
            )
            MemoryFormScreen(
                modifier = modifier,
                editingMemoryId = editingMemoryId
            )
        }
    }

    object AboutAppScreen : AppMainScreen(baseRoute = "app") {
        override val bottomBarIcon: ImageVector
            get() = Icons.Outlined.Android

        override val bottomBarTitleProducer: (Context) -> AppTextHolder
            get() = { context -> ResourceText(R.string.bottom_bar__app_title, context) }

        @Composable
        override fun ComposableContent(
            modifier: Modifier,
            navBackStackEntry: NavBackStackEntry
        ) {
            AboutAppScreen(modifier)
        }
    }

    object AppTodosScreen : AppScreen(baseRoute = "app/todos") {
        @Composable
        override fun ComposableContent(
            modifier: Modifier,
            navBackStackEntry: NavBackStackEntry
        ) {
            AppTodosScreen(modifier)
        }
    }

    object MemoriesScreen : AppScreen(baseRoute = "memories") {
        @Composable
        override fun ComposableContent(
            modifier: Modifier,
            navBackStackEntry: NavBackStackEntry
        ) {
            MemoriesScreen(
                modifier = modifier,
                navBackStackEntry = navBackStackEntry
            )
        }
    }

    object MemoriesCalendarScreen : AppScreen(baseRoute = "memories/calendar") {
        @Composable
        override fun ComposableContent(
            modifier: Modifier,
            navBackStackEntry: NavBackStackEntry
        ) {
            MemoriesCalendarScreen(
                modifier = modifier,
                navBackStackEntry = navBackStackEntry
            )
        }
    }

    /**
     * Возвращает начальный экран приложения в зависимости от того,
     * есть ли у пользователя созданный профиль.
     *
     * [userHasProfile] - есть ли у пользователя созданный профиль.
     *
     * Возвращает:
     *
     * Если профиля нет - [ProfileForm.route].
     *
     * Если профиль есть - [Home.route].
     */
    fun getStartDestination(userHasProfile: Boolean) = when (userHasProfile) {
        true -> Home
        false -> ProfileForm
    }.route

    /**
     * Список всех экранов приложения
     */
    val AllScreens = listOf(
        Home,
        ProfileForm,
        RelationshipForm,
        MemoryForm,
        AboutAppScreen,
        AppTodosScreen,
        MemoriesScreen,
        MemoriesCalendarScreen
    )

    /**
     * Возвращает список экранов приложения, находящихся
     * в нижней панели приложения.
     *
     * [appContext] - контекс приложения.
     */
    fun getBottomBarScreens(appContext: Context): List<BottomBarScreenItem> {
        return AllScreens.mapNotNull {
            val asMainScreen = it as? AppMainScreen
            asMainScreen?.let { mainScreen ->
                BottomBarScreenItem(
                    title = mainScreen.bottomBarTitleProducer(appContext),
                    icon = mainScreen.bottomBarIcon,
                    destination = it
                )
            }
        }
    }
}