package hu.klm60o.spiritrally.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.klm60o.spiritrally.data.News
import hu.klm60o.spiritrally.data.NewsViewModel
import hu.klm60o.spiritrally.data.UserViewModel

@Composable
fun NewsScreenComposable(navController: NavController, viewModel: UserViewModel, newsViewModel : NewsViewModel) {
    val navController = navController
    //al viewModel = viewModel
    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) }
    ) {
        innerPadding ->
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            //val newsList = newsViewModel.itemList as List<News>
            NewsList(newsViewModel.itemList.value)
        }

    }
    /*Surface {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp)) {
            Text(text = "Ez itt a hírek képernyő", fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 20.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center
            )
        }
        MyBottomAppbarComposable()
    }*/
}

@Composable
fun NewsList(itemList: List<News>) {
    LazyColumn {
        items(itemList, key = { item -> item.id }) { item ->
            ListItem(
                headlineContent = {
                    Text(
                        text = item.title,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 5.dp)
                    )
                },
                supportingContent = {
                    Text(text = item.content,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 5.dp)
                    )
                },
                leadingContent = {
                    if (item.important) {
                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "NewsIcon",
                            tint = Color.Red
                        )
                    }
                    else {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "NewsIcon"
                        )
                    }

                },
                overlineContent = {
                    if (item.important) {
                        Text(text = "FIYGELMEZTETÉS",
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                    }
                    else {
                        Text(text = "INFORMÁCIÓ",
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                    }
                }
            )
            Divider()
            //Text(text = item.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsPreview() {
    hu.klm60o.spiritrally.ui.theme.ui.theme.SpiritRallyTheme {
        NewsScreenComposable(
            navController = rememberNavController(),
            viewModel =  UserViewModel(),
            newsViewModel = NewsViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsPreviewDark() {
    hu.klm60o.spiritrally.ui.theme.SpiritRallyTheme(darkTheme = true) {
        NewsScreenComposable(
            navController = rememberNavController(),
            viewModel =  UserViewModel(),
            newsViewModel = NewsViewModel()
        )
    }
}

