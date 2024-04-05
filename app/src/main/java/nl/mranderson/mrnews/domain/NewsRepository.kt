package nl.mranderson.mrnews.domain

import nl.mranderson.mrnews.domain.model.NewsItem
import nl.mranderson.mrnews.domain.model.TypeOfNews

interface NewsRepository {
    suspend fun getNewsHeadlines(type: TypeOfNews): Result<List<NewsItem>>
}
