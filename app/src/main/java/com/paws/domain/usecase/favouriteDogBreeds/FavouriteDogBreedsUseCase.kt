package com.paws.domain.usecase.favouriteDogBreeds

import com.paws.data.repository.DataSource
import com.paws.domain.model.DogBreed
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteDogBreedsUseCase @Inject constructor(
    private val dataRepository: DataSource
) : UseCase {
    override fun getFavouriteDogBreeds(): Observable<List<DogBreed>> {
        return dataRepository.getFavouriteDogBreeds()
    }

    override suspend fun addToFavourites(name: String, isFavourite: Boolean) {
        dataRepository.updateDogBreeds(name = name, isFavourite = isFavourite)
    }
}