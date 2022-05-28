package zw.co.calculator.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zw.co.calculator.repos.OperationRepository
import zw.co.calculator.repos.OperationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

@Provides
fun provideOperationRepository():OperationRepository{
    return OperationRepositoryImpl()
}


}