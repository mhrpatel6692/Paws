package com.paws.utils

import com.paws.domain.model.DogBreed

object Breeds {

    fun breed(name: String, subBreeds: String, imageUrl: String, isFavourite: Boolean): DogBreed {
        return DogBreed(
            name = name,
            subBreeds = subBreeds,
            imageUrl = imageUrl,
            isFavourite = isFavourite
        )
    }

    fun dogBreeds(): List<DogBreed> {
        return listOf(
            DogBreed("Labrador", "", "", false),
            DogBreed("Beagle", "", "", false))
    }

    fun breedImages(): List<String> {
        return listOf("url one", "url two", "url three", "url four")
    }

//    fun dogBreedsResponse(): DogBreedResponse {
//        return DogBreedResponse(dogBreeds())
//    }
}
