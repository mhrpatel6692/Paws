package com.paws.usecase

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.paws.data.repository.DataSource
import com.paws.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import com.paws.utils.Breeds
import com.paws.utils.CoroutineTestRule
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavouriteDogBreedsUseCaseTest {
    lateinit var favouriteDogBreedsUseCase: FavouriteDogBreedsUseCase

    private val dataRepository: DataSource = mock()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        favouriteDogBreedsUseCase = FavouriteDogBreedsUseCase(dataRepository)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `fetch dog breeds`() {
        // Given
        val favouriteDogBreeds = Breeds.dogBreeds()
        whenever(dataRepository.getFavouriteDogBreeds()).thenReturn(
            Observable.just(
                favouriteDogBreeds
            )
        )

        // When
        val testObserver = favouriteDogBreedsUseCase.getFavouriteDogBreeds().test()

        // Then
        testObserver.assertValue(favouriteDogBreeds)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(dataRepository).getFavouriteDogBreeds()
    }

    @Test
    fun `getFavouriteDogBreeds should handle errors`() {
        // Given
        val error = Throwable("Something went wrong")
        whenever(dataRepository.getFavouriteDogBreeds()).thenReturn(Observable.error(error))

        // When
        val testObserver = favouriteDogBreedsUseCase.getFavouriteDogBreeds().test()

        // Then
        testObserver.assertError(error)
        testObserver.assertNotComplete()
        verify(dataRepository).getFavouriteDogBreeds()
    }

    @Test
    fun `addToFavourites should update dog breed`() = runTest {
        // Given
        val dogName = "Labrador"
        val isFavourite = true

        // When
        favouriteDogBreedsUseCase.addToFavourites(dogName, isFavourite)

        // Then
        verify(dataRepository).updateDogBreeds(dogName, isFavourite)
    }
}