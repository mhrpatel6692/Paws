package com.paws.usecase

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.paws.data.repository.DataSource
import com.paws.domain.usecase.dogBreeds.DogBreedsUseCase
import com.paws.utils.Breeds
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DogBreedsUseCaseTest {
    @Mock
    private lateinit var dataRepository: DataSource
    private lateinit var dogBreedsUseCase: DogBreedsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dogBreedsUseCase = DogBreedsUseCase(dataRepository)
    }

    @Test
    fun `getDogBreeds should return list of dog breeds`() {
        // Initialise
        val dogBreeds = Breeds.dogBreeds()
        whenever(dataRepository.getDogBreeds()).thenReturn(Observable.just(dogBreeds))

        // Invoke
        val testObserver = dogBreedsUseCase.getDogBreeds().test()

        // Verify
        testObserver.assertValue(dogBreeds)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(dataRepository).getDogBreeds()
    }

    @Test
    fun `getDogBreeds should handle errors`() {
        // Initialise
        val error = Throwable("Something went wrong")
        whenever(dataRepository.getDogBreeds()).thenReturn(Observable.error(error))

        // Invoke
        val testObserver = dogBreedsUseCase.getDogBreeds().test()

        // Verify
        testObserver.assertError(error)
        testObserver.assertNotComplete()
        verify(dataRepository).getDogBreeds()
    }
}