package ru.dayone.main.account.presentation.settings

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.selects.whileSelect
import ru.dayone.main.account.R
import ru.dayone.tasksplitter.common.theme.onBackgroundDark
import ru.dayone.tasksplitter.common.theme.onBackgroundLight
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.AUTO_THEME_CODE
import ru.dayone.tasksplitter.common.utils.DARK_THEME_CODE
import ru.dayone.tasksplitter.common.utils.DYNAMIC_THEME_CODE
import ru.dayone.tasksplitter.common.utils.LIGHT_THEME_CODE
import ru.dayone.tasksplitter.common.utils.THEME_KEY
import ru.dayone.tasksplitter.common.utils.or

@Composable
fun SettingsScreen(
    navController: NavController,
    sharedPrefs: SharedPreferences
) {
    val context = LocalContext.current

    val themeCodes = arrayOf(AUTO_THEME_CODE, LIGHT_THEME_CODE, DARK_THEME_CODE, DYNAMIC_THEME_CODE)
    val themes =
        arrayOf(R.string.text_auto, R.string.text_light, R.string.text_dark, R.string.text_dynamic)
    val nowThemeInd = when(sharedPrefs.getString(THEME_KEY, AUTO_THEME_CODE)){
        AUTO_THEME_CODE -> 0
        LIGHT_THEME_CODE -> 1
        DARK_THEME_CODE -> 2
        DYNAMIC_THEME_CODE -> 3
        else -> 0
    }
    var themesSelectedIndex by remember { mutableIntStateOf(nowThemeInd) }

    Column {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.padding(10.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors().copy(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    navController.popBackStack()
                }
            ){
               Image(
                   imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                   contentDescription = "Arrow back",
                   colorFilter = ColorFilter.tint(color = onBackgroundDark.or(onBackgroundLight))
               )
            }
            Text(
                text = context.getString(R.string.title_settings),
                style = titleTextStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = context.getString(R.string.text_theme),
            modifier = Modifier.padding(start = 20.dp)
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.padding(10.dp)
        ) {
            themes.forEachIndexed { index, textId ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = themes.size
                    ),
                    onClick = {
                        sharedPrefs.edit().putString(THEME_KEY, themeCodes[index]).apply()
                        themesSelectedIndex = index
                    },
                    selected = index == themesSelectedIndex,
                    label = { Text(context.getString(textId), overflow = TextOverflow.Ellipsis, maxLines = 1) }
                )
            }
        }

    }
}