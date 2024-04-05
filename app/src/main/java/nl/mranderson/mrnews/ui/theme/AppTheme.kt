package nl.mranderson.mrnews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import nl.mranderson.mrnews.ui.theme.news.NewsTheme
import nl.mranderson.mrnews.ui.theme.olympic.OlympicTheme
import nl.mranderson.mrnews.ui.theme.soccer.SoccerTheme

@Composable
fun MrNewsTheme(
    appTheme: Theme,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    when (appTheme) {
        Theme.News -> NewsTheme(isDarkMode, content)
        Theme.Olympic -> OlympicTheme(isDarkMode, content)
        Theme.Soccer -> SoccerTheme(isDarkMode, content)
    }
}

enum class Theme {
    News, Olympic, Soccer
}
