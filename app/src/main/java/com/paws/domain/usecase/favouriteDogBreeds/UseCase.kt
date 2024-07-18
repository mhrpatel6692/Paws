package com.paws.domain.usecase.favouriteDogBreeds

import com.paws.domain.model.DogBreed
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun getFavouriteDogBreeds(): Observable<List<DogBreed>>
    suspend fun addToFavourites(name: String, isFavourite: Boolean)
}