package com.paws.ui.dogBreedsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.paws.domain.usecase.dogBreeds.DogBreedsUseCase
import com.paws.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import com.paws.ui.component.dogBreedsList.DogBreedsListViewModel
import com.paws.utils.Breeds
import io.mockk.MockKAnnotations
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock

@ExperimentalCoroutinesApi
class DogBreedsListViewModelTest {

    private lateinit var viewModel: DogBreedsListViewModel

    private val dogBreedsUseCase: DogBreedsUseCase = mock()
    private val favouriteDogBreedsUseCase: FavouriteDogBreedsUseCase = mock()

    @Mock
    private lateinit var observer: Observer<DogBreedsListViewModel.PawState>

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `fetchDogBreeds should post Success when use case returns dog breeds`() {
        // Prepare
        val dogBreeds = Breeds.dogBreeds()
        whenever(dogBreedsUseCase.getDogBreeds()).thenReturn(Observable.just(dogBreeds))

        // Invoke
        viewModel = DogBreedsListViewModel(dogBreedsUseCase, favouriteDogBreedsUseCase)

        // verify
        assertThat(viewModel.pawState.value).isEqualTo(
            DogBreedsListViewModel.PawState.Success(
                dogBreeds
            )
        )
    }

    @Test
    fun `initialize then fetch dog breeds failed`() {
        // Prepare
        whenever(dogBreedsUseCase.getDogBreeds()).thenReturn(Observable.just(emptyList()))

        // Invoke
        viewModel = DogBreedsListViewModel(dogBreedsUseCase, favouriteDogBreedsUseCase)

        // Verify
        assertThat(viewModel.pawState.value).isNotEqualTo(
            DogBreedsListViewModel.PawState.Success(
                Breeds.dogBreeds()
            )
        )
    }
}