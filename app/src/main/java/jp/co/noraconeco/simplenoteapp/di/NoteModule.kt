package jp.co.noraconeco.simplenoteapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.noraconeco.simplenoteapp.model.SimpleNote
import jp.co.noraconeco.simplenoteapp.model.SimpleNoteImpl
import jp.co.noraconeco.simplenoteapp.repository.note.InMemoryNoteRepository
import jp.co.noraconeco.simplenoteapp.repository.note.NoteRepository
import jp.co.noraconeco.simplenoteapp.repository.note.DebugInMemoryNoteRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NoteModule {

    @Debug
    @Binds
    abstract fun bindDebugNoteRepository(
        debugInMemoryNoteRepository: DebugInMemoryNoteRepository
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