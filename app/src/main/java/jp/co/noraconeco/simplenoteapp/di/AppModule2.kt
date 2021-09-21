package jp.co.noraconeco.simplenoteapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.noraconeco.simplenoteapp.database.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
object AppModule2 {

    @Provides
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-name"
        ).build()
    }
}