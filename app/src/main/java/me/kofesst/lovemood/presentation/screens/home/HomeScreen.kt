package me.kofesst.lovemood.presentation.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.valentinilk.shimmer.shimmer
import me.kofesst.android.lovemood.navigation.AppScreen
import me.kofesst.lovemood.app.AppDestinations
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.LocalMainActivity
import me.kofesst.lovemood.app.LocalShimmer
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.async.asyncValueContent
import me.kofesst.lovemood.async.requiredAsyncValueContent
import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.ui.components.action.PanelButton
import me.kofesst.lovemood.core.ui.components.action.PanelButtonDefaults
import me.kofesst.lovemood.core.ui.components.lottie.LottieFile
import me.kofesst.lovemood.core.ui.components.lottie.LottieSize
import me.kofesst.lovemood.core.ui.utils.ByteArrayImage
import me.kofesst.lovemood.core.ui.utils.mergeWithStatusBar
import me.kofesst.lovemood.core.ui.utils.statusBarPadding
import me.kofesst.lovemood.presentation.screens.home.sections.EventsSection
import me.kofesst.lovemood.presentation.screens.home.sections.LoveDurationSection
import me.kofesst.lovemood.presentation.screens.home.sections.MemoriesSection
import me.kofesst.lovemood.presentation.screens.home.sections.ShimmerSection
import me.kofesst.lovemood.ui.AvatarPlaceholder
import me.kofesst.lovemood.ui.LottieResources
import me.kofesst.lovemood.ui.RelationshipAvatars
import me.kofesst.lovemood.ui.theme.WithShimmerEffect

object HomeScreen : AppScreen() {
    @Composable
    override fun ScreenContent(
        modifier: Modifier,
        navBackStackEntry: NavBackStackEntry
    ) {
        val mainActivity = LocalMainActivity.current
        val viewModel = hiltViewModel<HomeViewModel>(viewModelStoreOwner = mainActivity)
        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        Content(modifier, viewModel)
    }

    @Composable
    private fun Content(
        modifier: Modifier = Modifier,
        viewModel: HomeViewModel
    ) {
        val asyncProfile by viewModel.userProfileState
        val asyncRelationship by viewModel.relationshipState
        val asyncMemories by viewModel.memoriesState
        WithShimmerEffect {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(all = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                buildAsyncHeader(
                    asyncProfile = asyncProfile,
                    asyncRelationship = asyncRelationship
                )
                buildBody(
                    userProfile = asyncProfile.value,
                    asyncRelationship = asyncRelationship,
                    asyncMemories = asyncMemories
                )
            }
        }
    }

    //region Screen header

    private fun LazyListScope.buildAsyncHeader(
        asyncProfile: me.kofesst.lovemood.async.AsyncValue<Profile>,
        asyncRelationship: me.kofesst.lovemood.async.AsyncValue<Relationship>
    ) {
        asyncValueContent(
            asyncValue = asyncRelationship,
            onLoading = {
                updateTopBar {
                    ShimmerScreenHeader(Modifier.fillMaxWidth())
                }
            },
            onLoaded = { relationship ->
                if (relationship == null) {
                    buildProfileHeader(asyncProfile)
                } else {
                    updateTopBar {
                        val appState = LocalAppState.current
                        RelationshipScreenHeader(
                            modifier = Modifier.fillMaxWidth(),
                            relationship = relationship,
                            onEditProfileClick = {
                                appState.navigate(
                                    destination = AppDestinations.Forms.UserProfile,
                                    argumentValues = arrayOf(
                                        AppDestinations.Forms.UserProfile.editingIdArgument
                                                to relationship.userProfile.id
                                    )
                                )
                            },
                            onEditRelationshipClick = {
                                appState.navigate(
                                    destination = AppDestinations.Forms.Relationship,
                                    argumentValues = arrayOf(
                                        AppDestinations.Forms.Relationship.editingIdArgument
                                                to relationship.id
                                    )
                                )
                            }
                        )
                    }
                }
            }
        )
    }

    private fun LazyListScope.buildProfileHeader(
        asyncProfile: me.kofesst.lovemood.async.AsyncValue<Profile>
    ) {
        requiredAsyncValueContent(
            asyncValue = asyncProfile,
            onLoading = {
                updateTopBar {
                    ShimmerScreenHeader(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            onLoaded = { profile ->
                updateTopBar {
                    val appState = LocalAppState.current
                    ProfileScreenHeader(
                        modifier = Modifier.fillMaxWidth(),
                        profile = profile,
                        onEditProfileClick = {
                            appState.navigate(
                                destination = AppDestinations.Forms.UserProfile,
                                argumentValues = arrayOf(
                                    AppDestinations.Forms.UserProfile.editingIdArgument
                                            to profile.id
                                )
                            )
                        }
                    )
                }
            }
        )
    }

    @Composable
    private fun RelationshipScreenHeader(
        modifier: Modifier = Modifier,
        relationship: Relationship,
        onEditProfileClick: () -> Unit,
        onEditRelationshipClick: () -> Unit
    ) {
        ScreenHeaderContainer(modifier = modifier) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                ScreenHeaderTitleLayout(modifier = Modifier.fillMaxWidth()) {
                    RelationshipScreenHeaderTitle(relationship)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PanelButton(
                        modifier = Modifier.weight(1.0f),
                        action = dictionary.screens.home.editProfileAction.string(),
                        onClick = onEditProfileClick,
                        defaults = PanelButtonDefaults.defaults(
                            clip = PanelButtonDefaults.Clip.BottomStart,
                            textStyle = MaterialTheme.typography.bodyLarge
                        )
                    )
                    VerticalDivider(
                        modifier = Modifier.fillMaxHeight(fraction = 0.7f),
                        thickness = 2.dp
                    )
                    PanelButton(
                        modifier = Modifier.weight(1.0f),
                        action = dictionary.screens.home.editRelationshipAction.string(),
                        onClick = onEditRelationshipClick,
                        defaults = PanelButtonDefaults.defaults(
                            clip = PanelButtonDefaults.Clip.BottomEnd,
                            textStyle = MaterialTheme.typography.bodyLarge
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun RowScope.RelationshipScreenHeaderTitle(
        relationship: Relationship
    ) {
        RelationshipAvatars(
            relationship = relationship,
            imageModifier = Modifier.border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = CircleShape
            )
        )
        Text(
            modifier = Modifier.weight(1.0f),
            text = dictionary.screens.home.userAndPartner.string(
                "%user_username%" to relationship.userProfile.username,
                "%partner_username%" to relationship.partnerProfile.username
            ),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    private fun ProfileScreenHeader(
        modifier: Modifier = Modifier,
        profile: Profile,
        onEditProfileClick: () -> Unit
    ) {
        ScreenHeaderContainer(modifier = modifier) {
            ScreenHeaderTitleLayout(modifier = Modifier.fillMaxWidth()) {
                ByteArrayImage(
                    content = profile.avatarContent,
                    placeholder = {
                        AvatarPlaceholder(
                            gender = profile.gender,
                            size = 72.dp
                        )
                    },
                    size = 72.dp
                )
                Column(
                    modifier = Modifier.weight(1.0f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 10.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Text(
                        text = profile.username,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onEditProfileClick
                    ) {
                        Text(
                            text = dictionary.screens.home.editProfileAction.string(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ShimmerScreenHeader(
        modifier: Modifier = Modifier
    ) {
        ScreenHeaderContainer(
            modifier = modifier
                .height(statusBarPadding().calculateTopPadding() + 150.dp)
                .shimmer(LocalShimmer.current),
            content = {}
        )
    }

    @Composable
    private fun ScreenHeaderContainer(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            shadowElevation = 5.dp,
            tonalElevation = 3.dp,
            shape = RoundedCornerShape(20.dp)
        ) {
            content()
        }
    }

    @Composable
    private fun ScreenHeaderTitleLayout(
        modifier: Modifier = Modifier,
        content: @Composable RowScope.() -> Unit
    ) {
        Row(
            modifier = modifier.padding(20.dp.mergeWithStatusBar()),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }

//endregion

    //region Body content
    private fun LazyListScope.buildBody(
        userProfile: Profile?,
        asyncRelationship: me.kofesst.lovemood.async.AsyncValue<Relationship>,
        asyncMemories: me.kofesst.lovemood.async.AsyncValuesList<PhotoMemory>
    ) {
        asyncValueContent(
            asyncValue = asyncRelationship,
            onLoading = {
                buildShimmerBody()
            },
            onLoaded = { relationship ->
                if (relationship == null) {
                    item {
                        val appState = LocalAppState.current
                        NoRelationshipContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(40.dp),
                            onCreateRelationshipClick = {
                                appState.navigate(AppDestinations.Forms.Relationship)
                            },
                            userGender = userProfile?.gender ?: Gender.Male
                        )
                    }
                } else {
                    item(key = LOVE_DURATION_SECTION_KEY) {
                        LoveDurationSection(
                            modifier = Modifier.fillMaxWidth(),
                            relationship = relationship
                        )
                    }
                    item(key = EVENTS_SECTION_KEY) {
                        EventsSection(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    buildMemories(asyncMemories)
                }
            }
        )
    }

    private fun LazyListScope.buildShimmerBody() {
        item(key = LOVE_DURATION_SECTION_KEY) {
            ShimmerSection(
                modifier = Modifier.fillMaxWidth(),
            )
        }
        item(key = EVENTS_SECTION_KEY) {
            ShimmerSection(
                modifier = Modifier.fillMaxWidth(),
            )
        }
        item(key = MEMORIES_SECTION_KEY) {
            ShimmerSection(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    private fun LazyListScope.buildMemories(
        asyncMemories: me.kofesst.lovemood.async.AsyncValuesList<PhotoMemory>
    ) {
        requiredAsyncValueContent(
            asyncValue = asyncMemories,
            onLoading = {
                item(key = MEMORIES_SECTION_KEY) {
                    ShimmerSection(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            onLoaded = { memories ->
                item(key = MEMORIES_SECTION_KEY) {
                    val appState = LocalAppState.current
                    MemoriesSection(
                        modifier = Modifier.fillMaxWidth(),
                        memories = memories,
                        onAddMemoryClick = {
                            appState.navigate(AppDestinations.Forms.Memory)
                        },
                        onViewAllClick = {
                            appState.navigate(AppDestinations.Memories.List)
                        }
                    )
                }
            }
        )
    }

    private const val LOVE_DURATION_SECTION_KEY = "love_duration_section"
    private const val EVENTS_SECTION_KEY = "events_section"
    private const val MEMORIES_SECTION_KEY = "memories_section"

    @Composable
    private fun NoRelationshipContent(
        modifier: Modifier = Modifier,
        onCreateRelationshipClick: () -> Unit,
        userGender: Gender
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            LottieFile(
                resource = LottieResources.SadHeart.resource(),
                size = LottieSize.Maximized,
                dynamicProperties = LottieResources.SadHeart.dynamicProperties(userGender)
            )
            Text(
                text = dictionary.screens.home.noRelationshipTitle.string(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCreateRelationshipClick
            ) {
                Text(
                    text = dictionary.screens.home.addRelationshipAction.string(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

//endregion
}