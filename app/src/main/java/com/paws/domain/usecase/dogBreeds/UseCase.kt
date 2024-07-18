package com.paws.domain.usecase.dogBreeds

import com.paws.domain.model.DogBreed
import io.reactivex.Observable

interface UseCase {
    fun getDogBreeds(): Observable<List<DogBreed>>
}