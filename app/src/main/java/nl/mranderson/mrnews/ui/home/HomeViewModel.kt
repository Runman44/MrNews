package nl.mranderson.mrnews.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.mranderson.mrnews.domain.model.NewsItem
import nl.mranderson.mrnews.domain.model.TypeOfNews
import nl.mranderson.mrnews.domain.usecases.GetNewsHeadlines
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getNewsHeadlines: GetNewsHeadlines,
) : ViewModel() {

    private val _state: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val state: StateFlow<ScreenState> get() = _state.asStateFlow()

    init {
        fetchNewsArticles()
    }

    private fun fetchNewsArticles() {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            val result = getNewsHeadlines(TypeOfNews.GLOBAL)

            result.onSuccess { it ->
                _state.value = ScreenState.Data(it.map { it.toUiState() })
            }
            result.onFailure {
                _state.value = ScreenState.Error
            }
        }
    }

    sealed class ScreenState {
        data object Loading : ScreenState()
        data class Data(val newsItems: List<NewsItemUiState>) : ScreenState()
        data object Error : ScreenState()
    }
}

data class NewsItemUiState(val image: String, val title: String)

fun NewsItem.toUiState() : NewsItemUiState {
    return NewsItemUiState(title = this.title, image = this.imageUrl)
}
