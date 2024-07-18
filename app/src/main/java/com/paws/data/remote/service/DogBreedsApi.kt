package com.paws.data.remote.service

import com.paws.data.remote.dto.DogBreedImagesResponse
import com.paws.data.remote.dto.DogBreedResponse
import com.paws.data.remote.dto.DogBreedSingleImageResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedsApi {
    @GET("breeds/list/all")
    fun fetchDogBreeds(): Single<DogBreedResponse>

    @GET("breed/{breed_name}/images")
    fun fetchDogBreedImages(@Path("breed_name") breedName: String): Single<DogBreedImagesResponse>

    @GET("breed/{breed_name}/images/random")
    fun fetchDogBreedSingleImage(@Path("breed_name") breedName: String): Single<DogBreedSingleImageResponse>
}
