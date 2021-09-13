package jp.co.noraconeco.simplenoteapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.noraconeco.simplenoteapp.repository.note.InMemoryNoteRepository
import jp.co.noraconeco.simplenoteapp.repository.note.NoteRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NoteModule {

    @Binds
    abstract fun bindNoteRepository(
        inMemoryNoteRepository: InMemoryNoteRepository
    ) : NoteRepository
}