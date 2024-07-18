package com.paws.domain.usecase.dogBreeds

import com.paws.data.repository.DataSource
import com.paws.domain.model.DogBreed
import io.reactivex.Observable
import javax.inject.Inject

class DogBreedsUseCase @Inject constructor(
    private val dataRepository: DataSource
) : UseCase {
     override fun getDogBreeds(): Observable<List<DogBreed>> {
        return dataRepository.getDogBreeds()
    }
}