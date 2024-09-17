package me.kofesst.lovemood.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.models.Profile
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.ui.components.image.ByteImage

@Composable
fun RelationshipAvatars(
    modifier: Modifier = Modifier,
    relationship: Relationship,
    imageModifier: Modifier = Modifier,
    imageSize: Dp = 72.dp,
    imageShape: Shape = CircleShape
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = (-imageSize) / 2)
    ) {
        ProfileAvatarImage(
            modifier = imageModifier,
            profile = relationship.userProfile,
            imageSize = imageSize,
            imageShape = imageShape
        )
        ProfileAvatarImage(
            modifier = imageModifier,
            profile = relationship.partnerProfile,
            imageSize = imageSize,
            imageShape = imageShape
        )
    }
}

@Composable
fun ProfileAvatarImage(
    modifier: Modifier = Modifier,
    profile: Profile,
    imageSize: Dp = 72.dp,
    imageShape: Shape = CircleShape
) {
    ByteImage(
        modifier = modifier
            .size(imageSize)
            .clip(imageShape),
        byteContent = profile.avatarContent,
        placeholder = {
            AvatarPlaceholder(
                size = imageSize,
                shape = imageShape
            )
        }
    )
}

@Composable
fun AvatarPlaceholder(
    modifier: Modifier = Modifier,
    size: Dp = 96.dp,
    shape: Shape = CircleShape,
) {
    Image(
        modifier = modifier
            .size(size)
            .clip(shape),
        painter = painterResource(id = R.drawable.ic_male_avatar),
        contentDescription = null
    )
}