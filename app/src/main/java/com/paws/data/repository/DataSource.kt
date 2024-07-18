package com.paws.data.repository

import com.paws.domain.model.DogBreed
import io.reactivex.Observable
import io.reactivex.Single

interface DataSource {
    fun getDogBreeds(): Observable<List<DogBreed>>
    fun getDogBreedImages(breedName: String): Single<List<String>>
    suspend fun updateDogBreeds(name: String, isFavourite: Boolean)
    fun getFavouriteDogBreeds(): Observable<List<DogBreed>>
}