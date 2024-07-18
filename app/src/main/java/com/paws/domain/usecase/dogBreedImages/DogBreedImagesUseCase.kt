package com.paws.domain.usecase.dogBreedImages

import com.paws.data.Resource
import com.paws.data.repository.DataSource
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DogBreedImagesUseCase @Inject constructor(
    private val dataRepository: DataSource
) : UseCase {
    override fun getDogBreedImages(breed: String): Single<List<String>> {
        return dataRepository.getDogBreedImages(breed)
    }
}