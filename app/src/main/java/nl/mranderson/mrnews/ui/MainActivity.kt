@file:OptIn(ExperimentalMaterial3Api::class)

package nl.mranderson.mrnews.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SportsVolleyball
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.SportsVolleyball
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.mranderson.mrnews.ui.home.HomeScreen
import nl.mranderson.mrnews.ui.olympic.OlympicScreen
import nl.mranderson.mrnews.ui.soccer.SoccerScreen
import nl.mranderson.mrnews.ui.theme.MrNewsTheme
import nl.mranderson.mrnews.ui.theme.Theme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = listOf(
            BottomNavigationItem.Home,
            BottomNavigationItem.Olympic,
            BottomNavigationItem.Soccer
        )

        setContent {
            val navController = rememberNavController()

            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            MrNewsTheme(appTheme = viewModel.appTheme.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "News") },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                                ),
                            )
                        },
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            viewModel.onChangeTheme(item.theme)
                                            navController.navigate(item.screenRoute)
                                        },
                                        label = { Text(text = item.title) },
                                        icon = {
                                            BadgedBox(badge = {
                                                if (item.badgeCount != null) {
                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if (item.hasNews) {
                                                    Badge()
                                                }
                                            }) {
                                                Icon(
                                                    imageVector = if (selectedItemIndex == index) {
                                                        item.selectedIcon
                                                    } else {
                                                        item.unselectedIcon
                                                    },
                                                    contentDescription = "",
                                                )
                                            }
                                        },
                                    )
                                }
                            }
                        },
                        content = {
                            Box(Modifier.padding(it)) {
                                NavigationGraph(navController = navController)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavigationItem.Home.screenRoute) {
        composable(BottomNavigationItem.Home.screenRoute) {
            HomeScreen()
        }
        composable(BottomNavigationItem.Olympic.screenRoute) {
            OlympicScreen()
        }
        composable(BottomNavigationItem.Soccer.screenRoute) {
            SoccerScreen()
        }
    }
}

sealed class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val screenRoute: String,
    val theme: Theme,
) {
    data object Home : BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
        screenRoute = "home",
        theme = Theme.News,
    )

    data object Olympic : BottomNavigationItem(
        title = "Olympic",
        selectedIcon = Icons.Filled.LocalFireDepartment,
        unselectedIcon = Icons.Outlined.LocalFireDepartment,
        hasNews = true,
        screenRoute = "olympic",
        theme = Theme.Olympic,
    )

    data object Soccer : BottomNavigationItem(
        title = "Soccer",
        selectedIcon = Icons.Filled.SportsVolleyball,
        unselectedIcon = Icons.Outlined.SportsVolleyball,
        hasNews = false,
        badgeCount = 45,
        screenRoute = "soccer",
        theme = Theme.Soccer,
    )
}
