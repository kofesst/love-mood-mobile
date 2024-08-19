package me.kofesst.lovemood.core.ui.transitions

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith

val fadeTransition
    get() = fadeIn().togetherWith(fadeOut())

val softHorizontalSlide
    get() = (fadeIn() + slideInHorizontally { -it })
        .togetherWith(fadeOut() + slideOutHorizontally { -it })