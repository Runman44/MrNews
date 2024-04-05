package nl.mranderson.mrnews.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.mranderson.mrnews.data.NewsRepositoryImpl
import nl.mranderson.mrnews.domain.NewsRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    abstract fun bindNewsRepository(
        repoImpl: NewsRepositoryImpl
    ): NewsRepository

}
