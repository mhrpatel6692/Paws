package com.paws.ui.component.dogBreedImages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paws.domain.usecase.dogBreedImages.DogBreedImagesUseCase
import com.paws.ui.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DogBreedDetailsViewModel @AssistedInject constructor(
    private val dogBreedImagesUseCase: DogBreedImagesUseCase,
    @Assisted private val dogBreed: String
) : BaseViewModel() {

    private val _detailPawState = MutableLiveData<DetailPawState>()

    val pawState: LiveData<DetailPawState>
        get() = _detailPawState

    init {
        addSubscription(
            dogBreedImagesUseCase.getDogBreedImages(dogBreed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _detailPawState.value = DetailPawState.Success(it) },
                    { _detailPawState.value = DetailPawState.Error }
                )
        )
    }

    sealed interface DetailPawState {
        data class Success(val images: List<String>) : DetailPawState
        object Error : DetailPawState
    }
}