package com.paws.data.local

import com.paws.data.Resource
import com.paws.data.local.persistence.DogBreedsDao
import com.paws.domain.model.DogBreed
import com.paws.domain.model.DogBreedImages
import com.paws.utils.Converters
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.operators.single.SingleFlatMap
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject
constructor(private val dao: DogBreedsDao) : LocalSource {

    // Function for getting Dog Breeds List from Database
    override fun getDogBreeds(): Observable<List<DogBreed>> {
        return dao.getDogBreeds()
    }

    // Function for storing Dog Breeds List to Database
    override fun storeDogBreedListInDb(dogBreeds: List<DogBreed>): Completable {
        return dao.insertAllDogBreeds(dogBreeds)
    }

    // Function to get dog breed images from the database
    override fun getDogBreedImages(breedName: String): Single<List<String>> {
        return Single.create { emitter ->
            try {
                val dogBreedImageList = dao.getDogBreedImages(breedName)
                if (dogBreedImageList.isEmpty()) {
                    emitter.onError(Throwable("Error: No images found for breed $breedName"))
                } else {
                    val converter = Converters()
                    val images = converter.fromString(dogBreedImageList[0])
                    emitter.onSuccess(images)
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    // Function for storing Dog Breed Images List to Database
    override fun storeDogBreedImageListInDb(breedImages: DogBreedImages): Completable {
        return dao.insertDogBreedImages(breedImages)
    }

    // Function to update dog breeds table for favourite value in the Database
    override suspend fun updateDogBreeds(name: String, isFavourite: Boolean) {
        dao.updateDogBreeds(name, isFavourite)
    }

    // Function to get favourite dog breeds list from the Database
    override fun getFavouriteDogBreeds(): Observable<List<DogBreed>> {
        return dao.getFavouriteDogBreeds()
    }
}
