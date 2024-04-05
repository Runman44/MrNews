package nl.mranderson.mrnews.data

import nl.mranderson.mrnews.domain.NewsRepository
import nl.mranderson.mrnews.domain.model.NewsItem
import nl.mranderson.mrnews.domain.model.TypeOfNews
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor() : NewsRepository {
    override suspend fun getNewsHeadlines(type: TypeOfNews): Result<List<NewsItem>> {
        return when (type) {
            TypeOfNews.GLOBAL -> Result.success(
                listOf(
                    NewsItem(
                        imageUrl = "https://cdn.nos.nl/image/2024/03/21/1064742/1536x864a.jpg",
                        title = "Kaag bij overleg Gaza-hulp - Israelisch leger eist evacuatie al-Shifa-ziekenhuis",
                    ),
                    NewsItem(
                        imageUrl = "https://cdn.nos.nl/image/2024/03/21/1064749/1536x864a.jpg",
                        title = "Woning loodgieter Vlaardingen gesloten, meer politiesurveillance",
                    )
                )
            )
            TypeOfNews.OLYMPIC -> Result.success(
                listOf(
                    NewsItem(
                        imageUrl = "https://cdn.nos.nl/image/2024/02/18/1054195/1024x576a.jpg",
                        title = "In 1988 koningin van Calgary, nu geniet Van Gennip van rol op achtergrond",
                    ),
                    NewsItem(
                        imageUrl = "https://cdn.nos.nl/image/2024/02/15/1053048/1024x576a.jpg",
                        title = "Schouten opgekrabbeld na ziekte in aanloop naar WK: 'Slapen doet heel veel'",
                    )
                )
            )
            TypeOfNews.SOCCER -> Result.success(
                listOf(
                    NewsItem(
                        imageUrl = "https://cdn.nos.nl/image/2024/03/27/1066526/1024x576a.jpg",
                        title = "Brenet speelt niet meer voor FC Twente na veroordeling tot celstraf",
                    ),
                    NewsItem(
                        imageUrl = "https://cdn.nos.nl/image/2024/03/27/1066431/1024x576a.jpg",
                        title = "Schouten slaagt voor derde examen en stelt kandidatuur voor positie naast Frenkie de Jong",
                    )
                )
            )
        }
    }
}
