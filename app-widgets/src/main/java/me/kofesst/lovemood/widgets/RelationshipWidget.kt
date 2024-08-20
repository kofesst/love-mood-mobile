package me.kofesst.lovemood.widgets

import android.content.ComponentName
import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentSize
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import java.util.Base64

object RelationshipWidget : GlanceAppWidget() {
    val relationshipIdKey = intPreferencesKey(name = "relationship_id")
    val userUsernameKey = stringPreferencesKey(name = "user_username")
    val userAvatarEncodedKey = stringPreferencesKey(name = "user_avatar")
    val partnerUsernameKey = stringPreferencesKey(name = "partner_username")
    val partnerAvatarEncodedKey = stringPreferencesKey(name = "partner_avatar")
    val loveDurationKey = stringPreferencesKey(name = "love_duration")

    override val stateDefinition: GlanceStateDefinition<*> =
        PreferencesGlanceStateDefinition

    suspend fun pinNewWidget(context: Context) {
        GlanceAppWidgetManager(context).requestPinGlanceAppWidget(
            receiver = RelationshipWidgetReceiver::class.java
        )
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val relationshipId = currentState(relationshipIdKey) ?: -1
            val userUsername = currentState(userUsernameKey) ?: ""
            val userAvatarEncoded = currentState(userAvatarEncodedKey) ?: ""
            val partnerUsername = currentState(partnerUsernameKey) ?: ""
            val partnerAvatarEncoded = currentState(partnerAvatarEncodedKey) ?: ""
            val loveDuration = currentState(loveDurationKey) ?: ""
            val widgetData = RelationshipWidgetData(
                relationshipId = relationshipId,
                userUsername = userUsername,
                userAvatar = decodeAvatar(userAvatarEncoded),
                partnerUsername = partnerUsername,
                partnerAvatar = decodeAvatar(partnerAvatarEncoded),
                loveDuration = loveDuration
            )
            GlanceTheme {
                WidgetContent(widgetData)
            }
        }
    }

    override val sizeMode: SizeMode = SizeMode.Exact

    @Composable
    private fun WidgetContent(
        widgetData: RelationshipWidgetData
    ) {
        val size = LocalSize.current
        WidgetContainer {
            if (size.width < 300.dp || size.height < 100.dp) SmallSizeContent(widgetData)
            else if (size.width < 300.dp && size.height > 200.dp) VerticalContent(widgetData)
            else if (size.width > 350.dp && size.height > 275.dp) LargeSizeContent(widgetData)
            else DefaultContent(widgetData)
        }
    }

    @Composable
    private fun SmallSizeContent(
        widgetData: RelationshipWidgetData
    ) {
        RelationshipAvatars(
            userAvatar = widgetData.userAvatar,
            partnerAvatar = widgetData.partnerAvatar
        )
    }

    @Composable
    private fun VerticalContent(
        widgetData: RelationshipWidgetData
    ) {
        Column(horizontalAlignment = Alignment.Horizontal.CenterHorizontally) {
            RelationshipAvatars(
                userAvatar = widgetData.userAvatar,
                partnerAvatar = widgetData.partnerAvatar
            )
            Spacer(GlanceModifier.height(20.dp))
            RelationshipInfo(
                modifier = GlanceModifier.defaultWeight(),
                widgetData = widgetData,
                isLarge = false
            )
        }
    }

    @Composable
    private fun LargeSizeContent(
        widgetData: RelationshipWidgetData
    ) {
        Column(horizontalAlignment = Alignment.Horizontal.CenterHorizontally) {
            RelationshipAvatars(
                userAvatar = widgetData.userAvatar,
                partnerAvatar = widgetData.partnerAvatar,
                imageSize = 128.dp
            )
            Spacer(GlanceModifier.height(20.dp))
            RelationshipInfo(
                modifier = GlanceModifier.defaultWeight(),
                widgetData = widgetData,
                isLarge = true
            )
        }
    }

    @Composable
    private fun DefaultContent(
        widgetData: RelationshipWidgetData
    ) {
        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            RelationshipAvatars(
                modifier = GlanceModifier.wrapContentSize(),
                userAvatar = widgetData.userAvatar,
                partnerAvatar = widgetData.partnerAvatar
            )
            Spacer(GlanceModifier.width(20.dp))
            RelationshipInfo(
                modifier = GlanceModifier.defaultWeight(),
                widgetData = widgetData,
                isLarge = false
            )
        }
    }

    @Composable
    private fun RelationshipAvatars(
        modifier: GlanceModifier = GlanceModifier,
        userAvatar: ByteArray,
        partnerAvatar: ByteArray,
        imageSize: Dp = 48.dp
    ) {
        Box(modifier = modifier) {
            AvatarImage(
                modifier = GlanceModifier
                    .size(imageSize)
                    .cornerRadius(imageSize),
                content = userAvatar
            )
            Box(
                modifier = GlanceModifier.padding(
                    top = imageSize / 2,
                    start = imageSize / 2
                )
            ) {
                AvatarImage(
                    modifier = GlanceModifier
                        .size(imageSize)
                        .cornerRadius(imageSize),
                    content = partnerAvatar
                )
            }
        }
    }

    @Composable
    private fun AvatarImage(
        modifier: GlanceModifier = GlanceModifier,
        content: ByteArray
    ) {
        if (content.isNotEmpty()) {
            val bitmap = BitmapFactory.decodeByteArray(content, 0, content.size)
            Image(
                modifier = modifier,
                provider = ImageProvider(bitmap),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }

    @Composable
    private fun RelationshipInfo(
        modifier: GlanceModifier = GlanceModifier,
        widgetData: RelationshipWidgetData,
        isLarge: Boolean
    ) {
        Column(modifier) {
            Text(
                text = "${widgetData.userUsername} + ${widgetData.partnerUsername}",
                style = TextStyle(
                    color = GlanceTheme.colors.onBackground,
                    fontSize = if (isLarge) 24.sp else 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(GlanceModifier.height(10.dp))
            Text(
                text = widgetData.loveDuration,
                style = TextStyle(
                    color = GlanceTheme.colors.onBackground,
                    fontSize = if (isLarge) 18.sp else 16.sp
                )
            )
        }
    }

    @Composable
    private fun WidgetContainer(
        content: @Composable () -> Unit
    ) {
        GlanceTheme {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .appWidgetBackground()
                    .background(GlanceTheme.colors.background)
                    .clickable(
                        onClick = actionStartActivity(
                            componentName = ComponentName(
                                "me.kofesst.lovemood",
                                "me.kofesst.lovemood.presentation.main.MainActivity"
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }

    private fun decodeAvatar(string: String): ByteArray {
        if (string.isBlank()) return byteArrayOf()
        return Base64.getDecoder().decode(string)
    }

    fun encodeAvatar(avatar: ByteArray): String {
        if (avatar.isEmpty()) return ""
        return Base64.getEncoder().encodeToString(avatar)
    }
}