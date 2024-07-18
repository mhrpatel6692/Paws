package com.paws.data.remote

import com.paws.data.Resource
import com.paws.domain.model.DogBreed
import io.reactivex.Single

interface RemoteSource {
    fun getDogBreeds(): Single<List<DogBreed>>
    fun getDogBreedImages(breedName: String): Single<List<String>>
}

