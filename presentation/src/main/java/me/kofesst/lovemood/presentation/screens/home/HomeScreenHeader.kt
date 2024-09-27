package me.kofesst.lovemood.presentation.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import me.kofesst.lovemood.async.RequiredSnapshotContent
import me.kofesst.lovemood.async.SnapshotContent
import me.kofesst.lovemood.async.SnapshotValue
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.ui.components.image.ByteImage
import me.kofesst.lovemood.presentation.LocalShimmer

private val DefaultAvatarSize
    @Composable
    @ReadOnlyComposable
    get() = 200.dp

private val DefaultRelationshipAvatarsGap
    @Composable
    @ReadOnlyComposable
    get() = DefaultAvatarSize / 2

private val DefaultAvatarShape
    @Composable
    @ReadOnlyComposable
    get() = MaterialTheme.shapes.medium

@Composable
fun HomeScreenHeader(
    modifier: Modifier = Modifier,
    profileSnapshot: SnapshotValue<Profile>,
    relationshipSnapshot: SnapshotValue<Relationship>
) {
    AsyncProfileHeader(
        modifier = modifier,
        snapshot = profileSnapshot
    ) { loadedProfile ->
        AsyncRelationshipHeader(
            modifier = modifier,
            snapshot = relationshipSnapshot
        ) { nullableRelationship ->
            if (nullableRelationship == null) {
                ProfileHeader(
                    modifier = modifier,
                    profile = loadedProfile
                )
            } else {
                RelationshipHeader(
                    modifier = modifier,
                    relationship = nullableRelationship
                )
            }
        }
    }
}

@Composable
private fun AsyncProfileHeader(
    modifier: Modifier = Modifier,
    snapshot: SnapshotValue<Profile>,
    content: @Composable (Profile) -> Unit
) {
    RequiredSnapshotContent(
        modifier = modifier,
        snapshot = snapshot,
        loadingContent = {
            ShimmerScreenHeaderContent(modifier)
        },
        failedContent = {
            Text(text = it.toString())
        },
        loadedContent = { profile ->
            content(profile)
        }
    )
}

@Composable
private fun AsyncRelationshipHeader(
    modifier: Modifier = Modifier,
    snapshot: SnapshotValue<Relationship>,
    content: @Composable (Relationship?) -> Unit
) {
    SnapshotContent(
        modifier = modifier,
        snapshot = snapshot,
        loadingContent = {
            ShimmerScreenHeaderContent(modifier)
        },
        failedContent = {
            Text(text = it.toString())
        },
        loadedContent = { relationship ->
            content(relationship)
        }
    )
}

@Composable
private fun ProfileHeader(
    modifier: Modifier = Modifier,
    profile: Profile
) {
    HeaderLayout(modifier) {
        ProfileAvatar(
            modifier = Modifier.size(DefaultAvatarSize),
            avatarContent = profile.avatarContent
        )
        ProfileGreetingText(
            modifier = Modifier.fillMaxWidth(),
            username = profile.username
        )
    }
}

@Composable
private fun RelationshipHeader(
    modifier: Modifier = Modifier,
    relationship: Relationship
) {
    HeaderLayout(modifier) {
        RelationshipAvatars(
            userPictureContent = relationship.userProfile.avatarContent,
            partnerPictureContent = relationship.partnerProfile.avatarContent
        )
        ProfileGreetingText(
            modifier = Modifier.fillMaxWidth(),
            username = relationship.userProfile.username
        )
        RelationshipSubtitleText(
            modifier = Modifier.fillMaxWidth(),
            partnerUsername = relationship.partnerProfile.username
        )
    }
}

@Composable
private fun RelationshipSubtitleText(
    modifier: Modifier = Modifier,
    partnerUsername: String
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append("Вы и ")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
            ) {
                append(partnerUsername)
            }
            append(" отлично смотритесь вместе!")
        },
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun RelationshipAvatars(
    modifier: Modifier = Modifier,
    userPictureContent: ByteArray,
    partnerPictureContent: ByteArray
) {
    Box(
        modifier = modifier.size((DefaultAvatarSize - DefaultRelationshipAvatarsGap) * 2)
    ) {
        ProfileAvatar(
            modifier = Modifier.align(Alignment.TopStart),
            avatarContent = userPictureContent
        )
        ProfileAvatar(
            modifier = Modifier.align(Alignment.BottomEnd),
            avatarContent = partnerPictureContent
        )
    }
}

@Composable
private fun ProfileGreetingText(
    modifier: Modifier = Modifier,
    username: String
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            append("Привет, ")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append(username)
            }
            append("!")
        },
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun ProfileAvatar(
    modifier: Modifier = Modifier,
    avatarContent: ByteArray
) {
    AnimatedContent(
        modifier = modifier.size(DefaultAvatarSize),
        targetState = avatarContent
    ) { content ->
        if (content.isNotEmpty()) {
            ByteImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(DefaultAvatarShape),
                byteContent = content
            )
        }
    }
}

@Composable
private fun ShimmerScreenHeaderContent(
    modifier: Modifier = Modifier
) {
    HeaderLayout(modifier) {
        ShimmerProfilePicture()
        ShimmerProfileName()
    }
}

@Composable
private fun ShimmerProfilePicture() {
    Surface(
        modifier = Modifier
            .size(200.dp)
            .shimmer(MaterialTheme.colorScheme.primary),
        color = MaterialTheme.colorScheme.onSurface,
        shape = MaterialTheme.shapes.medium,
        content = {}
    )
}

@Composable
private fun ShimmerProfileName() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shimmer(MaterialTheme.colorScheme.onSurfaceVariant),
        color = MaterialTheme.colorScheme.onSurface,
        shape = MaterialTheme.shapes.medium,
        content = {}
    )
}

@Composable
private fun HeaderLayout(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 20.dp)
    ) {
        content()
    }
}