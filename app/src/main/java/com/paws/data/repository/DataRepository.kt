package com.paws.data.repository

import com.paws.data.local.LocalSource
import com.paws.data.remote.RemoteSource
import com.paws.domain.model.DogBreed
import com.paws.domain.model.DogBreedImages
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DataRepository @Inject
constructor(
    private val remoteDataSource: RemoteSource,
    private val localDataSource: LocalSource
) : DataSource {

    override fun getDogBreeds(): Observable<List<DogBreed>> {
        return remoteDataSource.getDogBreeds().flatMapCompletable {
            localDataSource.storeDogBreedListInDb(it)
        }.andThen(localDataSource.getDogBreeds()).onErrorResumeNext { _: Throwable ->
            localDataSource.getDogBreeds()
        }
    }

    override fun getDogBreedImages(breedName: String): Single<List<String>> {
        return remoteDataSource.getDogBreedImages(breedName)
            .flatMapCompletable { dogBreedImages ->
                localDataSource.storeDogBreedImageListInDb(
                    DogBreedImages(
                        dogBreedImages,
                        breedName
                    )
                )
            }.andThen(localDataSource.getDogBreedImages(breedName)).onErrorResumeNext {
                localDataSource.getDogBreedImages(breedName)
            }
    }

    override suspend fun updateDogBreeds(name: String, isFavourite: Boolean) {
        localDataSource.updateDogBreeds(name, isFavourite)
    }

    override fun getFavouriteDogBreeds(): Observable<List<DogBreed>> {
        return localDataSource.getFavouriteDogBreeds()
    }
}
