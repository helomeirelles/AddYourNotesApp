package com.addyournotes.di

import android.content.Context
import androidx.room.Room
import com.addyournotes.data.NoteDatabase
import com.addyournotes.data.NoteDatabaseDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNotesDao(noteDatabase: NoteDatabase): NoteDatabaseDAO = noteDatabase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context = context,
            NoteDatabase::class.java,
            "notes_db"
        ).fallbackToDestructiveMigration().build()

}