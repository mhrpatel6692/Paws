package com.paws.data.local.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paws.domain.model.DogBreed
import com.paws.domain.model.DogBreedImages
import com.paws.utils.Converters

@Database(version = 1, entities = [DogBreed::class, DogBreedImages::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class DogBreedsDatabase : RoomDatabase() {

    abstract fun dogBreedsDao(): DogBreedsDao
}