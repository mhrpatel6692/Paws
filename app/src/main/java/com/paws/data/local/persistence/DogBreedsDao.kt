package com.paws.data.local.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paws.domain.model.DogBreed
import com.paws.domain.model.DogBreedImages
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface DogBreedsDao {

    @Query("SELECT * FROM dogbreed")
    fun getDogBreeds(): Observable<List<DogBreed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogBreed(dog: DogBreed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDogBreeds(dogBreeds: List<DogBreed>): Completable

    @Query("SELECT image_urls FROM dogbreedimages WHERE breed_name =:breedName")
    fun getDogBreedImages(breedName: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDogBreedImages(dogBreeds: DogBreedImages): Completable

    @Query("UPDATE dogbreed SET is_favourite = :isFavourite WHERE name = :name")
    suspend fun updateDogBreeds(name: String, isFavourite: Boolean)

    @Query("SELECT * FROM dogbreed WHERE is_favourite = 1")
    fun getFavouriteDogBreeds(): Observable<List<DogBreed>>

    @Query("SELECT * FROM dogbreed WHERE name LIKE :name")
    fun getOneDogBreed(name: String): DogBreed
}