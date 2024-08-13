package me.kofesst.lovemood.presentation.screens.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.R
import me.kofesst.lovemood.presentation.app.LocalDictionary

@Composable
fun AppScreenHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AppLogoImage()
        AppNameText()
        AppVersionText()
    }
}

@Composable
private fun AppLogoImage(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(128.dp),
        painter = painterResource(R.drawable.app_logo),
        contentDescription = null
    )
}

@Composable
private fun AppNameText(
    modifier: Modifier = Modifier,
) {
    val dictionary = LocalDictionary.current
    Text(
        modifier = modifier,
        text = dictionary.appName.string(),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun AppVersionText(
    modifier: Modifier = Modifier
) {
    val dictionary = LocalDictionary.current
    val context = LocalContext.current
    val packageInfo = remember(context) {
        context.packageManager.getPackageInfo(context.packageName, 0)
    }
    Text(
        modifier = modifier,
        text = dictionary.appVersion.string(
            "%app_version%" to packageInfo.versionName
        ),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Normal
    )
}