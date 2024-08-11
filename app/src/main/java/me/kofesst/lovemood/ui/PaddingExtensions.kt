package me.kofesst.lovemood.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PaddingValues.alsoStatusBar(): PaddingValues {
    return this mergeWith statusBarPadding()
}

@Composable
fun PaddingValues.alsoNavBar(): PaddingValues {
    return this mergeWith navigationBarPadding()
}

@Composable
fun Dp.mergeWithStatusBar(): PaddingValues {
    return statusBarPadding() mergeWith this
}

@Composable
fun statusBarPadding(): PaddingValues {
    return WindowInsets.statusBars.asPaddingValues()
}

@Composable
fun navigationBarPadding(): PaddingValues {
    return WindowInsets.navigationBars.asPaddingValues()
}

@Composable
infix fun PaddingValues.mergeWith(all: Dp): PaddingValues {
    return with(LocalLayoutDirection.current) {
        PaddingValues(
            top = calculateTopPadding() + all,
            bottom = calculateBottomPadding() + all,
            start = calculateStartPadding(this) + all,
            end = calculateEndPadding(this) + all
        )
    }
}

@Composable
infix fun PaddingValues.mergeWith(other: PaddingValues): PaddingValues {
    return with(LocalLayoutDirection.current) {
        mergeWith(
            top = other.calculateTopPadding(),
            bottom = other.calculateBottomPadding(),
            start = other.calculateStartPadding(this),
            end = other.calculateEndPadding(this)
        )
    }
}

@Composable
fun PaddingValues.mergeWith(
    top: Dp = 0.dp,
    bottom: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp
): PaddingValues {
    return with(LocalLayoutDirection.current) {
        PaddingValues(
            top = calculateTopPadding() + top,
            bottom = calculateBottomPadding() + bottom,
            start = calculateStartPadding(this) + start,
            end = calculateEndPadding(this) + end
        )
    }
}