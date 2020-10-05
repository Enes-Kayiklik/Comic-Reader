package com.eneskayiklik.comicreader.di

import android.content.Context
import com.eneskayiklik.comicreader.utils.Constants.BOOK_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.builder.FileLoaderBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ComicModule {
    @Singleton
    @Provides
    fun provideFireStore(): CollectionReference =
        Firebase.firestore.collection(BOOK_COLLECTION)

    @Singleton
    @Provides
    fun provideFileLoader(
        @ApplicationContext context: Context
    ): FileLoaderBuilder =
        FileLoader.with(context)
}