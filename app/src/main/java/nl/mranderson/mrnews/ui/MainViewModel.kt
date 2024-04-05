package nl.mranderson.mrnews.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import nl.mranderson.mrnews.ui.theme.Theme

class MainViewModel : ViewModel() {

    private val _appTheme = mutableStateOf(Theme.News)
    val appTheme = _appTheme

    fun onChangeTheme(theme: Theme) {
        _appTheme.value = theme
    }
}
