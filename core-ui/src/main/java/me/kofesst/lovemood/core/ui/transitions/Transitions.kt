package me.kofesst.lovemood.core.ui.transitions

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

val fadeTransition
    get() = fadeIn().togetherWith(fadeOut())

val scaleTransition
    get() = (fadeIn() + scaleIn()).togetherWith(fadeOut() + scaleOut())

private val topBarEnterTransition
    get() = slideInVertically { -it }

private val topBarExitTransition
    get() = slideOutVertically { -it }

val topBarAnimatedTransition
    get() = topBarEnterTransition.togetherWith(topBarExitTransition)