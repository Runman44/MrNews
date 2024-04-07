package nl.mranderson.mrnews.domain.usecases

import nl.mranderson.mrnews.domain.NewsRepository
import nl.mranderson.mrnews.domain.model.TypeOfNews
import javax.inject.Inject

class GetNewsHeadlines @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(type: TypeOfNews) = repository.getNewsHeadlines(type)
}
