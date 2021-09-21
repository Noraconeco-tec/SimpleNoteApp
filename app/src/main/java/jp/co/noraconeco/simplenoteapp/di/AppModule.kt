package jp.co.noraconeco.simplenoteapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import jp.co.noraconeco.simplenoteapp.model.SimpleNoteImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    internal abstract fun bindSimpleNote(
        simpleNoteImpl: SimpleNoteImpl
    ): SimpleNote
}

