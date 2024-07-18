package com.paws.data.remote

import android.util.Log
import com.paws.data.Resource
import com.paws.data.remote.dto.DogBreedResponse
import com.paws.data.remote.helper.DogBreedWithSubBreeds
import com.paws.data.remote.service.DogBreedsApi
import com.paws.domain.model.DogBreed
import com.paws.utils.Constants.COMMA
import com.paws.utils.Constants.DEFAULT_IS_FAVOURITE
import com.paws.utils.extensions.asyncAll
import com.paws.utils.extensions.capitalizeFirstLetter
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject
constructor(private val dogApiService: DogBreedsApi) : RemoteSource {

    override fun getDogBreeds(): Single<List<DogBreed>> {
        return dogApiService.fetchDogBreeds().map { breedResponse ->
            breedResponse.message.entries.map { entry ->
                val breedName = entry.key
                val subBreeds = entry.value
                DogBreed(
                    name = breedName,
                    subBreeds = subBreeds.joinToString(" • "),
                    imageUrl = "", // Image URL will be fetched on demand
                    isFavourite = DEFAULT_IS_FAVOURITE
                )
            }
        }
    }
//
//    override fun getDogBreeds(): Single<List<DogBreed>> {
//        return dogApiService.fetchDogBreeds()
//            .flatMap { breedResponse ->
//                Observable.fromIterable(breedResponse.message.entries)
//                    .flatMapSingle { entry ->
//                        val breedName = entry.key
//                        val subBreeds = entry.value
//                        dogApiService.fetchDogBreedSingleImage(breedName).map { imageResponse ->
//                            DogBreed(
//                                name = breedName,
//                                subBreeds = subBreeds.joinToString(" • "),
//                                imageUrl = imageResponse.message,
//                                isFavourite = DEFAULT_IS_FAVOURITE
//                            )
//                        }.onErrorReturn { error ->
//                            DogBreed(
//                                name = breedName,
//                                subBreeds = subBreeds.joinToString(" • "),
//                                imageUrl = "",
//                                isFavourite = DEFAULT_IS_FAVOURITE
//                            )
//                        }
//                    }.toList()
//            }
//    }

    override fun getDogBreedImages(breedName: String): Single<List<String>> {
        return dogApiService.fetchDogBreedImages(breedName).map { dogBreedImages ->
            dogBreedImages.message
        }
    }
}
