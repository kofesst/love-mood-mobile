package me.kofesst.lovemood.core.ui.transitions

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith

val fadeTransition
    get() = fadeIn().togetherWith(fadeOut())

val scaleTransition
    get() = (fadeIn() + scaleIn()).togetherWith(fadeOut() + scaleOut())