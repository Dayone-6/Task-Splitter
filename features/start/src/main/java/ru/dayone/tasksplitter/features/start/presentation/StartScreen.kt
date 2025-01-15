package ru.dayone.tasksplitter.features.start.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import ru.dayone.start.R
import ru.dayone.tasksplitter.common.navigation.LoginNavRoute
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.features.start.data.utils.aboutAppImages
import ru.dayone.tasksplitter.features.start.data.utils.aboutAppText

@Composable
fun StartScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    val pagerState = rememberPagerState(pageCount = {
        5
    })
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.ic_launcher_background),
                    "Icon",
                    modifier = Modifier.height(40.dp)
                )
                Text(
                    text = context.getString(R.string.app_name),
                    style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 25.sp),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
            Text(
                text = context.getString(R.string.text_about_app),
                style = Typography.bodyLarge.copy(fontSize = 17.sp),
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center
            )

            HorizontalPager(
                pagerState,
                modifier = Modifier
                    .weight(1f)
                    .height(0.dp)
            ) { page ->
                AboutAppPage(
                    aboutAppImages[page],
                    context.getString(aboutAppText[page])
                )
            }

            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (
                        (pagerState.currentPage == iteration && !isSystemInDarkTheme()) ||
                        (pagerState.currentPage != iteration && isSystemInDarkTheme())
                    ) {
                        Color.DarkGray
                    } else {
                        Color.LightGray
                    }
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(7.dp)
                    )
                }
            }

            Button(
                onClick = {
                    navController.navigate(route = LoginNavRoute, navOptions = navOptions {
                        popUpTo<LoginNavRoute>()
                    })
                },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(.8f)
            ) {
                Text(
                    text = context.getString(R.string.text_to_login),
                    style = buttonTextStyle.copy(fontSize = 18.sp)
                )
            }
        }
    }

}

@Composable
fun AboutAppPage(
    @DrawableRes imageId: Int,
    title: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(imageId),
            "About App Image",
            modifier = Modifier
                .padding(10.dp)
                .size(300.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = title,
            style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        )
    }
}

@Composable
@Preview
fun StartScreenPreview() {
    StartScreen(NavHostController(LocalContext.current))
}