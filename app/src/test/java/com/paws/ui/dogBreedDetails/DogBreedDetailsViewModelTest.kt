package com.paws.ui.dogBreedDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.paws.domain.usecase.dogBreedImages.DogBreedImagesUseCase
import com.paws.ui.component.dogBreedImages.DogBreedDetailsViewModel
import com.paws.utils.Breeds
import io.mockk.MockKAnnotations
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class DogBreedDetailsViewModelTest {

    private lateinit var viewModel: DogBreedDetailsViewModel
    private val dogBreedImagesUsageCase: DogBreedImagesUseCase = mock()
    private val dogBreed = "Lab"

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
        val dogBreeds = Breeds.breedImages()
        whenever(dogBreedImagesUsageCase.getDogBreedImages(dogBreed)).thenReturn(
            Single.just(
                dogBreeds
            )
        )

        // Invoke
        viewModel =
            DogBreedDetailsViewModel(dogBreedImagesUsageCase, dogBreed)

        // verify
        assertThat(viewModel.pawState.value).isEqualTo(
            DogBreedDetailsViewModel.DetailPawState.Success(
                dogBreeds
            )
        )
    }

    @Test
    fun `initialize then fetch dog breeds failed`() {
        // Prepare
        whenever(dogBreedImagesUsageCase.getDogBreedImages(dogBreed)).thenReturn(
            Single.just(emptyList())
        )

        // Invoke
        viewModel =
            DogBreedDetailsViewModel(dogBreedImagesUsageCase, dogBreed)

        // Verify
        assertThat(viewModel.pawState.value).isEqualTo(
            DogBreedDetailsViewModel.DetailPawState.Success(
                emptyList()
            )
        )
    }
}