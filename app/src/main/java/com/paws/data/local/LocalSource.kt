package com.paws.data.local

import com.paws.data.Resource
import com.paws.domain.model.DogBreed
import com.paws.domain.model.DogBreedImages
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    fun getDogBreeds(): Observable<List<DogBreed>>
    fun getDogBreedImages(breedName: String): Single<List<String>>
    fun storeDogBreedListInDb(dogBreeds: List<DogBreed>): Completable
    fun storeDogBreedImageListInDb(breedImages: DogBreedImages): Completable
    suspend fun updateDogBreeds(name: String, isFavourite: Boolean)
    fun getFavouriteDogBreeds(): Observable<List<DogBreed>>
}
