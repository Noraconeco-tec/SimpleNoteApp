package jp.co.noraconeco.simplenoteapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import jp.co.noraconeco.simplenoteapp.model.SimpleNoteImpl
import jp.co.noraconeco.simplenoteapp.repository.note.InMemoryNoteRepository
import jp.co.noraconeco.simplenoteapp.repository.note.NoteRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NoteModule {

    @Debug
    @Binds
    abstract fun bindDebugNoteRepository(
        inMemoryNoteRepository: InMemoryNoteRepository
    ): NoteRepository

    @Release
    @Binds
    abstract fun bindReleaseNoteRepository(
        inMemoryNoteRepository: InMemoryNoteRepository
    ): NoteRepository

    @Binds
    abstract fun bindSimpleApp(
        simpleNoteImpl: SimpleNoteImpl
    ): SimpleNote
}