package com.paws.domain.usecase.dogBreedImages

import io.reactivex.Single

interface UseCase {
    fun getDogBreedImages(breed : String): Single<List<String>>
}